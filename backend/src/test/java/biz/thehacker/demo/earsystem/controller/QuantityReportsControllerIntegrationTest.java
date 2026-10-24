package biz.thehacker.demo.earsystem.controller;

import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
class QuantityReportsControllerIntegrationTest {

    private final WebApplicationContext webApplicationContext;

    public QuantityReportsControllerIntegrationTest(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    private MockMvc mockMvc;

    @BeforeEach
    void setupMockMvc() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void listQuantityReports() throws Exception {
        mockMvc
                .perform(get("/quantityReports"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$", hasSize(4))
                );
    }

    @Test
    void addQuantityReport_invalidJson() throws Exception {
        String requestJson = "This ain't JSON at all 😝️\u0000Oooooppsss.....";

        mockMvc
                .perform(
                        post("/quantityReports")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.success", is(false)),
                        jsonPath("$.errorMessage", startsWith("Invalid JSON: "))
                );
    }

    @Test
    void addQuantityReport_invalidYearMonth() throws Exception {
        @Language("json")
        String requestJson = """
            {
              "amount": 47,
              "unit": "PIECE",
              "yearMonth": "9999-13",
              "deviceTypeNumber": 12345,
              "registrationNumber": 666,
              "declarationOfTruth": true
            }
        """;

        mockMvc
                .perform(
                        post("/quantityReports")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.success", is(false)),
                        jsonPath("$.errorMessage", containsString("Invalid JSON")),
                        jsonPath("$.errorMessage", containsString("Text '9999-13' could not be parsed"))
                );
    }

    @Test
    void addQuantityReport_invalidAmountPrecision() throws Exception {
        @Language("json")
        String requestJson = """
            {
              "amount": "47.11",
              "unit": "PIECE",
              "yearMonth": "2026-10",
              "deviceTypeNumber": 578484984,
              "registrationNumber": 415848,
              "declarationOfTruth": true
            }
        """;

        mockMvc
                .perform(
                        post("/quantityReports")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.success", is(false)),
                        jsonPath("$.errorMessage", containsString(
                                "Invalid number of decimal places. Value 47.11 with unit PIECE may only have at most 0 decimal places."
                        ))
                );
    }

    @Test
    void addQuantityReport_negativeAmount() throws Exception {
        @Language("json")
        String requestJson = """
            {
              "amount": "-99.9999",
              "unit": "KILOGRAM",
              "yearMonth": "2026-09",
              "deviceTypeNumber": 548934231,
              "registrationNumber": 45752,
              "declarationOfTruth": true
            }
        """;

        mockMvc
                .perform(
                        post("/quantityReports")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.success", is(false)),
                        jsonPath("$.errorMessage", containsString(
                                "Cannot have negative amount. -99.9999 is not valid."
                        ))
                );
    }

    // TODO more test cases
    //      e.g. deviceTypeNumber/registrationNumber out of bounds, invalid unit,
    //           general overflow/underflow, missing/extra fields, ...
}

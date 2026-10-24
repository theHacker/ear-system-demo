package biz.thehacker.demo.earsystem.controller;

import biz.thehacker.demo.earsystem.model.QuantityReport;
import biz.thehacker.demo.earsystem.repository.QuantityReportsRepository;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

import static biz.thehacker.demo.earsystem.model.Unit.*;
import static java.time.Month.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.quality.Strictness.STRICT_STUBS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class QuantityReportsControllerTest {

    private MockitoSession session;
    private MockMvc mockMvc;

    private QuantityReportsRepository repositoryMock;

    @BeforeEach
    void setUp() {
        session = Mockito.mockitoSession()
                .strictness(STRICT_STUBS)
                .startMocking();

        repositoryMock = mock();

        mockMvc = MockMvcBuilders
                .standaloneSetup(new QuantityReportsController(repositoryMock))
                .build();
    }

    @AfterEach
    void tearDown() {
        session.finishMocking();
    }

    @Test
    void listQuantityReports() throws Exception {
        when(repositoryMock.queryAll()).thenReturn(
                List.of(
                        new QuantityReport(new BigDecimal("47.11"), KILOGRAM, YearMonth.of(2026, JANUARY), 12345, 99999, true),
                        new QuantityReport(new BigDecimal("0"), PIECE, YearMonth.of(2026, FEBRUARY), -999, 0, false), // <- likely not realistic
                        new QuantityReport(new BigDecimal("2000000000"), PIECE, YearMonth.of(2024, DECEMBER), 1236666987, 543210, true),
                        new QuantityReport(new BigDecimal("12345678901234567890.123456"), TON, YearMonth.of(2025, APRIL), Integer.MAX_VALUE, Integer.MIN_VALUE, false) // <- extreme values, also likely not realistic
                )
        );

        mockMvc
                .perform(get("/quantityReports"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$", hasSize(4))
                );
    }

    @Test
    void addQuantityReport_persists() throws Exception {
        @Language("json")
        String jsonRequested = """
            {
              "amount": "99.4",
              "unit": "TON",
              "yearMonth": "2012-04",
              "deviceTypeNumber": 123456,
              "registrationNumber": 987654,
              "declarationOfTruth": true
            }
        """;

        mockMvc
                .perform(
                        post("/quantityReports")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequested)
                )
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.success", is(true)),
                        jsonPath("$.errorMessage").doesNotExist()
                );

        verify(repositoryMock).add(new QuantityReport(new BigDecimal("99.4"), TON, YearMonth.of(2012, APRIL), 123456, 987654, true));
        verifyNoMoreInteractions(repositoryMock);
    }
}

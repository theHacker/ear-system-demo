package biz.thehacker.demo.earsystem.service;

import biz.thehacker.demo.earsystem.model.QuantityReport;
import biz.thehacker.demo.earsystem.soap.mitteilung.MitteilungsServicePort;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.YearMonth;

import static biz.thehacker.demo.earsystem.model.Unit.TON;
import static java.time.Month.APRIL;
import static org.mockito.Mockito.*;

class QuantityReportsServiceTest {

    @Test
    void submitNewReport_contactsWebService() throws Exception {
        MitteilungsServicePort mitteilungsServicePortMock = mock();
        QuantityReportsService service = new QuantityReportsService(mitteilungsServicePortMock);

        service.submitNewReport(
                new QuantityReport(new BigDecimal("99.4"), TON, YearMonth.of(2012, APRIL), 123456, 987654, true)
        );

        verify(mitteilungsServicePortMock).submitIstInput(new BigDecimal("99.4"), 3, 2012, 4, 123456, 987654, true);
        verifyNoMoreInteractions(mitteilungsServicePortMock);
    }

    // TODO test Spring scheduling and retry mechanism
}

package biz.thehacker.demo.earsystem;

import biz.thehacker.demo.earsystem.soap.mitteilung.MitteilungsServicePort;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Helper to explore the SOAP service.
 * <p>
 * Run the test (remove/comment out the tag), set a breakpoint in the debugger and use the evaluate feature
 * of your IDE to send requests to the Web Service.
 */
@SpringBootTest
class SoapDemoTest {

    @SuppressWarnings("unused")
    @Autowired private MitteilungsServicePort mitteilungsServicePort;

    @Test
    @Tag("manual")
    void testSoapAPI() {
        System.out.println("Comment out the @Tag, set breakpoint here, and use the mitteilungsServicePort in the evaluate window of your IDE.");
    }
}

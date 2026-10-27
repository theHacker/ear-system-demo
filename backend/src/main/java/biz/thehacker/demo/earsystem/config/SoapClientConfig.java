package biz.thehacker.demo.earsystem.config;

import biz.thehacker.demo.earsystem.soap.mitteilung.MitteilungsService;
import biz.thehacker.demo.earsystem.soap.mitteilung.MitteilungsServicePort;
import jakarta.xml.ws.BindingProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static jakarta.xml.ws.BindingProvider.*;

@Configuration
public class SoapClientConfig {

    private static final Logger log = LoggerFactory.getLogger(SoapClientConfig.class);

    private final EarsystemSoapConfigProperties soapConfigProperties;

    public SoapClientConfig(EarsystemConfigProperties configProperties) {
        this.soapConfigProperties = configProperties.getSoap();
    }

    // Note: WSDL has German identifiers -.-
    //       There fore we end up with a mix of English and German.

    @Bean
    public MitteilungsServicePort mitteilungsServicePort() {
        MitteilungsService service = new MitteilungsService();
        MitteilungsServicePort port = service.getPort(MitteilungsServicePort.class);

        BindingProvider bindingProvider = (BindingProvider) port;
        Map<String, Object> requestContext = bindingProvider.getRequestContext();
        if (soapConfigProperties.getEndpointUrl() != null) {
            requestContext.put(ENDPOINT_ADDRESS_PROPERTY, soapConfigProperties.getEndpointUrl());
        }
        if (soapConfigProperties.getUsername() != null) {
            requestContext.put(USERNAME_PROPERTY, soapConfigProperties.getUsername());
        }
        if (soapConfigProperties.getPassword() != null) {
            requestContext.put(PASSWORD_PROPERTY, soapConfigProperties.getPassword());
        }

        log.info("Configured SOAP MitteilungsService at {}", requestContext.get(ENDPOINT_ADDRESS_PROPERTY));

        return port;
    }
}

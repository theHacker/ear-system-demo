package biz.thehacker.demo.earsystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("earsystem")
public class EarsystemConfigProperties {

    private final EarsystemSoapConfigProperties soap;

    public EarsystemConfigProperties(EarsystemSoapConfigProperties soap) {
        this.soap = soap;
    }

    public EarsystemSoapConfigProperties getSoap() {
        return soap;
    }
}

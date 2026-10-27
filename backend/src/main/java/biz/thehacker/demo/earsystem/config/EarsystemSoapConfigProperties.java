package biz.thehacker.demo.earsystem.config;

public class EarsystemSoapConfigProperties {

    public EarsystemSoapConfigProperties(String endpointUrl, String username, String password) {
        this.endpointUrl = endpointUrl;
        this.username = username;
        this.password = password;
    }

    private final String endpointUrl;
    private final String username;
    private final String password;

    /** Endpoint URL to connect to the Web Service. Leave `null` for defaults from the WSDL. */
    public String getEndpointUrl() {
        return endpointUrl;
    }

    /** Username to use to authenticate against the Web Service. */
    public String getUsername() {
        return username;
    }

    /** Password to use to authenticate against the Web Service. */
    public String getPassword() {
        return password;
    }
}

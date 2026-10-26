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

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

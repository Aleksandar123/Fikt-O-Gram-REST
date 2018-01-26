package instagram.model.json.response;

import instagram.model.base.ModelBase;

/**
 * Created by aleksandar on 24.1.17.
 */
public class AuthenticationResponse extends ModelBase {

    private String token;

    public AuthenticationResponse() {
        super();
    }

    public AuthenticationResponse(String token) {
        this.setToken(token);
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}


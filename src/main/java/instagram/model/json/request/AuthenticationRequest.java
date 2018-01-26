package instagram.model.json.request;

import instagram.model.base.ModelBase;

/**
 * Created by aleksandar on 24.1.17.
 */
public class AuthenticationRequest extends ModelBase {
    private String username;
    private String password;


    public AuthenticationRequest() {
        super();
    }

    public AuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

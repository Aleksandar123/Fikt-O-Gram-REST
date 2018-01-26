package instagram.model.factory;

import instagram.domain.entity.User;
import instagram.model.security.SecurityUser;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Created by aleksandar on 24.1.17.
 */
public class UserFactory {
    public static SecurityUser create(User user) {
        return new SecurityUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getLastPasswordReset(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities())
        );
    }
}

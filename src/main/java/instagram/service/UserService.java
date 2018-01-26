package instagram.service;

import instagram.domain.entity.User;

import java.util.List;

/**
 * Created by aleksandar on 24.1.17.
 */
public interface UserService {
    List<User> getAllUsers();
    User getUserByUsername(String username);
    boolean authenticate(String username, String password);
    List<User> findAll();
    User findById(Long id);
    User create(User user);
    User edit(User user);
    void deleteById(Long id);
}

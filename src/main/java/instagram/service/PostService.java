package instagram.service;

import instagram.domain.entity.Post;

import java.util.List;

/**
 * Created by aleksandar on 24.1.17.
 */
public interface PostService {
    List<Post> findAll();
    List findAllByOrderByDateDesc();
    Post findById(Long id);
    Post create(Post post);
    List<Post> FindByUser(Long id);
    Post edit(Post post);
    void deleteById(Long id);
}

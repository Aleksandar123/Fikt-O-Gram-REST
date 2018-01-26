package instagram.service;

import instagram.domain.entity.Comment;

import java.util.List;

/**
 * Created by aleksandar on 24.1.17.
 */
public interface CommentService {
    List<Comment> findAll();
    Comment findById(Long id);
    Comment create(Comment comment);
    Comment edit(Comment comment);
    void deleteById(Long id);
    List<Comment> findByPostId(Long id);
    List<Comment> getByUsername(String username);
}

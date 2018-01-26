package instagram.service.impl;

import instagram.domain.entity.Comment;
import instagram.repository.CommentRepository;
import instagram.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by aleksandar on 24.1.17.
 */
@Service
@Primary
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepo;

    @Override
    public List<Comment> findAll() {
        return this.commentRepo.findAll();
    }

    @Override
    public Comment findById(Long id) {
        return this.commentRepo.findOne(id);
    }

    @Override
    public Comment create(Comment comment) {
        return this.commentRepo.save(comment);
    }

    @Override
    public Comment edit(Comment comment) {
        return this.commentRepo.save(comment);
    }

    @Override
    public void deleteById(Long id) {
        this.commentRepo.delete(id);
    }

    @Override
    public List<Comment> findByPostId(Long id) {
        return commentRepo.findAllByPostId(id);
    }

    @Override
    public List<Comment> getByUsername(String username) {
        return commentRepo.getByUsername(username);
    }

}

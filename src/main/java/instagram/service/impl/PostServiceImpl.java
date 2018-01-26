package instagram.service.impl;

import instagram.domain.entity.Post;
import instagram.repository.PostRepository;
import instagram.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by aleksandar on 24.1.17.
 */
@Service
@Primary
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepo;

    @Override
    public List<Post> findAll() {
        return this.postRepo.findAll();
    }



    @Override
    public List findAllByOrderByDateDesc() {
        return this.postRepo.findAllByOrderByDateDesc();
    }

    @Override
    public Post findById(Long id) {
        return this.postRepo.findOne(id);
    }

    @Override
    public Post create(Post post) {
        return this.postRepo.save(post);
    }

    @Override
    public List<Post> FindByUser(Long id) {


        return postRepo.FindAllByUserId(id);
    }

    @Override
    public Post edit(Post post) {
        return this.postRepo.save(post);
    }

    @Override
    public void deleteById(Long id) {
        this.postRepo.delete(id);
    }


}

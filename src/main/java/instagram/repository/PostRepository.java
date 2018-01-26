package instagram.repository;

import instagram.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aleksandar on 24.1.17.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    public final static String FIND_BY_USER_ID = "SELECT p " +
            "FROM Post p LEFT JOIN p.uploader u " +
            "WHERE u.id = :id";


    @Query(FIND_BY_USER_ID)
    List<Post> FindAllByUserId(@Param("id")  Long id);

    @Query("SELECT p FROM Post p ORDER BY date DESC")
    List findAllByOrderByDateDesc();
}

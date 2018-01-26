package instagram.domain.entity;

import instagram.domain.base.DomainBase;

import javax.persistence.*;

/**
 * Created by aleksandar on 24.1.17.
 */
@Entity
@Table(name = "comments")
public class Comment extends DomainBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String comment;

    @ManyToOne
    private User commentor;

    @ManyToOne
    private Post image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }
    public User getCommentor() {
        return commentor;
    }

    public void setCommentor(User commentor) {
        this.commentor = commentor;
    }

    public Post getImage() {
        return image;
    }

    public void setImage(Post image) {
        this.image = image;
    }

    public Comment() {
    }

    public Comment(Long id, String comment, User commentor, Post image) {
        this.id = id;
        this.comment = comment;
        this.commentor = commentor;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", commentor=" + commentor.getUsername() +
                ", image=" + image.getId() +
                '}';
    }

}

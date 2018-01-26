package instagram.rest;

import instagram.domain.entity.Comment;
import instagram.domain.entity.Post;
import instagram.domain.entity.User;
import instagram.service.CommentService;
import instagram.service.PostService;
import instagram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by aleksandar on 24.1.17.
 */

@RestController
@RequestMapping("${fiktogram.route.comment}")
public class CommentController extends BaseController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @ModelAttribute("comment")
    public Comment comment(){
        return new Comment();
    }

    @RequestMapping(value="user/{username}", method = RequestMethod.GET)
    public List<Comment> viewCommentsByUser(@PathVariable(value="username")String username){
        User user = new User();
        user = userService.getUserByUsername(username);
        if(user.equals(null)){
            return null;
        }else{
            return commentService.getByUsername(user.getUsername());
        }
    }

    @RequestMapping(value = "post/{id}", method = RequestMethod.GET)
    public List<Comment> viewPostComments(@PathVariable(value =  "id") Long id){

        //return commentService.findByPostId(id);
        if(postService.findById(id)!=null){
            return commentService.findByPostId(id);
        }else{
            return null;
        }
    }
    @RequestMapping(value = "viewAll", method = RequestMethod.GET)
    public List<Comment> viewAllComments(){
        return commentService.findAll();
    }

    /**
     * curl  -H "Content-Type: application/json" -H "X-Auth-Token: JWT"  -X POST http://localhost:8081/api/comment/new/post/POSTID?comment=COMMENT
     * %20 - instead of space to submit a multi word comment (this%20is%20a%20comment)
     */
    @RequestMapping(value="new/post/{id}", method = RequestMethod.POST)
    public String newComment(@RequestParam("comment") String comment, @PathVariable("id") Long postId ){
        try{
            User user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            Post post = postService.findById(postId);
            Comment newComment = new Comment();

            newComment.setCommentor(user);
            //System.out.printf("***\n" + comment. +"\n***");

            if(comment.equals(null)){
                return "Comment must be present\n";
            }else{
                newComment.setComment(comment);
                //System.out.printf("***\n" + comment+"\n***");
            }


            if(post.equals(null)){
                return "Post not found\n";
            } else {
                newComment.setImage(post);
                commentService.create(newComment);
                return "Comment posted successfully\n";
            }
        }
        catch (NullPointerException e){
            //System.out.printf("***\n" + comment.toString() +"***");
            return "Null pointer Exception\n";

        }
    }
    @RequestMapping(value = "edit/{id}", method = RequestMethod.POST)
    public String editComment(@PathVariable(value = "id")Long id, @RequestBody Comment comment){
        User loggedUser = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Comment editedComment =  commentService.findById(id);

        if(!(editedComment.equals(null)) && loggedUser.equals(commentService.findById(id).getCommentor())){

            editedComment.setComment(comment.getComment());
            commentService.edit(editedComment);
            return "Comment with id " + id + " successfully edited.\n";
        }else{
            return "Error, No comment with that id or you don't have the privileges to edit the comment.\n";
        }
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public String deleteComment(@PathVariable(value = "id")Long id){
        Comment comment = commentService.findById(id);


        if(comment.equals(null)){
            return "Can't find comment with id " + id + "\n";
        }
        try{
            if(comment.getCommentor().equals( userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName())))
            {commentService.deleteById(id);
                return "Comment with id " + id + " successfully deleted!\n";}
            else{
                return "You are unauthorized to delete this comment\n";
            }
        }
        catch (Exception e){
            return e.getMessage();
        }
    }


}

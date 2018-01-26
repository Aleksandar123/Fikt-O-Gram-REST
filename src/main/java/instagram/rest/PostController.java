package instagram.rest;

import instagram.domain.entity.Post;
import instagram.domain.entity.User;
import instagram.other.PictureTools;
import instagram.service.CommentService;
import instagram.service.PostService;
import instagram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

/**
 * Created by aleksandar on 24.1.17.
 */
@RestController
@RequestMapping("${fiktogram.route.post}")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    private PictureTools pt = new PictureTools();

   // @Value("${fiktogram.root_location}")
    //private String ROOT;
    public static final String ROOT = "D:\\Aleksandar\\Diplomska-20170710T145751Z-001\\Diplomska\\Diplomska-2-0\\target\\classes\\public\\";

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public Post view(@PathVariable(value =  "id") Long id){
        return postService.findById(id);
    }

    @RequestMapping(value="allposts", method = RequestMethod.GET)
    public List<Post> viewAll(){
        return postService.findAll();
    }

    @RequestMapping(value = "view/user/{id}", method = RequestMethod.GET)
    public List<Post> viewByUserId(@PathVariable(value =  "id") Long id){
        return postService.FindByUser(id);
    }

    /**
     * curl -H "Content-Type: application/json" -H "X-Auth-Token: JWT"  -X POST -d '{"description":"edited"}' http://localhost:8081/api/post/edit/ID

     * */

    @RequestMapping(value = "edit/{id}" , method = RequestMethod.POST)
    public String editPost(@PathVariable(value = "id") Long id, @RequestBody Post post){
        Post editedPost = postService.findById(id);

        User loggedUser = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if(editedPost.getUploader().equals(loggedUser)){
            editedPost.setDescription(post.getDescription());
            postService.edit(editedPost);
            return "Post edited successfully.\n";
        }else{
            return "You are unauthorized to edit this post.";
        }

    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public String deletePost(@PathVariable(value = "id")Long id){
        Post post = postService.findById(id);
        User loggedUser = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(post==null){
            return "Post not found\n";
        }else if(post.getUploader().equals(loggedUser)){
            postService.deleteById(id);
            return "Post successfully deleted.\n";
        }
        else{
            return "You are unauthorized to delete this post.";
        }
    }
    /**
     curl -H "Content-Type: multipart/form-data" -H "X-Auth-Token: JWT"  -F "description=image description" -F "file=@LOCATION" http://localhost:8081/api/post/new

     */
    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String createNewPost(@RequestParam("file") MultipartFile file, @RequestParam("description") String post){

        if(file.getSize() >4194304L ){
            return "File to large. Images must be below 4mb.";
        }
        if (!file.isEmpty()) {

            Post newPost = new Post();
            newPost.setId(new Date().getTime() + 1L);
            User user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            String filename =  new Date().getTime() + ""  + "." + file.getOriginalFilename().split("\\.")[1];
            //System.out.println(ROOT + filename);
            //####
            File dir = new File(ROOT + File.separator + "userImages");
            if (!dir.exists())
                dir.mkdirs();
            String s = new Date().getTime() + "_" + file.getOriginalFilename();
            File serverFile = new File(dir.getAbsolutePath()
                    + File.separator + s);



        /*if (!file.isEmpty()) {*/
            try {
                File file1 = pt.convert(file);

                if(pt.getFormat(file1.getAbsolutePath()).equals("not-allowed")){
                    return "This type of file is not allowed!";

                }
                System.out.println(file.getSize());
                Files.copy(file.getInputStream(), Paths.get(serverFile.getAbsolutePath()));
                newPost.setUploader(user);
                newPost.setDescription(post);
                //newPost.setImgLocation( File.separator + "userImages" + File.separator + s);//File.separator + filename);
                newPost.setImgLocation( "/userImages/" + s);  //File.separator + filename);
                System.out.println(File.separator + "userImages" + File.separator + s);



                newPost.setImage(pt.cropImage(file1.getAbsoluteFile()));
                postService.create(newPost);
                System.out.println(newPost.toString());
                return "Successfuly uploaded";
            } catch (IOException | RuntimeException e) {
                System.out.println(e.toString());
                return "IO Exception";
            }
        } else {
            return "Error while uploading";
        }
    }

}


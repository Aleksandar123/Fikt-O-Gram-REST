package instagram.rest;

import instagram.domain.entity.User;
import instagram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by aleksandar on 24.1.17.
 */
@RestController
@RequestMapping("${fiktogram.route.user}")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> userList = userService.getAllUsers();

        return ResponseEntity.ok(userList);
    }

    //GET USER BY USERNAME
    // curl http://localhost:8080/api/users/username/{username} | python -m json.tool
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserByName(@PathVariable("username") String username) {

        User aUser = userService.getUserByUsername(username);

        return ResponseEntity.ok(aUser);
    }

    //GET ONE USER BY ID
    // curl http://localhost:8080/api/users/id/{id} | python -m json.tool
    @RequestMapping("/id/{id}")
    public User viewProfile(@PathVariable(value = "id")Long id){
        return userService.findById(id);
    }

    @RequestMapping("/me")
    public User me(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //System.out.println("****\n" + username + "\n****");
        return userService.getUserByUsername(username);
    }


    //GET ALL USERS
    // curl http://localhost:8080/api/user/viewallusers | python -m json.tool
    @RequestMapping(value = "/viewallusers", method = RequestMethod.GET)
    public List<User> getAll(){
        return userService.findAll();
    }

    //EDIT USER
    //curl -H "Content-Type: application/json" -X POST -d '{"fullName":"{value}","aboutMe":"{value}"}' http://localhost:8080/api/users/edit/{id}
    @RequestMapping(value = "/edit/{id}" , method = RequestMethod.POST)
    public String editUser(@PathVariable(value = "id") Long id, @RequestBody User user){
        User editedUser = userService.findById(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User authUser = userService.getUserByUsername(auth.getName());

        if(!editedUser.equals(authUser)){
            return "Unauthorized action.    \n";
        }else{

            editedUser.setEmail(user.getEmail());
            userService.edit(editedUser);

            return "Successful edit.";
        }
    }

    //DELETE USER
    //curl -X DELETE http://localhost:8080/api/user/delete/{id}
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable("id") Long id){
        User user = userService.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User authUser = userService.getUserByUsername(auth.getName());



        if(user==null){
            return "User not found\n";
        }else if(!user.equals(authUser)){
            return "Unauthorized action\n";
        }else{
            userService.deleteById(id);
            return "User successfully deleted.\n";
        }
    }

/*    //CREATE NEW
    //curl -H "Content-Type: application/json" -X POST -d '{"username": "{value}","password": "{value}"}' http://localhost:8080/api/user/create
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String createUser(@RequestBody User user) {
        try{
            if((user.getUsername().isEmpty()) | user.getPassword().isEmpty()){
                return "Username and password must be present.\n";

            }if(userService.getUserByUsername(user.getUsername())!=null){
                return "Username already exists. Please try another one\n";
            }
            else {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                user.setAuthorities("USER");
                //Bcrypt Test
                String bcryptpass = encoder.encode(user.getPassword());
                System.out.println("bcryptpass: " + bcryptpass);
                user.setPassword(bcryptpass);
                if(user.getId() == null){
                    userService.create(user);

                    return "User " + user.getUsername() +" created\n";
                }}
        }catch (NullPointerException e){
            return "All values should be entered.\n";
        }
        return "what?\n";
    }*/

}

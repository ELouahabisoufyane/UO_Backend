package com.UO.Web;

import com.UO.DOA.UserRepository;
import com.UO.Modele.Conference;
import com.UO.Modele.Participant;
import com.UO.Modele.User;
import com.UO.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserWeb {

    @Autowired
    private UserService us ;
    @Autowired
    private UserRepository ur;

    @PostMapping("/addOne")
    public User saveUser(@RequestBody User u ){
        return this.us.addUser(u);
    }
    @PostMapping("/login")
    User login(@RequestBody User u){

        return ur.findByUsernameAndPassword(u.getUsername(),u.getPassword());
    }
    @GetMapping("/getAll")
    List<User> getAll(){
        return this.ur.findAll();
    }
    @GetMapping("/getOne/{id}")
    User getOne(@PathVariable("id") String id){
        return this.ur.findByUsername(id);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        this.ur.deleteById(id);
    }
    @PutMapping("/updateOne")
    public User updateUser(@RequestBody User u){
        return this.us.addUser(u);
    }

}

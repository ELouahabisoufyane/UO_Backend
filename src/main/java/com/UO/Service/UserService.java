package com.UO.Service;

import com.UO.DOA.UserRepository;
import com.UO.Modele.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    final UserRepository ur;

    public UserService(UserRepository ur) {
        this.ur = ur;
    }

    public User addUser(User u){

        return this.ur.save(u);

    }
}

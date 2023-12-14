package com.cafe.java.cafebackend.services.auth;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cafe.java.cafebackend.models.UserProfile;
import com.cafe.java.cafebackend.repo.UserRepository;

@Service
public class CustomerDetailsUserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    private UserProfile userDetails;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userDetails = userRepository.findByUserEmail(username);
        if(!Objects.isNull(userDetails)){
            return new User(userDetails.getUserEmail(),userDetails.getPassword(),new ArrayList<>());
        }else {
            throw new UsernameNotFoundException("User not found!!");
        }
    }

    public UserProfile getUserProfile() {
        UserProfile user = userDetails;
        user.setPassword(null);
        return userDetails;
    }


    
}

package com.crud.service;

import com.crud.dao.UserRepository;
import com.crud.utility.ApplicationUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Boolean isUsernameExist(String username){
        var total = userRepository.countExistingUser(username);
        return (total > 0);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var account = userRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("User not found");
        } else {
            var userDetails = new ApplicationUserDetails(account);
            return userDetails;
        }
    }

    public Boolean checkUsernamePassword(String username, String password){
        var isAuthenticated = false;
        var entity = userRepository.findByUsername(username);
        if(entity != null){
            var hashedPassword = entity.getPassword();
            isAuthenticated = passwordEncoder.matches(password, hashedPassword);
        }
        return isAuthenticated;
    }
}

package com.dev.javadevproject.services;

import com.dev.javadevproject.entities.UserEntity;
import com.dev.javadevproject.repositories.RoleRepo;
import com.dev.javadevproject.repositories.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserDetails loadUserByUsername(String email) {
        return userRepo.findByEmail(email);
    }

    public void Create(String email, String username, String password){
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Arrays.asList(roleRepo.findByName("ROLE_USER")));
        userRepo.save(user);
    }

    public static UserEntity getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return null;
        Object principal = auth.getPrincipal();
        return (principal instanceof UserEntity) ? (UserEntity) principal : null;
    }

    public Boolean existsByEmail(String email){
        return userRepo.existsByEmail(email);
    }
}

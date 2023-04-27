package com.dev.javadevproject.resources;

import com.dev.javadevproject.dto.auth.*;
import com.dev.javadevproject.entities.UserEntity;
import com.dev.javadevproject.repositories.UserRepo;
import com.dev.javadevproject.security.JwtProvider;
import com.dev.javadevproject.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthResource {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserServiceImpl userService;

    @PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signin(@RequestBody SignInRequest signInRequest) throws IOException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.email, signInRequest.password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtProvider.generateJwtToken(signInRequest.email);
        return ResponseEntity.ok(new JwtResponse(jwtToken));
    }

    @PostMapping(value = "signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<String, String>> signup(@RequestBody SignUpRequest signUpRequest) throws IOException {
        HashMap<String, String> response = new HashMap<>();
        if (signUpRequest.email == null || signUpRequest.password == null || userService.existsByEmail(signUpRequest.email)) {
            response.put("status", "error");
            response.put("message", "Email is already taken or fields are empty");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        userService.Create(signUpRequest.email, signUpRequest.username, signUpRequest.password);
        response.put("status", "ok");
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProfile() throws IOException {
        UserEntity user = userService.getCurrentUser();
        user.setPassword("");
        return ResponseEntity.ok(user);
    }

    @PatchMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeInfo(@RequestBody UserPatchRequest userPatchRequest) throws IOException {
        HashMap<String, String> response = new HashMap<>();
        UserEntity user = userService.getCurrentUser();
        if(!userPatchRequest.email.equals("") && !userPatchRequest.email.equals(user.getEmail())){
            response.put("status", "error");
            response.put("message", "email already exists");
            if(userRepo.existsByEmail(userPatchRequest.email)) return ResponseEntity.ok(response);
            user.setEmail(userPatchRequest.email);
        }
        if(!userPatchRequest.username.equals("") && !userPatchRequest.username.equals(user.getUsername())){
            user.setUsername(userPatchRequest.username);
        }
        if(!userPatchRequest.newPassword.equals("")){
            user.setPassword(passwordEncoder.encode(userPatchRequest.newPassword));
        }
        userRepo.save(user);
        response.put("status", "ok");
        response.put("message", "User info updated");
        return ResponseEntity.ok(response);
    }
}

package com.dev.javadevproject.resources;

import com.dev.javadevproject.dto.orders.CartResponse;
import com.dev.javadevproject.entities.UserEntity;
import com.dev.javadevproject.repositories.UserRepo;
import com.dev.javadevproject.services.UserServiceImpl;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/cart")
public class CartResource {
    @Autowired
    UserRepo userRepo;

    @GetMapping("")
    public ResponseEntity<?> getList() throws IOException {
        UserEntity user = UserServiceImpl.getCurrentUser();
        return ResponseEntity.ok(new CartResponse(user.getCartItems()));
    }

    @PostMapping("")
    public ResponseEntity<?> addToList(@RequestBody String productId) throws IOException {
        UserEntity user = UserServiceImpl.getCurrentUser();
        var newCart = user.getCartItems();
        newCart.add(productId);
        user.setCartItems(newCart);
        userRepo.save(user);
        return ResponseEntity.ok(user.getCartItems());
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteFromList(@RequestBody String productId) throws IOException {
        UserEntity user = UserServiceImpl.getCurrentUser();
        List<String> newCart = user.getCartItems();
        newCart.remove(productId);
        user.setCartItems(newCart);
        userRepo.save(user);
        return ResponseEntity.ok(user.getCartItems());
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAll() throws IOException {
        HashMap<String, String> response = new HashMap<>();
        UserEntity user = UserServiceImpl.getCurrentUser();
        user.setCartItems(new ArrayList<>());
        userRepo.save(user);
        response.put("status","ok");
        response.put("message", "deleted all items");
        return ResponseEntity.ok(response);
    }
}

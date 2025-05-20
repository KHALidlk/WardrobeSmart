package org.example.outfit.Controller;

import org.example.outfit.dto.UserDTO;
import org.example.outfit.Service.UserService;
import org.example.outfit.mapper.UserMapper;
import org.example.outfit.mapper.UserMapperImpl;
import org.example.outfit.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapperImpl userMapper;

    @Autowired
    public UserController(UserService userService, UserMapperImpl userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    // Endpoint to get a list of all users - maintain your original
    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    // Update login endpoint to be more mobile-friendly
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        try {
            System.out.println("Login attempt for: " + userDTO.getLogin());

            // Get user from database to check if it exists
            User existingUser = userService.findByLogin(userDTO.getLogin());
            System.out.println("User found in database: " + (existingUser != null));

            String token = userService.verfy(userMapper.userDTOToUser(userDTO));
            if (!"FAIL".equals(token)) {
                // Return token with a proper JSON structure
                Map<String, Object> tokenMap = new HashMap<>();
                tokenMap.put("token", token);
                tokenMap.put("username", userDTO.getLogin());

                // Add user ID and role if available
                if (existingUser != null) {
                    tokenMap.put("id", existingUser.getId());
                    tokenMap.put("role", existingUser.getRole());

                    // Add wardrobe ID if available
                    if (existingUser.getWardrobe() != null) {
                        tokenMap.put("wardrobeId", existingUser.getWardrobe().getId());
                    }
                }

                System.out.println("Generated token for " + userDTO.getLogin() + ": " + token.substring(0, 20) + "...");
                return ResponseEntity.ok().body(tokenMap);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Invalid credentials");
                return ResponseEntity.status(401).body(error);
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Login error: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    // Endpoint to get a user by their ID
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Endpoint to add a new user - make more mobile friendly
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO) {
        // Enhanced validation for required fields
        if (userDTO.getLogin() == null || userDTO.getLogin().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Login is required");
            return ResponseEntity.badRequest().body(error);
        }

        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Password is required");
            return ResponseEntity.badRequest().body(error);
        }

        try {
            // Check if user already exists
            User existingUser = userService.findByLogin(userDTO.getLogin());
            if (existingUser != null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Username already exists");
                return ResponseEntity.badRequest().body(error);
            }

            UserDTO savedUser = userService.addUser(userDTO);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error creating user: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    // Endpoint to delete a user by their ID
    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
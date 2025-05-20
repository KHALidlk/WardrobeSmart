package org.example.outfit.Service;

import org.example.outfit.dto.UserDTO;
import org.example.outfit.mapper.UserMapper;
import org.example.outfit.model.User;
import org.example.outfit.model.Wardrobe;
import org.example.outfit.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final AuthenticationManager authManager;
private final JWTService jwtService;
    @Autowired
    public UserService(UserRepo userRepo, UserMapper userMapper, AuthenticationManager authManager, JWTService jwtService) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        return userRepo.findById(id)
                .map(userMapper::userToUserDTO)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }

//    public UserDTO getUserByEmail(String email) {
//        return userRepo.findByEmail(email)
//                .map(userMapper::userToUserDTO)
//                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
//    }

    public UserDTO addUser(UserDTO userDTO) {

        // Validate required fields
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userMapper.userDTOToUser(userDTO);

        // If no role is specified, default to USER role
        if (user.getRole() == null) {
            user.setRole(User.Role.USER);
        }

        // If the user role is USER, create a new Wardrobe and link it
        if (user.getRole() == User.Role.USER) {
            Wardrobe wardrobe = new Wardrobe();
            wardrobe.setName((user.getFname() != null ? user.getFname() : "User") + "'s Wardrobe");
            wardrobe.setDescription("Default wardrobe for " + (user.getFname() != null ? user.getFname() : "user"));
            user.setWardrobe(wardrobe); // Link the wardrobe to the user
        }

        User savedUser = userRepo.save(user);
        return userMapper.userToUserDTO(savedUser);
    }

    public void deleteUserById(Long id) {
        userRepo.deleteById(id);
    }
    public String verfy(User user) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword())
            );
            return jwtService.generateToken(auth);
        } catch (BadCredentialsException e) {
            System.out.println("Invalid credentials for user: " + user.getLogin());
            return "FAIL";
        } catch (Exception e) {
            System.out.println("Authentication error: " + e.getMessage());
            e.printStackTrace();
            throw e; // Or handle it appropriately
        }
    }
    public User findByLogin(String login) {
        return userRepo.findByLogin(login).orElse(null);
    }
}

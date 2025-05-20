package org.example.outfit.mapper;

import org.example.outfit.dto.UserDTO;
import org.example.outfit.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO userToUserDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        userDTO.setPassword(user.getPassword());
        userDTO.setFname(user.getFname());
        userDTO.setLname(user.getLname());
        userDTO.setEmail(user.getEmail());
        userDTO.setAge(user.getAge());
        userDTO.setRole(user.getRole());
        userDTO.setGender(user.getGender());
        return userDTO;
    }

    @Override
    public User userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());  // Fix: Set password correctly
        user.setFname(userDTO.getFname());
        user.setLname(userDTO.getLname());
        user.setEmail(userDTO.getEmail());
        user.setAge(userDTO.getAge());
        user.setRole(userDTO.getRole());
        user.setGender(userDTO.getGender());
        return user;
    }
}

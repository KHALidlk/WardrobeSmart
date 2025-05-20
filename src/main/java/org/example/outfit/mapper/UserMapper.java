package org.example.outfit.mapper;

import org.example.outfit.dto.UserDTO;
import org.example.outfit.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);
}

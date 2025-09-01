package co.credit.app.consumer.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.credit.app.consumer.dto.UserResponseDTO;
import co.credit.app.model.user.User;

@Mapper(componentModel = "spring")
public interface UserResponseDTOMapper {

    UserResponseDTO toResponse(User user);

    List<UserResponseDTO> toResponseList(List<User> users);

    User toModel(UserResponseDTO userResponseDTO);

}
package co.credit.app.consumer.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.credit.app.consumer.dto.UserDTOResponse;
import co.credit.app.model.user.User;

@Mapper(componentModel = "spring")
public interface UserResponseDTOMapper {

    UserDTOResponse toResponse(User user);

    List<UserDTOResponse> toResponseList(List<User> users);

    User toModel(UserDTOResponse userDTO);

}
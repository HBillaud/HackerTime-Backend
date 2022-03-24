package com.example.hackertimebackend.mapper;

import com.example.hackertimebackend.commons.UserResponse;
import com.example.hackertimebackend.db.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse userToUserResponse(User user);
}

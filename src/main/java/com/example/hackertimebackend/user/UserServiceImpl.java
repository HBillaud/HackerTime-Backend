package com.example.hackertimebackend.user;

import com.example.hackertimebackend.commons.UserResponse;
import com.example.hackertimebackend.mapper.UserMapper;
import com.example.hackertimebackend.db.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public UserResponse getUser(String id) throws Exception {
        return userRepository.findById(id).map(
                User -> userMapper.userToUserResponse(User)
        ).orElseThrow(
                () -> new Exception(String.format("User with email %s not found!", id))
        );
    }

}

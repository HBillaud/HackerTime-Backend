package com.example.hackertimebackend.user;

import com.example.hackertimebackend.commons.UserResponse;
import com.example.hackertimebackend.db.repositories.UserRepository;
import com.example.hackertimebackend.mapper.UserMapper;
import com.example.hackertimebackend.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public UserResponse getUser(String token) throws Exception {
        return userRepository.findById(jwtUtils.getUserNameFromJwtToken(token.substring(7))).map(
                User -> userMapper.userToUserResponse(User)
        ).orElseThrow(
                () -> new Exception(String.format("User with email %s not found!", token))
        );
    }

    @Override
    public UserResponse addReport(ObjectId reportId, String token) throws Exception {
        return userRepository.findById(jwtUtils.getUserNameFromJwtToken(token.substring(7))).map(
                User -> {
                    User.addReportId(reportId);
                    userRepository.save(User);
                    return userMapper.userToUserResponse(User);
                }
        ).orElseThrow(
                () -> new Exception(String.format("Report could not be added to current user"))
        );
    }

    @Override
    public void resetPassword(String email, String password) throws Exception {
        // TODO add implementation
    }
}

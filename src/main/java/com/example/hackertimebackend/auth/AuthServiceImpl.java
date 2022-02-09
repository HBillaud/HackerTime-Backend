package com.example.hackertimebackend.auth;

import com.example.hackertimebackend.commons.UserLoginRequest;
import com.example.hackertimebackend.commons.UserResponse;
import com.example.hackertimebackend.commons.UserSignupRequest;
import com.example.hackertimebackend.db.models.User;
import com.example.hackertimebackend.db.repositories.UserRepository;
import com.example.hackertimebackend.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final EmailVerification emailVerification;

    @Override
    public UserResponse login(UserLoginRequest request) throws Exception {
        return userRepository.findById(request.getEmail()).map(
                User -> {
                    try {
                        validateLogin(User, request.getPassword());
                        log.info("User successfully logged in!");
                        return UserResponse.builder().email(User.getEmail()).name(User.getName()).companyName(User.getCompanyName()).build();
                    } catch (Exception e) {
                        log.error("", e);
                        return null;
                    }
                }
        ).orElseThrow(
                () -> new Exception(String.format("User with email: %s not found!", request.getEmail()))
        );
    }

    @Override
    public UserResponse signup(UserSignupRequest request) throws Exception {
        if (userRepository.existsById(request.getEmail())) {
            throw new Exception(String.format("User with email: %s already exists!", request.getEmail()));
        } else {
            String salt = UUID.randomUUID().toString();
            String password = PasswordUtils.encryptFullPassword(
                    PasswordUtils.encryptEmailPassword(request.getEmail(), request.getPassword()),
                    salt);
            String verificationCode = UUID.randomUUID().toString();
            User user = User.builder()
                    .email(request.getEmail())
                    .companyName(request.getCompanyName())
                    .name(request.getName())
                    .password(password)
                    .salt(salt)
                    .createdDate(new Date())
                    .verified(false)
                    .verificationCode(verificationCode)
                    .build();

            userRepository.save(user);
            emailVerification.sendVerificationEmail(user);

            return UserResponse.builder().email(user.getEmail()).companyName(user.getCompanyName()).name(user.getName()).build();
        }
    }

    @Override
    public void verify(String id, String code) throws Exception {
        emailVerification.verifyUser(id, code);
    }

    private void validateLogin(User user, String password) throws Exception {
        if (!user.getVerified()) {
            throw new Exception(String.format("User with email: %s is not verified!", user.getEmail()));
        }
        if (!PasswordUtils.validatePassword(user, password)) {
            throw new Exception("Password is incorrect!");
        }
    }
}

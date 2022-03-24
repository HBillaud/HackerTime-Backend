package com.example.hackertimebackend.auth;

import com.example.hackertimebackend.commons.UserLoginRequest;
import com.example.hackertimebackend.commons.UserLoginResponse;
import com.example.hackertimebackend.commons.UserSignupRequest;
import com.example.hackertimebackend.db.models.User;
import com.example.hackertimebackend.db.repositories.UserRepository;
import com.example.hackertimebackend.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailVerification emailVerification;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserLoginResponse login(UserLoginRequest request) throws Exception {
        return userRepository.findById(request.getEmail()).map(
                User -> {
                    try {
                        if (!User.getVerified()) throw new Exception(String.format("User with email: %s is not " +
                                "verified", request.getEmail()));
                        Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
                        );
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        String jwt = jwtUtils.generateJwtToken(authentication);

                        log.info("User successfully logged in!");
                        return UserLoginResponse.builder().email(User.getEmail()).name(User.getName()).companyName(User.getCompanyName()).jwtToken(jwt).build();
                    } catch (Exception e) {
                        log.error("", e);
                        return null;
                    }
                }
        ).orElseThrow(
                () -> new Exception(String.format("Logging for: %s Failed!", request.getEmail()))
        );
    }

    @Override
    public UserLoginResponse signup(UserSignupRequest request) throws Exception {
        if (userRepository.existsById(request.getEmail())) {
            throw new Exception(String.format("User with email: %s already exists!", request.getEmail()));
        } else {
            String verificationCode = UUID.randomUUID().toString();
            User user = User.builder()
                    .email(request.getEmail())
                    .companyName(request.getCompanyName())
                    .name(request.getName())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .createdDate(new Date())
                    .verified(false)
                    .verificationCode(verificationCode)
                    .build();

            userRepository.save(user);
            emailVerification.sendVerificationEmail(user);

            return UserLoginResponse.builder().email(user.getEmail()).companyName(user.getCompanyName()).name(user.getName()).build();
        }
    }

    @Override
    public void verify(String id, String code) throws Exception {
        emailVerification.verifyUser(id, code);
    }
}

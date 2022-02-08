package com.example.hackertimebackend.auth;

import com.example.hackertimebackend.commons.UserLoginRequest;
import com.example.hackertimebackend.commons.UserResponse;
import com.example.hackertimebackend.commons.UserSignupRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.example.hackertimebackend.utils.ApiConstants.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_PATH_AUTH)
public class AuthController {
    private final AuthService authService;

    @PostMapping(LOGIN_PATH)
    public ResponseEntity login(
            @RequestBody @Valid UserLoginRequest userLoginRequest
    ) throws Exception {
        log.info("[POST] login request: {}", userLoginRequest);
        try {
            UserResponse userResponse = authService.login(userLoginRequest);
            ResponseEntity response = ResponseEntity.ok().build();
            log.info("[POST] login response: {}", userResponse);
            return response;
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
    }

    @PostMapping(SIGNUP_PATH)
    public ResponseEntity signup(
            @RequestBody @Valid UserSignupRequest userSignupRequest
    ) throws Exception {
        log.info("[POST] signup request: {}", userSignupRequest);
        try {
            UserResponse userResponse = authService.signup(userSignupRequest);
            ResponseEntity response = ResponseEntity.ok().build();
            log.info("[POST] signup response: {}", userResponse);
            return response;
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
    }
}

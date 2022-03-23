package com.example.hackertimebackend.auth;

import com.example.hackertimebackend.commons.UserLoginRequest;
import com.example.hackertimebackend.commons.UserResponse;
import com.example.hackertimebackend.commons.UserSignupRequest;
import com.example.hackertimebackend.db.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.hackertimebackend.utils.ApiConstants.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_PATH_AUTH)
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping(LOGIN_PATH)
    public ResponseEntity login(
            @RequestBody @Valid UserLoginRequest userLoginRequest
    ) throws Exception {
        log.info("[POST] login request: {}", userLoginRequest);
        try {
            UserResponse userResponse = authService.login(userLoginRequest);
            ResponseEntity response = new ResponseEntity(userResponse, HttpStatus.OK);
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
            ResponseEntity response = new ResponseEntity(userResponse, HttpStatus.CREATED);
            log.info("[POST] signup response: {}", userResponse);
            return response;
        } catch (Exception e) {
            userRepository.deleteById(userSignupRequest.getEmail());
            log.error("", e);
            throw e;
        }
    }

    @GetMapping(EMAIL_VERIFICATION_PATH)
    public ResponseEntity verify(
        @RequestParam("id") String id,
        @RequestParam("code") String code
    ) throws Exception {
        log.info("[GET] verify email: {} with token: {}", id, code);
        try {
            authService.verify(id, code);
            ResponseEntity response = new ResponseEntity(HttpStatus.OK);
            log.info("email: {} successfully verified!", id);
            return response;
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
    }
}

package com.example.hackertimebackend.user;

import com.example.hackertimebackend.commons.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.hackertimebackend.utils.ApiConstants.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_PATH)
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(USER_PATH)
    public ResponseEntity getUser(
            @RequestHeader(value = "Authorization") String token
    ) throws Exception {
        log.info("[GET] get user request: {}", token);
        try {
            UserResponse user = userService.getUser(token);
            ResponseEntity response = new ResponseEntity(user, HttpStatus.OK);
            log.info("[GET] user response: {}", user);
            return response;
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
    }
}

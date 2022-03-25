package com.example.hackertimebackend.user;

import com.example.hackertimebackend.commons.UserResponse;
import com.example.hackertimebackend.db.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @PathVariable String id
    ) throws Exception {
        log.info("[GET] get user request: {}", id);
        try {
            UserResponse user = userService.getUser(id);
            ResponseEntity response = new ResponseEntity(user, HttpStatus.OK);
            log.info("[GET] user response: {}", user);
            return response;
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
    }
}

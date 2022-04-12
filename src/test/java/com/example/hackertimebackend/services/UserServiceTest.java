package com.example.hackertimebackend.services;

import com.example.hackertimebackend.auth.AuthService;
import com.example.hackertimebackend.commons.UserLoginRequest;
import com.example.hackertimebackend.commons.UserLoginResponse;
import com.example.hackertimebackend.commons.UserResponse;
import com.example.hackertimebackend.db.models.User;
import com.example.hackertimebackend.db.repositories.UserRepository;
import com.example.hackertimebackend.mapper.UserMapper;
import com.example.hackertimebackend.report.ReportService;
import com.example.hackertimebackend.user.UserServiceImpl;
import com.example.hackertimebackend.utils.JwtUtils;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockRestServiceServer
class UserServiceTest {
    @Mock
    private JwtUtils jwtUtilsMock;
    @Mock
    private ReportService reportServiceMock;
    @Mock
    private UserMapper userMapperMock;
    @Mock
    private UserRepository userRepositoryMock;
    @InjectMocks
    private UserServiceImpl userService;

    @Autowired
    private AuthService authServiceMock;

    private UserLoginRequest request = new UserLoginRequest(
            "hpcbillaud@gmail.com",
            "test");
    private User user = new User(
            "hpcbillaud@gmail.com",
            "John Wick",
            "N/A",
            "$2a$10$u2JehPNmeoVDhfk7GBSiseFySTS9I5MokHFXD.XZ.dOMsrKqZhb.G",
            new ObjectId[0],
            true,
            "da91a2c8-4a00-4368-b922-7a035d306231",
            null
    );
    private UserLoginResponse response = new UserLoginResponse(
            "John Wick",
            "hpcbillaud@gmail.com",
            "N/A",
            ""
    );
    private UserResponse userResponse = new UserResponse(
            "hpcbillaud@gmail.com",
            "John Wick",
            "N/A",
            new ArrayList<>()
    );
    private UserLoginResponse loggedUser;


    @BeforeEach
    public void setup() throws Exception {
        loggedUser = authServiceMock.login(
                request
        );

        /*RecursiveComparisonConfiguration ignoreIdConfig = new RecursiveComparisonConfiguration();
        ignoreIdConfig.ignoreFields("jwtToken");
        assertThat(loggedUser).usingRecursiveComparison(ignoreIdConfig)
                .isEqualTo(response);*/
    }

    @Test
    public void whenValidInputGetUserThenReturnSuccess() throws Exception {
        given(jwtUtilsMock.getUserNameFromJwtToken(loggedUser.getJwtToken())).willReturn(loggedUser.getEmail());
        given(userRepositoryMock.findById(loggedUser.getEmail())).willReturn(Optional.of(user));
        given(userMapperMock.userToUserResponse(user)).willReturn(userResponse);

        UserResponse actual = userService.getUser(loggedUser.getJwtToken());
        userResponse.setReports(actual.getReports());

        assertThat(actual).isEqualTo(userResponse);
    }

    @Test
    public void whenInvalidInputGetUserThenFail() throws IllegalArgumentException {
        given(jwtUtilsMock.getUserNameFromJwtToken("")).willThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class,
                () -> userService.getUser(""));
    }


}

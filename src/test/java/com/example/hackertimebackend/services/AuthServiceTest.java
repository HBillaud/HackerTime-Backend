package com.example.hackertimebackend.services;

import com.example.hackertimebackend.auth.AuthServiceImpl;
import com.example.hackertimebackend.auth.EmailVerificationImpl;
import com.example.hackertimebackend.commons.UserLoginRequest;
import com.example.hackertimebackend.commons.UserLoginResponse;
import com.example.hackertimebackend.commons.UserSignupRequest;
import com.example.hackertimebackend.db.models.User;
import com.example.hackertimebackend.db.repositories.UserRepository;
import com.example.hackertimebackend.utils.JwtUtils;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockRestServiceServer
class AuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private EmailVerificationImpl emailVerification;
    @InjectMocks
    private AuthServiceImpl authService;

    UserLoginRequest request = new UserLoginRequest(
            "hpcbillaud@gmail.com",
            "test");
    UserLoginResponse response = new UserLoginResponse(
            "John Wick",
            "hpcbillaud@gmail.com",
            "N/A",
            "faferfwe");
    User user = new User(
            "hpcbillaud@gmail.com",
            "John Wick",
            "N/A",
            "$2a$10$u2JehPNmeoVDhfk7GBSiseFySTS9I5MokHFXD.XZ.dOMsrKqZhb.G",
            true,
            "da91a2c8-4a00-4368-b922-7a035d306231",
            null
    );

    @Test
    public void whenValidInputLoginReturnSuccessfulLogin() throws Exception {
        given(userRepository.findById(request.getEmail())).willReturn(Optional.of(user));

        UserLoginResponse actual = authService.login(
                request
        );

        RecursiveComparisonConfiguration ignoreIdConfig = new RecursiveComparisonConfiguration();
        ignoreIdConfig.ignoreFields("jwtToken");
        assertThat(actual).usingRecursiveComparison(ignoreIdConfig)
                .isEqualTo(response);
    }

    /*@Test
    public void whenInvalidPasswordLoginReturnUnsuccessfulLogin() throws Exception {
        UserLoginRequest request = new UserLoginRequest("hpcbillaud@gmail.com", "wrongpassword");

        given(userRepository.findById(request.getEmail())).willReturn(Optional.of(user));

        Exception actualEx = assertThrows(BadCredentialsException.class,
                () -> authService.login(request)
        );

        assertThat(actualEx.getMessage()).isEqualTo("Logging for: "+request.getEmail()+" Failed!");
    }*/

    @Test
    public void whenEmailDoesNotExistLoginThenReturnFailure() {
        UserLoginRequest badRequest = new UserLoginRequest("bademail", "xwy");

        given(userRepository.findById(badRequest.getEmail())).willReturn(Optional.empty());

        Exception actualEx = assertThrows(Exception.class,
                () -> authService.login(badRequest));

        assertThat(actualEx.getMessage()).startsWith("Logging for: " + badRequest.getEmail() + " Failed!");
    }

    @Test
    public void whenSignUpValidInputThenReturnSuccess() throws Exception {
        UserSignupRequest request = new UserSignupRequest(
                "test_test",
                "test@Test.com",
                "test",
                "Co."
        );
        UserLoginResponse expected = new UserLoginResponse(
                "test_test",
                "test@Test.com",
                "Co.",
                null
        );

        UserLoginResponse actual = authService.signup(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void whenSignUpEmailAlreadyExistsThenReturnFailure() {
        UserSignupRequest request = new UserSignupRequest(
                "TestName",
                "hpcbillaud@gmail.com",
                "test",
                "N/A"
        );

        given(userRepository.existsById(request.getEmail())).willReturn(Boolean.TRUE);

        assertThrows(Exception.class,
                () -> authService.signup(request));
    }

/*    @Test
    public void whenLoginUser_GivenValidInputs_ThenReturnSuccess() throws Exception {
        given(authService.login(request)).willReturn(response);

        UserResponse actual = RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .header(X_TRANSACTION_ID, X_TRANSACTION_ID_HDR_STUB)
                .header(AUTHORIZATION, AUTHORIZATION_HDR_STUB)
                .body(request)
                .post(ApiConstants.LOGIN_PATH)
                .then()
                .status(HttpStatus.CREATED)
                .extract()
                .as(UserLoginResponse.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void whenLoginUser_GivenTokenValidationFailure_ThenReturnFail() {
        UserLoginRequest request = getUserLoginRequestStub();

        given(loginService.login(request, SDC_TOKEN_STUB))
                .willThrow(new ServiceException(ServiceError.AUTHENTICATION_FAILURE, HttpStatus.UNAUTHORIZED));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .header(X_TRANSACTION_ID, X_TRANSACTION_ID_HDR_STUB)
                .header(AUTHORIZATION, AUTHORIZATION_HDR_STUB)
                .body(request)
                .post(ApiConstants.LOGIN_PATH)
                .then()
                .expect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof ServiceException));
    }*/

}
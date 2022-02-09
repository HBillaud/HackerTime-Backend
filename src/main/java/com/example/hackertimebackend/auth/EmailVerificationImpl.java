package com.example.hackertimebackend.auth;

import com.example.hackertimebackend.db.models.User;
import com.example.hackertimebackend.db.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static com.example.hackertimebackend.utils.ApiConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailVerificationImpl implements EmailVerification {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void sendVerificationEmail(User user) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        String url =
                LOCALHOST + BASE_PATH_AUTH + EMAIL_VERIFICATION_PATH + "?id=" + user.getEmail() + "&code=" + user.getVerificationCode();
        String body = "Hello [[name]], <br>"
                + "Thank you for creating a Hackertime account. Please click the link below to verify your registration before login:<br>"
                + "<h3><a href=\"[[URL]]\">Verify</a></h3>"
                + "Thank you,<br>"
                + "Hackertime Team.";

        body = body.replace("[[name]]", user.getName());
        body = body.replace("[[URL]]", url);

        try {
            helper.setTo(user.getEmail());
            helper.setSubject("Verify your Hackertime account");
            helper.setText(body, true);
            mailSender.send(msg);
        } catch (Exception e) {
            log.info("Verification email could not be sent!");
            throw e;
        }
    }

    @Override
    public boolean verifyUser(String id, String code) throws Exception {
        return userRepository.findById(id).map(
                User -> {
                    if (!User.getVerified() && User.getVerificationCode().equals(code)) {
                        User.setVerified(!User.getVerified());
                        userRepository.save(User);
                        return true;
                    }
                    return false;
                }
        ).orElseThrow(
                () -> new Exception("User does not exist!")
        );
    }
}

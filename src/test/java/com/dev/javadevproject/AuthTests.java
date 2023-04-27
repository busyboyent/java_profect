package com.dev.javadevproject;

import com.dev.javadevproject.security.JwtProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class AuthTests {

    @Autowired
    JwtProvider jwtProvider;

    @Test
    void usernameIsExtractedFromJwt() {
        String jwtToken = jwtProvider.generateJwtToken("example@example.com");
        String extractedUserName = jwtProvider.getUserNameFromJwtToken(jwtToken);
        assertThat(extractedUserName).isEqualTo("example@example.com");
    }

    @Test
    void invalidTokensAreRejected() {
        String invalidSignatureToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhbGciOiJIUzI1NiIsInR4cCI6IkpXVTJ9.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        assertThat(jwtProvider.validateJwtToken(invalidSignatureToken)).isEqualTo(false);
    }

    @Test
    void outdatedTokensAreRejected() {
        var outdatedToken = Jwts.builder().setSubject("example@example.com").setIssuedAt(new Date()).setExpiration(new Date((new Date(0)).getTime())).signWith(SignatureAlgorithm.HS512, "secret").compact();
        assertThat(jwtProvider.validateJwtToken(outdatedToken)).isEqualTo(false);
    }
}

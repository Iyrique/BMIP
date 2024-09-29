package org.example.lab3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lab3.generator.UserGenerator;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;

@Entity
@Table(name = "users", schema = "auth")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String secretWord;

    private String password;

    private boolean containsASD = false;

    @Column(name = "biometric_vector", columnDefinition = "BYTEA")
    private byte[] biometricVector;

    @Column(name = "min_vi", columnDefinition = "BYTEA")
    private byte[] minVi;

    @Column(name = "max_vi", columnDefinition = "BYTEA")
    private byte[] maxVi;

    public User(String name, String secretWord, String password, byte[] biometricVector, byte[] minVi, byte[] maxVi) {
        this.name = name;
        this.secretWord = secretWord;
        this.password = password;
        this.biometricVector = biometricVector;
        this.minVi = minVi;
        this.maxVi = maxVi;
        checkerOnASD(password);
        try {
            this.password = UserGenerator.passwordToHash(password);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        }
    }

    private void checkerOnASD(String password) {
        if (password.contains("a") && password.contains("s") && password.contains("d")) {
            this.containsASD = true;
        }
    }

}

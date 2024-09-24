package org.example.lab2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.postgresql.util.MD5Digest.bytesToHex;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    private boolean containsASD = false;

    @Column(name = "biometric_vector", columnDefinition = "BYTEA")
    private byte[] biometricVector;

    public User(String name, String password, byte[] biometricVector) {
        this.name = name;
        this.password = password;
        this.biometricVector = biometricVector;
        checkerOnASD(password);
        try {
            passwordToHash();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        }
    }

    private void checkerOnASD(String password) {
        if (password.contains("a") && password.contains("s") && password.contains("d")) {
            this.containsASD = true;
        }
    }

    private void passwordToHash() throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        this.password = bytesToHex(encodedHash);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

package org.example.lab2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    @Lob
    @Column(name = "biometric_vector")
    private byte[] biometricVector;

    public User(String name, String password, byte[] biometricVector) {
        this.name = name;
        this.password = password;
        this.biometricVector = biometricVector;
    }
}

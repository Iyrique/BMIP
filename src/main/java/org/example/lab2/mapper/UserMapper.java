package org.example.lab2.mapper;

import org.example.lab2.entity.User;
import org.example.lab2.generator.ByteArrayConverter;
import org.example.lab2.rest.dto.UserDTO;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        double[] biometricVector = ByteArrayConverter.byteArrayToDoubleArray(user.getBiometricVector());
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getPassword(),
                biometricVector
        );
    }

    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        byte[] biometricVector = ByteArrayConverter.doubleArrayToByteArray(userDTO.getBiometricVector());
        return new User(
                userDTO.getId(),
                userDTO.getName(),
                userDTO.getPassword(),
                biometricVector
        );
    }
}

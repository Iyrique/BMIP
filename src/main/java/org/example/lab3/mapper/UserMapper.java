package org.example.lab3.mapper;

import org.example.lab3.entity.User;
import org.example.lab3.generator.ByteArrayConverter;
import org.example.lab3.rest.dto.UserDTO;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        double[] biometricVector = ByteArrayConverter.byteArrayToDoubleArray(user.getBiometricVector());
        double[] minVi = ByteArrayConverter.byteArrayToDoubleArray(user.getMinVi());
        double[] maxVi = ByteArrayConverter.byteArrayToDoubleArray(user.getMaxVi());
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getSecretWord(),
                user.getPassword(),
                user.isContainsASD(),
                biometricVector,
                minVi,
                maxVi
        );
    }

    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        byte[] biometricVector = ByteArrayConverter.doubleArrayToByteArray(userDTO.getBiometricVector());
        byte[] minVi = ByteArrayConverter.doubleArrayToByteArray(userDTO.getMinVi());
        byte[] maxVi = ByteArrayConverter.doubleArrayToByteArray(userDTO.getMaxVi());
        return new User(
                userDTO.getId(),
                userDTO.getName(),
                userDTO.getSecretWord(),
                userDTO.getPassword(),
                userDTO.isContainsASD(),
                biometricVector,
                minVi,
                maxVi
        );
    }
}

package org.example.lab2.service;

import lombok.RequiredArgsConstructor;
import org.example.lab2.entity.User;
import org.example.lab2.generator.ByteArrayConverter;
import org.example.lab2.mapper.UserMapper;
import org.example.lab2.repository.UserRepository;
import org.example.lab2.rest.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(String name, String password, double[] biometricVector) {
        byte[] biometricVectorBytes = ByteArrayConverter.doubleArrayToByteArray(biometricVector);
        User user = new User(name, password, biometricVectorBytes);
        return userRepository.save(user);
    }

    public List<UserDTO> getUsersWithASDInPassword() {
        List<User> users = userRepository.findByPasswordContaining("asd");
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return UserMapper.toDTO(user);
    }
}

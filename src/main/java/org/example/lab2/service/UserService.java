package org.example.lab2.service;

import lombok.RequiredArgsConstructor;
import org.example.lab2.entity.User;
import org.example.lab2.generator.ByteArrayConverter;
import org.example.lab2.mapper.UserMapper;
import org.example.lab2.repository.UserRepository;
import org.example.lab2.rest.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final double THRESHOLD = 0.85;
    private final UserRepository userRepository;

    public User registerUser(String name, String password, double[] biometricVector) {
        byte[] biometricVectorBytes = ByteArrayConverter.doubleArrayToByteArray(biometricVector);
        User user = new User(name, password, biometricVectorBytes);
        return userRepository.save(user);
    }

    public List<UserDTO> getUsersWithASDInPassword() {
        List<User> users = userRepository.findByContainsASDTrue();
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

    public boolean authenticateUser(String name, String password, double[] biometricVector) {
        Optional<User> userOpt = userRepository.findByNameAndPassword(name, password);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            double[] storedBiometricVector = ByteArrayConverter.byteArrayToDoubleArray(user.getBiometricVector());

            double similarity = calculateSimilarity(biometricVector, storedBiometricVector);
            return similarity >= THRESHOLD;
        }
        return false;
    }

    private double calculateSimilarity(double[] vector1, double[] vector2) {
        if (vector1.length != vector2.length) {
            throw new IllegalArgumentException("Biometric vectors must be of the same length");
        }
        double sum = 0.0;
        for (int i = 0; i < vector1.length; i++) {
            sum += Math.pow(vector1[i] - vector2[i], 2);
        }
        double distance = Math.sqrt(sum);
        double maxPossibleDistance = Math.sqrt(vector1.length);
        return 1.0 - (distance / maxPossibleDistance);
    }
}

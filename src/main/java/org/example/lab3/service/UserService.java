package org.example.lab3.service;

import lombok.RequiredArgsConstructor;
import org.example.lab3.entity.User;
import org.example.lab3.generator.ByteArrayConverter;
import org.example.lab3.generator.UserGenerator;
import org.example.lab3.mapper.UserMapper;
import org.example.lab3.repository.UserRepository;
import org.example.lab3.rest.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final double THRESHOLD = 0.25;
    private static final double COEFFICIENT_STUDENT = 127.3;
    private final UserRepository userRepository;

    public User registerUser(String name, String password, String secretWord, double[] biometricVector) {
        byte[] biometricVectorBytes = ByteArrayConverter.doubleArrayToByteArray(biometricVector);
        double a = biometricVector[0];
        double b = biometricVector[1];
        double[] minVi = {
                a - COEFFICIENT_STUDENT * 10 * THRESHOLD >= 0 ? a - COEFFICIENT_STUDENT * 10 * THRESHOLD : 0,
                b - COEFFICIENT_STUDENT * 10 * THRESHOLD >= 0 ? b - COEFFICIENT_STUDENT * 10 * THRESHOLD : 0
        };
        double[] maxVi = {
                a + COEFFICIENT_STUDENT * 10 * THRESHOLD >= 0 ? a + COEFFICIENT_STUDENT * 10 * THRESHOLD : 0,
                b + COEFFICIENT_STUDENT * 10 * THRESHOLD >= 0 ? b + COEFFICIENT_STUDENT * 10 * THRESHOLD : 0
        };
        User user = new User(name, secretWord, password, biometricVectorBytes, ByteArrayConverter.doubleArrayToByteArray(minVi),
                ByteArrayConverter.doubleArrayToByteArray(maxVi));
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

//    public boolean authenticateUser(String name, String password, double[] biometricVector) {
//        Optional<User> userOpt = userRepository.findByNameAndPassword(name, password);
//        if (userOpt.isPresent()) {
//            User user = userOpt.get();
//            double[] storedBiometricVector = ByteArrayConverter.byteArrayToDoubleArray(user.getBiometricVector());
//
//            double similarity = calculateSimilarity(biometricVector, storedBiometricVector);
//            return similarity >= THRESHOLD;
//        }
//        return false;
//    }

    public Optional<User> authenticateUser(String name, String password, double[] vector) throws NoSuchAlgorithmException {
        Optional<User> user = userRepository.findByName(name);
        if (user.isPresent()) {
            if (user.get().getPassword().equals(UserGenerator.passwordToHash(password))) {
                final double EPS = 100;
                double a = vector[0];
                double b = vector[1];
                double[] minVi = ByteArrayConverter.byteArrayToDoubleArray(user.get().getMinVi());
                double[] maxVi = ByteArrayConverter.byteArrayToDoubleArray(user.get().getMaxVi());
                if (a + EPS >= minVi[0] && b + EPS >= minVi[1]
                        && a - EPS <= maxVi[0] && b - EPS <= maxVi[1]) {
                    return user;
                } else {
                    user.get().setId(-1L);
                    return user;
                }
            }
        }
        return Optional.empty();
    }

//    private double calculateSimilarity(double[] vector1, double[] vector2) {
//        if (vector1.length != vector2.length) {
//            throw new IllegalArgumentException("Biometric vectors must be of the same length");
//        }
//        double sum = 0.0;
//        for (int i = 0; i < vector1.length; i++) {
//            sum += Math.pow(vector1[i] - vector2[i], 2);
//        }
//        double distance = Math.sqrt(sum);
//        double maxPossibleDistance = Math.sqrt(vector1.length);
//        return 1.0 - (distance / maxPossibleDistance);
//    }
}

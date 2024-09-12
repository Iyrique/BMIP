package org.example.lab2.generator;

import lombok.RequiredArgsConstructor;
import org.example.lab2.repository.UserRepository;
import org.example.lab2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

@Component
public class DatabaseInitializer {

    private final UserService userService;
    private final UserGenerator userGenerator;
    private final UserRepository userRepository;

    @Autowired
    public DatabaseInitializer(UserService userService, UserRepository userRepository, UserGenerator userGenerator) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userGenerator = userGenerator;  // Инжектируем бин
    }

    @PostConstruct
    public void init() {

        long userCount = userRepository.count();
        if (userCount > 0) {
            System.out.println("Пользователи уже существуют в базе данных, генерация новых не требуется.");
            return;
        }

        IntStream.range(0, 100).forEach(i -> {
            String name = userGenerator.generateRandomName(8);
            String password = userGenerator.generateRandomPassword(12);
            double[] biometricVector = userGenerator.generateRandomBiometricVector(5);

            userService.registerUser(name, password, biometricVector);

            System.out.println("Generated user: Name = " + name + ", Password = " + password);
        });
    }
}

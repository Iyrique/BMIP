package org.example.lab3;

import lombok.RequiredArgsConstructor;
import org.example.lab1.Main;
import org.example.lab1.PasswordGenerator;
import org.example.lab3.entity.User;
import org.example.lab3.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ConsoleInputRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ConsoleInputRunner.class);
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Выберите действие: 1 - Регистрация, 2 - Авторизация");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                registration(scanner);
            } else if (choice == 2) {
                auth(scanner);
            } else {
                System.out.println("Неверный выбор.");
            }
        }
    }

    private void auth(Scanner scanner) throws IOException, NoSuchAlgorithmException {
        System.out.println("Введите имя пользователя: ");
        String name = scanner.next();

        System.out.println("Введите пароль пользователя: ");
        String[] args = enterPassLogin();
        String password = args[2];
        double[] vector = {Double.parseDouble(args[0]), Double.parseDouble(args[1])};

        Optional<User> userOptional = userService.authenticateUser(name, password, vector);
        if (userOptional.isEmpty()) {
            log.error("Неверное имя пользователя или пароль!");
            return;
        }
        if (userOptional.get().getId() == -1L) {
            System.out.println("Введите идентификационную фразу пользователя: ");
            String secretWord = scanner.next();
            if (!secretWord.equals(userOptional.get().getSecretWord())) {
                System.out.println("Введенные данные неверны!");
                return;
            }
        }
        System.out.println("Авторизация успешна!");
    }

    private void registration(Scanner scanner) throws IOException {
        System.out.println("Введите длину пароля:");
        int L = scanner.nextInt();
        scanner.nextLine();

        String alphabet = Main.alphabetChooser();

        String password = PasswordGenerator.generatePassword(L, alphabet);

        System.out.println("Сгенерированный пароль: " + password);

        double[] vector = enterPassRegister(password);

        System.out.println("Введите имя пользователя: ");
        String name = scanner.next();

        System.out.println("Введите фразу для идентификации: ");
        String secretWord = scanner.next();

        User user = userService.registerUser(name, password, secretWord, vector);

        System.out.println("User id: " + user.getId());
    }

    private double[] enterPassRegister(String password) throws IOException {
        int charCode;
        long startTime = System.currentTimeMillis();
        long totalKeyPressTime = 0;
        StringBuilder sb = new StringBuilder();
        do {
            if (sb.length() != 0) {
                sb.delete(0, sb.length());
                System.out.println("Неверный пароль.");
            }
            while ((charCode = System.in.read()) != '\n') {
                long keyPressStartTime = System.currentTimeMillis();
                char c = (char) charCode;
                sb.append(c);
                long keyPressEndTime = System.currentTimeMillis();
                totalKeyPressTime += (keyPressEndTime - keyPressStartTime);
            }
        } while (!password.contentEquals(sb));
        System.out.println("Пароль введен верно.");
        long endTime = System.currentTimeMillis();
        double totalTime = (endTime - startTime);
        double idleTime = (totalTime - totalKeyPressTime) / (double) password.length();

        System.out.println("Общее время ввода: " + totalTime + " мс");
        System.out.println("Время нажатия клавиш: " + totalKeyPressTime + " мс");
        System.out.println("Среднее время простоя клавиш: " + idleTime + " мс");
        return new double[]{totalTime, idleTime};
    }

    private String[] enterPassLogin() throws IOException {
        int charCode;
        long startTime = System.currentTimeMillis();
        long totalKeyPressTime = 0;
        StringBuilder sb = new StringBuilder();
        while ((charCode = System.in.read()) != '\n') {
            long keyPressStartTime = System.currentTimeMillis();
            char c = (char) charCode;
            sb.append(c);
            long keyPressEndTime = System.currentTimeMillis();
            totalKeyPressTime += (keyPressEndTime - keyPressStartTime);
        }
        long endTime = System.currentTimeMillis();
        double totalTime = (endTime - startTime);
        double idleTime = (totalTime - totalKeyPressTime) / (double) sb.length();

        System.out.println("Общее время ввода: " + totalTime + " мс");
        System.out.println("Время нажатия клавиш: " + totalKeyPressTime + " мс");
        System.out.println("Среднее время простоя клавиш: " + idleTime + " мс");
        return new String[]{Double.toString(totalTime), Double.toString(idleTime), sb.toString()};
    }
}

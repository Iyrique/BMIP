package org.example.lab3;

import lombok.RequiredArgsConstructor;
import org.example.lab1.Main;
import org.example.lab1.PasswordGenerator;
import org.example.lab3.entity.User;
import org.example.lab3.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ConsoleInputRunner implements CommandLineRunner {

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

            } else {
                System.out.println("Неверный выбор.");
            }
        }
    }


    private int hammingDistance(double[] vector1, double[] vector2) {
        int distance = 0;
        for (int i = 0; i < vector1.length; i++) {
            if (Math.abs(vector1[i] - vector2[i]) > 0.1) { // Допустимая разница между элементами
                distance++;
            }
        }
        return distance;
    }

    private void registration(Scanner scanner) throws IOException {
        System.out.println("Введите длину пароля:");
        int L = scanner.nextInt();
        scanner.nextLine();

        String alphabet = Main.alphabetChooser();

        String password = PasswordGenerator.generatePassword(L, alphabet);

        System.out.println("Сгенерированный пароль: " + password);

        double[] vector = enterPass(password);

        System.out.println("Введите имя пользователя: ");
        String name = scanner.next();

        User user = userService.registerUser(name, password, vector);

        System.out.println("User id: " + user.getId());
    }

    private double[] enterPass(String password) throws IOException {
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
        return new double[]{totalKeyPressTime, idleTime};
    }
}

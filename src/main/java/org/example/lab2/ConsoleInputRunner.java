package org.example.lab2;

import lombok.RequiredArgsConstructor;
import org.example.lab1.Main;
import org.example.lab1.MathCalculation;
import org.example.lab1.PasswordGenerator;
import org.example.lab2.entity.User;
import org.example.lab2.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ConsoleInputRunner implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите длину пароля:");
        int L = scanner.nextInt();
        scanner.nextLine();

        String alphabet = Main.alphabetChooser();

        String password = PasswordGenerator.generatePassword(L, alphabet);

        StringBuilder sb = new StringBuilder();
        long[] searchTimes;
        long[] pressTimes;
        do {
            System.out.println("Сгенерированный пароль: " + password);
            searchTimes = new long[L];
            pressTimes = new long[L];

            for (int i = 0; i < L; i++) {
                System.out.println("Введите символ " + (i + 1) + " пароля:");

                long searchStartTime = System.nanoTime();
                char input = scanner.next().charAt(0);
                long searchEndTime = System.nanoTime();

                searchTimes[i] = searchEndTime - searchStartTime;

                long pressStartTime = System.nanoTime();
                sb.append(input);
                long pressEndTime = System.nanoTime();

                pressTimes[i] = pressEndTime - pressStartTime;
            }
        } while (!password.contentEquals(sb));

        double avgSearchTime = MathCalculation.calculateExpectedTime(searchTimes);
        double avgPressTime = MathCalculation.calculateExpectedTime(pressTimes);

        System.out.println("Среднее время поиска: " + avgSearchTime / 1e9 + " секунд");
        System.out.println("Среднее время нажатия: " + avgPressTime / 1e9 + " секунд");

        double[] vector = {avgSearchTime, avgPressTime};

        System.out.println("Введите имя пользователя: ");
        String name = scanner.next();

        User user = userService.registerUser(name, password, vector);

        System.out.println("User id: " + user.getId());
    }
}

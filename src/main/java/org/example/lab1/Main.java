package org.example.lab1;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Введите длину пароля: ");
        int L = sc.nextInt();

        System.out.println("Выберите алфавит для генерации пароля:");
        System.out.println("1. Только строчные буквы (a-z)");
        System.out.println("2. Только прописные буквы (A-Z)");
        System.out.println("3. Цифры (0-9)");
        System.out.println("4. Специальные символы (!@#$%^&* и т.д.)");
        System.out.println("5. Строчные буквы и цифры (a-z, 0-9)");
        System.out.println("6. Прописные буквы и цифры (A-Z, 0-9)");
        System.out.println("7. Полный английский алфавит (a-z, A-Z)");
        System.out.println("8. Полный алфавит (a-z, A-Z, 0-9, спец. символы)");
        System.out.print("Ваш выбор (1-8): ");
        int choice = sc.nextInt();

        String alphabet;
        switch (choice) {
            case 1:
                alphabet = Alphabets.LOWERCASE_ENGLISH;
                break;
            case 2:
                alphabet = Alphabets.UPPERCASE_ENGLISH;
                break;
            case 3:
                alphabet = Alphabets.DIGITS;
                break;
            case 4:
                alphabet = Alphabets.SYMBOLS;
                break;
            case 5:
                alphabet = Alphabets.LOWERCASE_AND_DIGITS;
                break;
            case 6:
                alphabet = Alphabets.UPPERCASE_AND_DIGITS;
                break;
            case 7:
                alphabet = Alphabets.FULL_ENGLISH;
                break;
            case 8:
                alphabet = Alphabets.FULL_ALPHABET;
                break;
            default:
                System.out.println("Неправильный выбор. Используется стандартный алфавит (строчные буквы).");
                alphabet = Alphabets.LOWERCASE_ENGLISH;
                break;
        }

        String password = lab1(L, alphabet);
        System.out.println(password);

        sc.close();
    }

    private static String lab1(int L, String alphabet) {
        Scanner scanner = new Scanner(System.in);
        String pass = PasswordGenerator.generatePassword(L, alphabet);
        StringBuilder sb = new StringBuilder();
        long[] searchTimes;
        long[] pressTimes;
        do {
            System.out.println("Сгенерированный пароль: " + pass);
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
        } while (!pass.contentEquals(sb));

        double avgSearchTime = MathCalculation.calculateExpectedTime(searchTimes);
        double avgPressTime = MathCalculation.calculateExpectedTime(pressTimes);

        double searchVariance = MathCalculation.calculateVariance(searchTimes);
        double pressVariance = MathCalculation.calculateVariance(pressTimes);

        System.out.println("Среднее время поиска клавиши: " + avgSearchTime / 1e9 + " секунд");
        System.out.println("Среднее время нажатия клавиши: " + avgPressTime / 1e9 + " секунд");

        System.out.println("Дисперсия времени поиска: " + searchVariance / 1e18 + " секунд^2");
        System.out.println("Дисперсия времени нажатия: " + pressVariance / 1e18 + " секунд^2");

        return sb.toString();

    }

}

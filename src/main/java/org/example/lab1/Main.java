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
        double mu = 0.5; // Average time for typing one char
        double sigmaSquared = 0.05; // The variance of the time of entering a char

        double expectedTime = MathCalculation.calculateExpectedTime(L, mu);
        double variance = MathCalculation.calculateVariance(L, sigmaSquared);

        System.out.println("Математическое ожидание времени ввода пароля: " + expectedTime + " секунд");
        System.out.println("Дисперсия времени ввода пароля: " + variance + " секунд^2");

        return PasswordGenerator.generatePassword(L, alphabet);

    }
}

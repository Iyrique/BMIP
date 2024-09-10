package org.example.lab1;

import java.util.Random;

public class PasswordGenerator {

    public static String generatePassword(int L, String alphabet) {
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        int A = alphabet.length();

        for (int i = 0; i < L; i++) {
            int index = random.nextInt(A);
            password.append(alphabet.charAt(index));
        }

        return password.toString();
    }
}

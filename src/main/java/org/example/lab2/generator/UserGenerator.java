package org.example.lab2.generator;

import org.example.lab1.Alphabets;

import java.util.Random;

public class UserGenerator {

    private static Random random = new Random();

    private String generateRandomSeq(int length, String alphabet) {
        StringBuilder seq = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            seq.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return seq.toString();
    }


    public String generateRandomPassword(int length) {
        return generateRandomSeq(length, Alphabets.FULL_ENGLISH_AND_DIGITS);
    }

    public String generateRandomName(int length) {
        return generateRandomSeq(length, Alphabets.FULL_ENGLISH);
    }

    public double[] generateRandomBiometricVector(int size) {
        double[] vector = new double[size];
        for (int i = 0; i < size; i++) {
            vector[i] = random.nextDouble();
        }
        return vector;
    }
}

package org.example.lab3.generator;

import org.example.lab1.Alphabets;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public static String passwordToHash(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedHash);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

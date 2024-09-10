package org.example.lab1;

public class Alphabets {

    public static final String LOWERCASE_ENGLISH = "abcdefghijklmnopqrstuvwxyz";
    public static final String UPPERCASE_ENGLISH = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String DIGITS = "0123456789";
    public static final String SYMBOLS = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/";

    public static final String LOWERCASE_AND_DIGITS = LOWERCASE_ENGLISH + DIGITS;
    public static final String UPPERCASE_AND_DIGITS = UPPERCASE_ENGLISH + DIGITS;
    public static final String FULL_ENGLISH = LOWERCASE_ENGLISH + UPPERCASE_ENGLISH;
    public static final String FULL_ENGLISH_AND_DIGITS = FULL_ENGLISH + DIGITS;
    public static final String FULL_ALPHABET = FULL_ENGLISH + DIGITS + SYMBOLS;
}

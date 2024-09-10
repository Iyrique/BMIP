package org.example.lab1;

public class MathCalculation {

    public static double calculateExpectedTime(int L, double mu) {
        return L * mu;
    }

    public static double calculateVariance(int L, double sigmaSquared) {
        return L * sigmaSquared;
    }
}

package org.example.lab1;

public class MathCalculation {

    public static double calculateExpectedTime(long[] times) {
        long sum = 0;
        for (long time : times) {
            sum += time;
        }
        return (double) sum / times.length;
    }

    public static double calculateVariance(long[] times) {
        double mean = calculateExpectedTime(times);
        double variance = 0;
        for (long time : times) {
            variance += Math.pow(time - mean, 2);
        }
        return variance / times.length;
    }
}

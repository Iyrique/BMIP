package org.example.lab2.generator;

import java.io.*;

public class ByteArrayConverter {

    public static double[] byteArrayToDoubleArray(byte[] bytes) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (double[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to convert byte array to double array", e);
        }
    }

    // Преобразование double[] в byte[]
    public static byte[] doubleArrayToByteArray(double[] array) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(array);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert double array to byte array", e);
        }
    }
}

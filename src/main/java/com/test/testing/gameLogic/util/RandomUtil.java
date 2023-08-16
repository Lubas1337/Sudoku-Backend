package com.test.testing.gameLogic.util;

import java.security.SecureRandom;

public class RandomUtil {
    private static final SecureRandom random = new SecureRandom();

    public static void shuffle(int[] a) {
        for(int i = a.length-1; i > 0; i--) {
            int j = random.nextInt(i);
            swap(a, i, j);
        }
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static int randomInt(int min, int max) {
        return random.nextInt(min, max);
    }
}

package com.task.base.utils;

import net.bytebuddy.utility.RandomString;

public class VerificationUtils {

    public static String getRandomNumbers() {

        int min = 100000;
        int max = 999999999;

        int random_int_first = (int) (Math.random() * (max - min + 1) + min);

        int random_int_second = (int) (Math.random() * (max - min + 1) + min);

        String code = RandomString.make();

        return String.valueOf(random_int_first).concat(code).concat(String.valueOf(random_int_second));
    }
}

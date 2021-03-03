package com.task.base.utils;

import java.util.Date;

public class DateUtils {

    public static boolean isGreat(Date date1, Date date2) {

        return (date1.getTime() - date2.getTime()) <= 300000;
    }

}

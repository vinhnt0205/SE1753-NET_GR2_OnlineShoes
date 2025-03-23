package com.example.se1753net_gr2_onlineshoes.data.local.utils;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime(); // Convert Date to Long (milliseconds)
    }

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp); // Convert Long back to Date
    }
}

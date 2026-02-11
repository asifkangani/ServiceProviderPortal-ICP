package com.dtt.organization.util;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.UUID;

public class AppUtil {

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }



    public static String getDate(){
        SimpleDateFormat smpdate = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        Date date = new Date();
        return smpdate.format(date);
    }



    public static Date getCurrentDate() {
        return new Date();
    }


    public static String formatDate(String date){

        String datePart = date.substring(0, 10);
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate dateNew = LocalDate.parse(datePart, inputFormatter);
        return dateNew.format(outputFormatter);

    }


    public static String getDateInDDMMYY(){
        SimpleDateFormat smpdate = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
        Date date = new Date();
        return smpdate.format(date);
    }
    public static String generateLicenseKey() {
        return "LIC-" + UUID.randomUUID()
                .toString()
                .substring(0, 10)
                .toUpperCase();
    }

    public static boolean isLicenseExpired(String expiryOn) {

        if (expiryOn == null || expiryOn.isBlank()) {
            return false;
        }

        try {
            LocalDateTime expiryDate = LocalDateTime.parse(expiryOn, DATE_TIME_FORMAT);

            LocalDateTime now = LocalDateTime.now();
            return expiryDate.isBefore(now);

        } catch (DateTimeParseException e) {
            System.out.println("Invalid expiry date format: " + expiryOn);
            return false;
        }
    }










}
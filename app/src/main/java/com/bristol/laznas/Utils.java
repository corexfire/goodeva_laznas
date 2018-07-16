package com.bristol.laznas;

public class Utils {

    public static String encodeEmail(String email) {
        return email.replace(".", "%2E").replace("@", "%40");
    }
}

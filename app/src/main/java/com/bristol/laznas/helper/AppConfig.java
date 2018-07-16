package com.bristol.laznas.helper;

public class AppConfig {
    private static String live_link = "https://bsmu.or.id/api/";
    private static String dev_link = "http://bsmu.goodeva.co.id/api/";
    private static String mode_app = "PRODUCTION";

    public String api_url(){
        if (mode_app.equals("PRODUCTION")){
            return live_link;
        }else{
            return dev_link;
        }
    }
}

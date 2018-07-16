package com.bristol.laznas.helper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class ConvertRupiah {
    public String ConvertRupiah(String nominal){
        String hasil = "";

        DecimalFormat ConvertRupiah = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatAngka = new DecimalFormatSymbols();

        formatAngka.setCurrencySymbol("Rp. ");
        formatAngka.setMonetaryDecimalSeparator('#');
        formatAngka.setGroupingSeparator('.');
        ConvertRupiah.setDecimalFormatSymbols(formatAngka);

        hasil = ConvertRupiah.format(Double.valueOf(nominal)).split("#")[0];

        return hasil;
    }
    public String ConvertToDouble(String nominal){
        String hasil = "";

        hasil = nominal.replace("Rp. ", "");
        hasil = hasil.replace(".", "");
        return  hasil;
    }
}

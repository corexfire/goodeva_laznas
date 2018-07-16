package com.bristol.laznas.helper;

import java.math.BigInteger;

public class HitungPresentase {
    public int totalPresentase(String terkumpul, String target){
        float persen = (Long.valueOf(terkumpul)*100 )/ Long.valueOf(target);
        return Math.round(persen);
    }
}

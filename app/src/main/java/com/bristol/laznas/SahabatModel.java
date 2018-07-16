package com.bristol.laznas;

import java.util.ArrayList;

public class SahabatModel {

    private String mName;
    private String mTarget;
    private String mBatasWaktu;
    private String mDeskripsi;
    private String mImage;
    private String mNID;
    private String mTerkumpul;
    private boolean mOnline;

    public SahabatModel(String nid, String name, String target, String batasWaktu, String deskripsi, String image, String terkumpul) {
        mNID = nid;
        mName = name;
        mTarget = target;
        mBatasWaktu = batasWaktu;
        mDeskripsi = deskripsi;
        mImage = image;
        mTerkumpul = terkumpul;
    }

    public  String getTekumpul(){return mTerkumpul;}

    public  String getNID() {
        return mNID;
    }

    public  String getName() {
        return mName;
    }

    public  String getTarget() {
        return mTarget;
    }

    public  String getBatasWaktu() {
        return mBatasWaktu;
    }

    public  String getDeskripsi() {
        return mDeskripsi;
    }

    public  String getImage() {
        return mImage;
    }

    public static ArrayList<SahabatModel> createDonasiList(ArrayList<String> nid,
                                                          ArrayList<String> judul,
                                                          ArrayList<String> target,
                                                          ArrayList<String> batasWaktu,
                                                          ArrayList<String> deskripsi,
                                                          ArrayList<String> image,
                                                          ArrayList<String> terkumpul
    ) {

        ArrayList<SahabatModel> donasiModels = new ArrayList<SahabatModel>();
        for (int i = 0; i < judul.size(); i++) {
            donasiModels.add(new SahabatModel("" + nid.get(i),
                    "" + judul.get(i),
                    "" + target.get(i),
                    "" + batasWaktu.get(i),
                    "" + deskripsi.get(i),
                    "" + image.get(i),
                    "" + terkumpul.get(i)));

        }

        return donasiModels;
    }

}

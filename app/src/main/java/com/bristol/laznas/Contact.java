package com.bristol.laznas;

import java.util.ArrayList;

/**
 * Created by Brian Adi Kusumo on 3/13/2017.
 */

public class Contact {
    private String mName;
    private String mTarget;
    private String mBatasWaktu;
    private String mDeskripsi;
    private String mImage;
    private String mNID;
    private boolean mOnline;

    public Contact(String nid, String name, String target, String batasWaktu, String deskripsi, String image) {
        mNID = nid;
        mName = name;
        mTarget = target;
        mBatasWaktu = batasWaktu;
        mDeskripsi = deskripsi;
        mImage = image;
    }

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

    public static ArrayList<Contact> createContactsList(ArrayList<String> nid,
                                                        ArrayList<String> judul,
                                                        ArrayList<String> target,
                                                        ArrayList<String> batasWaktu,
                                                        ArrayList<String> deskripsi,
                                                        ArrayList<String> image
                                                        ) {

        ArrayList<Contact> contacts = new ArrayList<Contact>();
        for (int i = 0; i < judul.size(); i++) {
            contacts.add(new Contact("" + nid.get(i),
                    "" + judul.get(i),
                    "" + target.get(i),
                    "" + batasWaktu.get(i),
                    "" + deskripsi.get(i),
                    "" + image.get(i)))  ;

        }

        return contacts;
    }



}
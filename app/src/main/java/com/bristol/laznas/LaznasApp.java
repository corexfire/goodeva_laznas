package com.bristol.laznas;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class LaznasApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Gotham.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    private String userName;
    private String password;
    private String uid;
    private String name;
    private int flag;
    ArrayList<String> nidArray = new ArrayList<String>();
    ArrayList<String> judulArray = new ArrayList<String>();
    ArrayList<String> targetArray = new ArrayList<String>();
    ArrayList<String> batasWaktuArray = new ArrayList<String>();
    ArrayList<String> deskripsiArray = new ArrayList<String>();
    ArrayList<String> imageArray = new ArrayList<String>();
    ArrayList<String> terkumpulArray = new ArrayList<String>();



    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getNidArray() {
        return nidArray;
    }

    public ArrayList<String> getJudulArray() {
        return judulArray;
    }

    public ArrayList<String> getTargetArray() {
        return targetArray;
    }

    public ArrayList<String> getBatasWaktuArray() {
        return batasWaktuArray;
    }

    public ArrayList<String> getDeskripsiArray() {
        return deskripsiArray;
    }

    public ArrayList<String> getImageArray() {
        return imageArray;
    }
    public ArrayList<String> getTerkumpulArray() {
        return imageArray;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public  String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNidArray(ArrayList<String> nidArray) {
        this.nidArray = nidArray;
    }

    public void setJudulArray(ArrayList<String> judulArray) {
        this.judulArray = judulArray;
    }

    public void setTargetArray(ArrayList<String> targetArray) {
        this.targetArray = targetArray;
    }

    public void setBatasWaktuArray(ArrayList<String> batasWaktuArray) {
        this.batasWaktuArray = batasWaktuArray;
    }

    public void setDeskripsiArray(ArrayList<String> deskripsiArray) {
        this.deskripsiArray = deskripsiArray;
    }

    public void setImageArray(ArrayList<String> imageArray) {
        this.imageArray = imageArray;
    }

    public void setTerkumpulArray(ArrayList<String> terkumpulArray) {
        this.terkumpulArray = terkumpulArray;
    }

    public void setFlag(int flag){
        this.flag = flag;
    }

    public int getFlag(){
        return flag;
    }

}
package com.bristol.laznas.model;

import com.bristol.laznas.DonasiModel;

import java.util.ArrayList;

public class GalangDanaModel {


    private String title;
    private String desc;
    private String tanggal;
    private String target_donasi;


    private String donatur;
    private String image;
    private String id;
    private String terkumpul;

    public String getTerkumpul() {
        return terkumpul;
    }

    public void setTerkumpul(String terkumpul) {
        this.terkumpul = terkumpul;
    }


    public String getDonatur() {
        return donatur;
    }

    public void setDonatur(String donatur) {
        this.donatur = donatur;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTarget_donasi() {
        return target_donasi;
    }

    public void setTarget_donasi(String target_donasi) {
        this.target_donasi = target_donasi;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public GalangDanaModel(String id, String title, String desc, String tanggal, String target_donasi, String image, String terkumpul, String donatur) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.tanggal = tanggal;
        this.target_donasi = target_donasi;
        this.image = image;
        this.terkumpul = terkumpul;
        this.donatur = donatur;
    }

}

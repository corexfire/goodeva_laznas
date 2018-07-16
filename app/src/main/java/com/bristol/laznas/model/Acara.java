package com.bristol.laznas.model;

/**
 * Created by aditrioka on 04/10/2016.
 */

public class Acara {
    private String fb, deskripsi, harga, jam_mulai, foto, jam_selesai, kota, lokasi, nama,
            tanggal, telepon, twitter, website;
    private int kategori_id;

    public Acara() {

    }

    public Acara(String fb, String deskripsi, String harga, String jam_mulai, String foto,
                 String jam_selesai, String kota, String lokasi, String nama, String tanggal,
                 String telepon, String twitter, String website, int kategori_id) {

        this.fb = fb;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.jam_mulai = jam_mulai;
        this.foto = foto;
        this.jam_selesai = jam_selesai;
        this.kota = kota;
        this.lokasi = lokasi;
        this.nama = nama;
        this.tanggal = tanggal;
        this.telepon = telepon;
        this.twitter = twitter;
        this.website = website;
        this.kategori_id = kategori_id;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getJam_mulai() {
        return jam_mulai;
    }

    public void setJam_mulai(String jam_mulai) {
        this.jam_mulai = jam_mulai;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getJam_selesai() {
        return jam_selesai;
    }

    public void setJam_selesai(String jam_selesai) {
        this.jam_selesai = jam_selesai;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getKategori_id() {
        return kategori_id;
    }

    public void setKategori_id(int kategori_id) {
        this.kategori_id = kategori_id;
    }
}

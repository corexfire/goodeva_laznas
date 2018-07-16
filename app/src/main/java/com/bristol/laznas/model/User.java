package com.bristol.laznas.model;

/**
 * Created by aditrioka on 04/10/2016.
 */

public class User {
    private String nama, username, nomor_telepon, foto;

    public User() {

    }

    public User(String nama, String username, String password, String nomor_telepon, String foto) {
        this.nama = nama;
        this.username = username;
        this.nomor_telepon = nomor_telepon;
        this.foto = foto;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNomor_telepon() {
        return nomor_telepon;
    }

    public void setNomor_telepon(String nomor_telepon) {
        this.nomor_telepon = nomor_telepon;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}

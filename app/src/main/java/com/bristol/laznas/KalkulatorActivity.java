package com.bristol.laznas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_kalkulator)
public class KalkulatorActivity extends AppCompatActivity {

    @ViewById(R.id.btn_emas)
    LinearLayout btn_emas;
    @ViewById(R.id.btn_hasil_pertanian)
    LinearLayout btn_hasil_pertanian;
    @ViewById(R.id.btn_barang_dagangan)
    LinearLayout btn_barang_dagangan;
    @ViewById(R.id.btn_binatang_ternak)
    LinearLayout btn_binatang_ternak;
    @ViewById(R.id.btn_hasil_tambang)
    LinearLayout btn_hasil_tambang;
    @ViewById(R.id.btn_rikazz)
    LinearLayout btn_rikazz;
    @ViewById(R.id.btn_hasil_profesi)
    LinearLayout btn_hasil_profesi;
    @ViewById(R.id.btn_tabungan)
    LinearLayout btn_tabungan;
    @ViewById(R.id.btn_left)
    ImageView btn_left;

    @AfterViews
    void init() {

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_emas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ZakatOnlineActivity_.class);
                intent.putExtra("desc","Emas");
                startActivity(intent);
            }
        });

        btn_hasil_pertanian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ZakatOnlineActivity_.class);
                intent.putExtra("desc","Pertanian");
                startActivity(intent);
            }
        });

        btn_barang_dagangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ZakatOnlineActivity_.class);
                intent.putExtra("desc","Dagang");
                startActivity(intent);
            }
        });

        btn_binatang_ternak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ZakatOnlineActivity_.class);
                intent.putExtra("desc","Ternak");
                startActivity(intent);
            }
        });

        btn_hasil_tambang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ZakatOnlineActivity_.class);
                intent.putExtra("desc","Tambang");
                startActivity(intent);
            }
        });

        btn_rikazz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ZakatOnlineActivity_.class);
                intent.putExtra("desc","Rikaz");
                startActivity(intent);
            }
        });

        btn_hasil_profesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ZakatOnlineActivity_.class);
                intent.putExtra("desc","Profesi");
                startActivity(intent);
            }
        });

        btn_tabungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ZakatOnlineActivity_.class);
                intent.putExtra("desc","Tabungan");
                startActivity(intent);
            }
        });

    }
}

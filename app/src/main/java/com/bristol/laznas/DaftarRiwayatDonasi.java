package com.bristol.laznas;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

public class DaftarRiwayatDonasi extends AppCompatActivity {

    ListView lv;
    Context context;

    public static int[] fotoAcara = {R.drawable.facebook_icon, R.drawable.facebook_icon, R.drawable.facebook_icon, R.drawable.facebook_icon, R.drawable.facebook_icon};
    public static String[] namaAcara = {"Acara 1", "Acara 2", "Acara 3", "Acara 4", "Acara 5"};
    public static String[] deskripsiAcara = {"Design Sprint merupakan metodologi pengembangan produk teknologi yang dikembangkan oleh Google sejak 2012.",
            "Design Sprint merupakan metodologi pengembangan produk teknologi yang dikembangkan oleh Google sejak 2012.",
            "Design Sprint merupakan metodologi pengembangan produk teknologi yang dikembangkan oleh Google sejak 2012.",
            "Design Sprint merupakan metodologi pengembangan produk teknologi yang dikembangkan oleh Google sejak 2012.",
            "Design Sprint merupakan metodologi pengembangan produk teknologi yang dikembangkan oleh Google sejak 2012."};
    public static String[] waktuAcara = {"24 Sept 2016", "25 Sept 2016", "1 Okt 2016", "8 Okt 2016", "8 Okt 2016"};
    public static String[] hargaAcara = {"Gratis", "20K", "50K", "100K", "100K"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_donasi);

        context = this;

        lv = (ListView) findViewById(R.id.listAcara);
        lv.setAdapter(new AdapterDaftarRiwayatDonasi(this, fotoAcara, namaAcara, deskripsiAcara, waktuAcara, hargaAcara));

        String acaraUser = getIntent().getStringExtra("acaraUser");
        String kategoriAcara = getIntent().getStringExtra("kategoriAcara");
        String text = "Mencurigakan";



        //TextView toolbarText = (TextView) findViewById(R.id.toolbarText);
        //toolbarText.setText(text);
    }

//    public void toAcara(View view) {
//        Intent intent = new Intent(this, AcaraActivity_.class);
//
//        /*String tag =  (String) view.getTag();
//        intent.putExtra("acaraUser", tag);*/
//        startActivity(intent);
//    }
}
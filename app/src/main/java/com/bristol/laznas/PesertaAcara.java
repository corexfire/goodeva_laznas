package com.bristol.laznas;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class PesertaAcara extends AppCompatActivity {
    ListView lv;
    Context context;

    public static int[] fotoPeserta = {R.drawable.facebook_icon, R.drawable.facebook_icon, R.drawable.facebook_icon, R.drawable.facebook_icon, R.drawable.facebook_icon};
    public static String[] namaPeserta = {"Gohan Parningotan", "Adi Trioka", "Andhito", "Helmi Fakriaadi", "Bapak Budi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peserta_acara);

        context = this;

        lv = (ListView) findViewById(R.id.listPeserta);
        lv.setAdapter(new AdapterPeserta(this, fotoPeserta, namaPeserta));

    }
}

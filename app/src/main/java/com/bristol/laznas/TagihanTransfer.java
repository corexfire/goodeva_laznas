package com.bristol.laznas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@EActivity(R.layout.activity_tagihan_transfer)
public class TagihanTransfer extends AppCompatActivity {

    private String deskripsi;
    private String nama;
    private String batas_waktu;
    private String target;
    private String gambar;
    private String donasi;
    private static final String TAG = TagihanTransfer.class.getSimpleName();

    @ViewById(R.id.tvTotal)
    TextView tvTotal;
    @ViewById(R.id.tanggalPembayaran)
    TextView tvTanggal;
    @AfterViews
    void init() {
        Intent intent = this.getIntent();
        if( getIntent().getExtras() != null){
//            deskripsi = intent.getStringExtra("deskripsi");
//            nama = intent.getStringExtra("nama");
//            batas_waktu = intent.getStringExtra("batas_waktu");
//            target = intent.getStringExtra("target");
//            gambar = intent.getStringExtra("gambar");
            donasi = intent.getStringExtra("donasi");
//            Log.v(TAG, "deskripsi of acara: " + deskripsi);
//            Log.v(TAG, "nama of acara: " + nama);
//            Log.v(TAG, "batas_waktu of acara: " + batas_waktu);
//            Log.v(TAG, "target of acara: " + target);
//            Log.v(TAG, "gambar of acara: " + gambar);
            Log.v(TAG, "donasi of acara: " + donasi);
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy, HH:mm");
            String date = df.format(Calendar.getInstance().getTime());

            Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
            Date currentTime = localCalendar.getTime();
            int currentDay = localCalendar.get(Calendar.DATE)+3;
            int currentMonth = localCalendar.get(Calendar.MONTH) + 1;
            int currentYear = localCalendar.get(Calendar.YEAR);
            //Setting titik
            int frmt = Integer.parseInt(donasi);

            DecimalFormat dfmt = new DecimalFormat();
            DecimalFormatSymbols fmts = new DecimalFormatSymbols();
            fmts.setGroupingSeparator('.');
            dfmt.setGroupingSize(3);
            dfmt.setGroupingUsed(true);
            dfmt.setDecimalFormatSymbols(fmts);
            String outputNomor = dfmt.format(frmt);

            tvTotal.setText("Rp " + outputNomor  + "");
            tvTanggal.setText(""+currentDay+"-"+currentMonth+"-"+currentYear);

        }
    }

    public void toBeranda(View view) {
        Intent intent = new Intent(this, MainActivity_.class);

        /*String tag =  (String) view.getTag();
        intent.putExtra("acaraUser", tag);*/
        startActivity(intent);
        finish();
    }
}

package com.bristol.laznas;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.Calendar;

@EActivity(R.layout.activity_konfirmasi_tagihan)
public class KonfirmasiTagihanActivity extends AppCompatActivity implements
        View.OnClickListener {

    EditText tanggalPembayaran;
    private int mYear, mMonth, mDay;

    @AfterViews
    void init() {

        tanggalPembayaran = (EditText) findViewById(R.id.tanggalPembayaran);

        tanggalPembayaran.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tanggalPembayaran.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}

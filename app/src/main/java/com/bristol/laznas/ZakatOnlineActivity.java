package com.bristol.laznas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bristol.laznas.helper.ConvertRupiah;
import com.bristol.laznas.model.JSONParser;
import com.bristol.laznas.utils.SessionManager;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EActivity(R.layout.activity_zakat_online)
public class ZakatOnlineActivity extends AppCompatActivity {
    CarouselView carouselView;
    ConvertRupiah cr;


    @ViewById(R.id.title)
    TextView title;
    @ViewById(R.id.titleZakat)
    TextView titleZakat;
    @ViewById(R.id.keterangan)
    TextView keterangan;
    @ViewById(R.id.btn_donasi)
    Button btn_donasi;
    @ViewById(R.id.btn_panduan)
    Button btn_panduan;

    @ViewById(R.id.total_zakat)
    TextView total_zakat;

    //ZakatOnline
    @ViewById(R.id.zakat_online)
    LinearLayout zakat_online;
    @ViewById(R.id.nominal)
    EditText nominal;

    //Zakat Penghasilan
    @ViewById(R.id.zakat_penghasilan)
    LinearLayout zakat_penghasilan;
    @ViewById(R.id.penghasilan)
    EditText penghasilan;
    @ViewById(R.id.pendapatan_lain)
    EditText pendapatan_lain;
    @ViewById(R.id.hutang)
    EditText hutang;

    //nishab
    @ViewById(R.id.nishab)
    LinearLayout nishab;
    @ViewById(R.id.harga_emas)
    TextView harga_emas;
    @ViewById(R.id.total_nishab)
    TextView total_nishab;
    int vharga_emas, vtotal_nishab;


    //zakat emas
    @ViewById(R.id.jumlah_emas)
    EditText jumlah_emas;
    @ViewById(R.id.zakat_emas)
    LinearLayout zakat_emas;

    //zakat rikaz
    @ViewById(R.id.rikaz)
    EditText rikaz;
    @ViewById(R.id.zakat_rikaz)
    LinearLayout zakat_rikaz;

    //zakat rikaz
    @ViewById(R.id.tabungan)
    EditText tabungan;
    @ViewById(R.id.zakat_tabungan)
    LinearLayout zakat_tabungan;

    //zakat pertanian
    @ViewById(R.id.zakat_pertanian)
    LinearLayout zakat_pertanian;
    @ViewById(R.id.hasil_panen)
    EditText hasil_panen;
    @ViewById(R.id.total_zakat_tanpa_pemeliharaan)
    TextView total_zakat_tanpa_pemeliharaan;
    @ViewById(R.id.total_zakat_pemeliharaan)
    TextView total_zakat_pemeliharaan;

    //zakat perdagangan
    @ViewById(R.id.zakat_perdagangan)
    LinearLayout zakat_perdagangan;
    @ViewById(R.id.modal)
    EditText modal;
    @ViewById(R.id.keuntungan)
    EditText keuntungan;

    //zakat ternak
    @ViewById(R.id.zakat_ternak)
    LinearLayout zakat_ternak;
    @ViewById(R.id.unta)
    EditText unta;
    @ViewById(R.id.totan_zakat_unta)
    TextView totan_zakat_unta;
    @ViewById(R.id.kambing)
    EditText kambing;
    @ViewById(R.id.totan_zakat_kambing)
    TextView totan_zakat_kambing;
    @ViewById(R.id.sapi)
    EditText sapi;
    @ViewById(R.id.totan_zakat_sapi)
    TextView totan_zakat_sapi;




    @ViewById(R.id.group_total)
    LinearLayout group_total;
    @ViewById(R.id.imgHeader)
    ImageView imgHeader;


    private String jumlahDonasi;
    private String nid;
    String namaDonatur;
    SessionManager sessionManager;

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    String desc;
    private static final String ORDER_URL = "https://bsmu.or.id/api/laznas/mobile/order";
    private static final String IMAGE_URL = "https://bsmu.or.id/api/laznas/mobile/image";
    List<String> imageList = new ArrayList<String>();
    private static final String TAG = ZakatOnlineActivity.class.getSimpleName();
    @ViewById(R.id.btn_zakat_sekarang)
    Button btn_zakat_sekarang;
    ConvertRupiah convertRupiah;
    View rootViewBeranda;
    int[] sampleImages = {R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4};
    @AfterViews
    void init() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(total_zakat.getWindowToken(), 0);
        carouselView = (CarouselView) findViewById(R.id.carouselView);
        convertRupiah = new ConvertRupiah();
        vharga_emas = 534649;
        vtotal_nishab = 85*534649;
        harga_emas.setText(convertRupiah.ConvertRupiah(String.valueOf(vharga_emas)));
        total_nishab.setText(convertRupiah.ConvertRupiah(String.valueOf(vtotal_nishab)));

        sessionManager = new SessionManager(getApplicationContext());
        String[] header = getResources().getStringArray(R.array.panduan_header);
        String[] keterangans = getResources().getStringArray(R.array.panduan_description);
        nid = "137";
        Intent intent = this.getIntent();
        if( getIntent().getExtras() != null){
            desc = intent.getStringExtra("desc");
            switch (desc){
                case "Zakat Online":
                    //todo: zakat online

                    zakat_online.setVisibility(View.VISIBLE);
                    group_total.setVisibility(View.GONE);
                    keterangan.setText(keterangans[0]);
                    title.setText(header[0]);
                    titleZakat.setText(header[0]);

                    break;
                case "Fidyah":
                    //todo: fidyah
                    keterangan.setText(keterangans[1]);
                    title.setText(header[1]);
                    titleZakat.setText(header[1]);
                    break;
                case "Tabungan":
                    //todo: tabungan
                    carouselView.setVisibility(View.GONE);
                    imgHeader.setVisibility(View.VISIBLE);
                    imgHeader.setImageDrawable(getResources().getDrawable(R.drawable.tabungan));
                    nishab.setVisibility(View.VISIBLE);
                    zakat_tabungan.setVisibility(View.VISIBLE);
                    keterangan.setText(keterangans[2]);
                    title.setText(header[2]);
                    titleZakat.setText(header[2]);
                    tabungan.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            total_zakat.setText(convertRupiah.ConvertRupiah(totalZakatTabungan()) );
                        }
                    });
                    break;
                case "Emas":
                    //todo: emas
                    carouselView.setVisibility(View.GONE);
                    imgHeader.setVisibility(View.VISIBLE);
                    imgHeader.setImageDrawable(getResources().getDrawable(R.drawable.gold));
                    nishab.setVisibility(View.VISIBLE);
                    zakat_emas.setVisibility(View.VISIBLE);
                    keterangan.setText(keterangans[3]);
                    title.setText(header[3]);
                    titleZakat.setText(header[3]);
                    jumlah_emas.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(hasFocus){
                                if(jumlah_emas.getText().toString().equals("0"))
                                    jumlah_emas.setText("");
                            }else{
                                if(jumlah_emas.getText().toString().equals(""))
                                    jumlah_emas.setText("0");
                            }
                        }
                    });
                    jumlah_emas.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            total_zakat.setText(convertRupiah.ConvertRupiah(total_zakat_emas()) );
                        }
                    });
                    break;
                case "Ternak":
                    //todo: ternak
                    carouselView.setVisibility(View.GONE);
                    imgHeader.setVisibility(View.VISIBLE);
                    imgHeader.setImageDrawable(getResources().getDrawable(R.drawable.ternak));
                    group_total.setVisibility(View.GONE);
                    btn_zakat_sekarang.setVisibility(View.GONE);
                    zakat_ternak.setVisibility(View.VISIBLE);
                    keterangan.setText(keterangans[4]);
                    title.setText(header[4]);
                    titleZakat.setText(header[4]);
                    unta.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            int total_unta;
                            if(unta.getText().toString().equals(""))
                                total_unta = 0;
                            else
                                total_unta = Integer.valueOf(unta.getText().toString());
                            totan_zakat_unta.setText(hitung_zakat_unta(total_unta));
                        }
                    });
                    kambing.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            int total_kambing;
                            if(kambing.getText().toString().equals(""))
                                total_kambing = 0;
                            else
                                total_kambing = Integer.valueOf(kambing.getText().toString());
                            totan_zakat_kambing.setText(hitung_zakat_kambing(total_kambing));
                        }
                    });
                    sapi.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            int total_sapi;
                            if(sapi.getText().toString().equals(""))
                                total_sapi = 0;
                            else
                                total_sapi = Integer.valueOf(sapi.getText().toString());
                            totan_zakat_sapi.setText(hitung_zakat_sapi(total_sapi));
                        }
                    });
                    break;
                case "Pertanian":
                    //todo: pertanian
                    carouselView.setVisibility(View.GONE);
                    imgHeader.setVisibility(View.VISIBLE);
                    imgHeader.setImageDrawable(getResources().getDrawable(R.drawable.pertanian));
                    group_total.setVisibility(View.GONE);
                    btn_zakat_sekarang.setVisibility(View.GONE);
                    zakat_pertanian.setVisibility(View.VISIBLE);
                    keterangan.setText(keterangans[5]);
                    title.setText(header[5]);
                    titleZakat.setText(header[5]);
                    hasil_panen.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            zakat_hasil_panen();
                        }
                    });
                    hasil_panen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(hasFocus){
                                if(hasil_panen.getText().toString().equals("0"))
                                    hasil_panen.setText("");
                            }else{
                                if(hasil_panen.getText().toString().equals(""))
                                    hasil_panen.setText("0");
                            }
                        }
                    });
                    break;
                case "Dagang":
                    //todo: dagang
                    carouselView.setVisibility(View.GONE);
                    imgHeader.setVisibility(View.VISIBLE);
                    imgHeader.setImageDrawable(getResources().getDrawable(R.drawable.dagang));
                    nishab.setVisibility(View.VISIBLE);
                    zakat_perdagangan.setVisibility(View.VISIBLE);
                    keterangan.setText(keterangans[6]);
                    title.setText(header[6]);
                    titleZakat.setText(header[6]);
                    modal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(hasFocus){
                                if(modal.getText().toString().equals(""))
                                    modal.setText("0");
                            }else {
                                if(modal.getText().toString().equals("0"))
                                    modal.setText("");
                            }
                        }
                    });
                    keuntungan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(hasFocus){
                                if(keuntungan.getText().toString().equals(""))
                                    keuntungan.setText("0");
                            }else {
                                if(keuntungan.getText().toString().equals("0"))
                                    keuntungan.setText("");
                            }
                        }
                    });
                    modal.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                            total_zakat.setText(convertRupiah.ConvertRupiah(hitung_zakat_perdaganan()));
                        }
                    });
                    keuntungan.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                            total_zakat.setText(convertRupiah.ConvertRupiah(hitung_zakat_perdaganan()));
                        }
                    });
                    break;
                case "Tambang":
                    //todo: tambang
                    carouselView.setVisibility(View.GONE);
                    imgHeader.setVisibility(View.VISIBLE);
                    imgHeader.setImageDrawable(getResources().getDrawable(R.drawable.tambang));
                    nishab.setVisibility(View.VISIBLE);
                    zakat_emas.setVisibility(View.VISIBLE);
                    keterangan.setText(keterangans[7]);
                    title.setText(header[7]);
                    titleZakat.setText(header[7]);
                    jumlah_emas.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(hasFocus){
                                if(jumlah_emas.getText().toString().equals("0"))
                                    jumlah_emas.setText("");
                            }else{
                                if(jumlah_emas.getText().toString().equals(""))
                                    jumlah_emas.setText("0");
                            }
                        }
                    });
                    jumlah_emas.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            total_zakat.setText(convertRupiah.ConvertRupiah(total_zakat_emas()) );
                        }
                    });
                    break;
                case "Rikaz":
                    //todo: rikaz
                    carouselView.setVisibility(View.GONE);
                    imgHeader.setVisibility(View.VISIBLE);
                    imgHeader.setImageDrawable(getResources().getDrawable(R.drawable.rikaaz));
                    zakat_rikaz.setVisibility(View.VISIBLE);
                    keterangan.setText(keterangans[8]);
                    title.setText(header[8]);
                    titleZakat.setText(header[8]);
                    rikaz.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            total_zakat.setText(convertRupiah.ConvertRupiah(total_zakat_rikaz()) );
                        }
                    });
                    break;
                case "Profesi":
                    //todo: profesi
                    carouselView.setVisibility(View.GONE);
                    imgHeader.setVisibility(View.VISIBLE);
                    imgHeader.setImageDrawable(getResources().getDrawable(R.drawable.profil_fix));
                    zakat_penghasilan.setVisibility(View.VISIBLE);
                    keterangan.setText(keterangans[9]);
                    title.setText(header[9]);
                    titleZakat.setText(header[9]);
                    penghasilan.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if(penghasilan.getText().toString().equals("")){
//                                penghasilan.setText("0");

                            }
                            total_zakat.setText(convertRupiah.ConvertRupiah(totalZakat()) );
                        }
                    });
                    pendapatan_lain.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if(pendapatan_lain.getText().toString().equals("")){
//                                pendapatan_lain.setText("0");
                            }
                            total_zakat.setText(convertRupiah.ConvertRupiah(totalZakat()) );
                        }
                    });
                    hutang.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if(hutang.getText().toString().equals("")){
//                                hutang.setText("0");
                            }
                            total_zakat.setText(convertRupiah.ConvertRupiah(totalZakat()) );
                        }
                    });
                    penghasilan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(hasFocus){
                                if(penghasilan.getText().toString().equals("0"))
                                    penghasilan.setText("");
                            }else{
                                if(penghasilan.getText().toString().equals(""))
                                    penghasilan.setText("0");
                            }
                        }
                    });
                    pendapatan_lain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(hasFocus){
                                if(pendapatan_lain.getText().toString().equals("0"))
                                    pendapatan_lain.setText("");
                            }else{
                                if(pendapatan_lain.getText().toString().equals(""))
                                    pendapatan_lain.setText("0");
                            }
                        }
                    });
                    hutang.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(hasFocus){
                                if(hutang.getText().toString().equals("0"))
                                    hutang.setText("");
                            }else{
                                if(hutang.getText().toString().equals(""))
                                    hutang.setText("0");
                            }
                        }
                    });
                    break;
                case "Donasi":
                    //todo: donasi
                    zakat_online.setVisibility(View.VISIBLE);
                    group_total.setVisibility(View.GONE);
                    keterangan.setText("Donasi");
                    keterangan.setVisibility(View.INVISIBLE);
                    title.setText("Donasi Online");
                    titleZakat.setText("Donasi Online");
                    break;
                case "Infaq":
                    //todo: infaq
                    zakat_online.setVisibility(View.VISIBLE);
                    group_total.setVisibility(View.GONE);
                    keterangan.setText("Infaq");
                    keterangan.setVisibility(View.INVISIBLE);
                    title.setText("Infaq Online");
                    titleZakat.setText("Infaq Online");
                    btn_zakat_sekarang.setText("Infaq Sekarang");
                    break;
            }
        }

        Slider();
        cr = new ConvertRupiah();
        btn_panduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PanduanActivity_.class);
                startActivity(intent);
            }
        });
        btn_zakat_sekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!nominal.getText().toString().contains("Rp."))
//                    nominal.setText(cr.ConvertRupiah(nominal.getText().toString()));
//                else
//                    nominal.setText(cr.ConvertToDouble(nominal.getText().toString()));
                switch (desc){
                    case "Zakat Online":
                        //todo: zakat online
                        jumlahDonasi = nominal.getText().toString();
                        break;
                    case "Fidyah":
                        jumlahDonasi = nominal.getText().toString();
                        break;
                    case "Tabungan":
                        jumlahDonasi = convertRupiah.ConvertToDouble(total_zakat.getText().toString()) ;
                        break;
                    case "Emas":
                        jumlahDonasi = convertRupiah.ConvertToDouble(total_zakat.getText().toString()) ;
                        break;
                    case "Ternak":

                        break;
                    case "Pertanian":

                        break;
                    case "Dagang":
                        jumlahDonasi = convertRupiah.ConvertToDouble(total_zakat.getText().toString()) ;
                        break;
                    case "Tambang":
                        jumlahDonasi = convertRupiah.ConvertToDouble(total_zakat.getText().toString()) ;
                        break;
                    case "Rikaz":
                        jumlahDonasi = convertRupiah.ConvertToDouble(total_zakat.getText().toString()) ;
                        break;
                    case "Profesi":
                        jumlahDonasi = convertRupiah.ConvertToDouble(total_zakat.getText().toString()) ;
                        break;
                    case "Donasi":
                        jumlahDonasi = nominal.getText().toString();
                        break;
                    case "Infaq":
                        jumlahDonasi = nominal.getText().toString();
                        break;
                }
                jumlahDonasi = nominal.getText().toString();
                boolean validDonasi = isDonasiValid(jumlahDonasi);
                if (validDonasi) {
//                    if(txtNamaUser.getText().equals("")){
//                        Toast.makeText(AcaraActivityLaznas.this, "Masukkan Nama Anda",
//                                Toast.LENGTH_SHORT).show();
//                    }else {

                        new AttemptBayar().execute();
//                    }

            /*Intent intent = new Intent(this, BeliTiketActivityLaznas_.class);
            //String tag =  (String) view.getTag();
            //intent.putExtra("acaraUser", tag);
            intent.putExtra("deskripsi", deskripsi);
            intent.putExtra("nama", nama);
            intent.putExtra("batas_waktu", batas_waktu);
            intent.putExtra("target", target);
            intent.putExtra("gambar", gambar);
            intent.putExtra("donasi", jumlahDonasi);
            startActivity(intent);*/
                }
                else{
                    Toast.makeText(ZakatOnlineActivity.this, "Masukkan nominal Donasi (minimal 10.000)",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        nominal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                if(!hasFocus){
//                    nominal.setText(cr.ConvertRupiah(nominal.getText().toString()));
//                }else{
////                    nominal.setText(cr.ConvertToDouble(nominal.getText().toString()));
//                }
            }
        });

        nominal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!nominal.getText().equals("")){
//                    if(nominal.getText().toString().contains("Rp.")) {
//                        nominal.setText(cr.ConvertToDouble(nominal.getText().toString()));
//                        nominal.setSelection(nominal.getText().length());
//                    }
//                }
            }
        });



    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                BitmapFactory.Options options=new BitmapFactory.Options();
                options.inSampleSize=3; //decrease decoded image
                mIcon11 = BitmapFactory.decodeStream(in,null,options);
                mIcon11.compress(Bitmap.CompressFormat.JPEG, 50, out);
//                Picasso.with(getApplicationContext()).load(urldisplay).resize(631,346).into(imageView);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, final ImageView imageView) {
            new ZakatOnlineActivity.DownloadImageTask(imageView).execute(imageList.get(position));
        }
    };

    private class AttemptSlider extends AsyncTask<String, String, String> {

        ProgressDialog pDialogProfil3;
        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;
        //ProgressDialog dialog = new ProgressDialog(getParentFragment().getContext(),ProgressDialog.STYLE_SPINNER);
        // The variable is moved here, we only need it here while displaying the
        // progress dialog.
        TextView txtView;
        View view1;
        Activity mContex;
        public AttemptSlider(FragmentActivity activity, View rootView) {
            mContex = activity;
            view1 = rootView;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialog mAuthProgressDialog;
            String nama = mContex.getLocalClassName();
            Log.d("class", nama);
//            pDialogProfil3 = new ProgressDialog(view1.getContext());
//            //pDialog.setMessage("Loading...");
//            pDialogProfil3.setMessage("Mohon Tunggu...");
//            pDialogProfil3.setIndeterminate(false);
//            pDialogProfil3.setCancelable(true);
//            pDialogProfil3.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // Auto-generated method stub
            // Check for success tag
            int success;


            //--Perhatikan json baik-baik

            Log.d("request 2!", "starting");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting product details by making HTTP request


            Log.d("request 3!", "starting");

            //List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting product details by making HTTP request

            JSONObject json = jsonParser.makeHttpRequest(
                    IMAGE_URL, "POST", params);


            // check your log for json response
            // json success tag
            //success = json.getInt(TAG_SUCCESS);
            int img = 0;
            if (json != null) {

                int i = 0;
                int j = 1;


                for(i = 0; i<j;i++){
                    try {
                        String nid = json.getJSONObject(Integer.toString(i)).getString("nid");
                        String url = json.getJSONObject(Integer.toString(i)).getString("url");
                        String filename = json.getJSONObject(Integer.toString(i)).getString("filename");
                        String filesize = json.getJSONObject(Integer.toString(i)).getString("filesize");
                        String status = json.getJSONObject(Integer.toString(i)).getString("status");
                        String filemime = json.getJSONObject(Integer.toString(i)).getString("filemime");

                        Log.d("url", url);
                        imageList.add(url);
                        j++;
                    }
                    catch (JSONException e) {
                        i=j;
                    }
                }



                //((LaznasApp) MasukActivityLaznas.this.getApplication()).setUid(uid);
                //pDialogProfil3.dismiss();
            } else {
//                Log.d("Login attempt Front", "Login Failed");
            }

            //}
            //catch (JSONException e) {
            //e.printStackTrace();
            //}


            return null;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            //pDialogProfil3.dismiss();
            //Perform all UI changes here
            //tvNama.setText(name);

            carouselView.setImageListener(imageListener);
            carouselView.setPageCount(imageList.size());




        }




    }

    public void Slider()
    {
        new ZakatOnlineActivity.AttemptSlider(this, rootViewBeranda).execute("");
    }

    private String hitung_zakat_unta(int jumlah_unta){
        String total ="";
        if(jumlah_unta >= 5 && jumlah_unta<=9){
            total = "1 ekor kambing";
        }else if (jumlah_unta >=10  && jumlah_unta <= 14){
            total = "2 ekor kambing";
        }else if (jumlah_unta >=15  && jumlah_unta <= 19){
            total = "3 ekor kambing";
        }else if (jumlah_unta >=20  && jumlah_unta <= 24){
            total = "4 ekor kambing";
        }else if (jumlah_unta >=25  && jumlah_unta <= 35){
            total = "1 ekor unta sejenis usia lebih dari 1 tahun";
        }else if (jumlah_unta >=36  && jumlah_unta <= 45){
            total = "1 ekor unta sejenis usia lebih dari 2 tahun";
        }else if (jumlah_unta >=46  && jumlah_unta <= 60){
            total = "1 ekor unta sejenis usia lebih dari 3 tahun";
        }else if (jumlah_unta >=61  && jumlah_unta <= 75){
            total = "1 ekor unta sejenis usia lebih dari 4 tahun";
        }else if (jumlah_unta >=76  && jumlah_unta <= 90){
            total = "2 ekor unta sejenis usia lebih dari 2 tahun";
        }else if (jumlah_unta >=91  && jumlah_unta <= 120){
            total = "2 ekor unta sejenis usia lebih dari 3 tahun";
        }else if (jumlah_unta >=121){
            total = "2 ekor unta sejenis usia lebih dari 4 tahun";
            int unta_lebih = jumlah_unta;
            int tambahan_unta = 0;
            do{
                if(unta_lebih >= 126){
                    tambahan_unta = tambahan_unta + 1;
                unta_lebih = unta_lebih - 5;
                    total = "2 ekor unta sejenis usia lebih dari 4 tahun dan " + String.valueOf(tambahan_unta) + " ekor kambing";
                }
            }while (unta_lebih >= 126);
        }
        return total;
    }
    private String hitung_zakat_kambing(int jumlah_kambing){
        int total =0;
        if(jumlah_kambing >= 40 && jumlah_kambing<=120){
            total = 1;
        }else if(jumlah_kambing >= 121 && jumlah_kambing<=200){
            total = 2;
        }else if(jumlah_kambing >= 201 && jumlah_kambing<=399){
            total = 3;
        }else if(jumlah_kambing >= 400 && jumlah_kambing<=499){
            total = 4;
        }else if(jumlah_kambing >= 500 ){
//            total = "1 ekor kambing";
            int kambing_lebih = jumlah_kambing;
            int tambahan_kambing = 4;
            do{
                if(kambing_lebih >= 599){

                    kambing_lebih = kambing_lebih - 100;
                    tambahan_kambing = tambahan_kambing + 1;
                    total = tambahan_kambing;
                }
            }while (kambing_lebih >= 599);
        }
        return String.valueOf(total)  + " ekor kambing";
    }
    private String hitung_zakat_sapi(int jumlah_sapi){
        String total ="";
        if(jumlah_sapi >= 30 && jumlah_sapi<=39){
            total = "1 ekor usia 1 tahun";
        }else if(jumlah_sapi >= 40 && jumlah_sapi<=59){
            total = "1 ekor usia 2 tahun";
        }else if(jumlah_sapi >= 60 && jumlah_sapi<=69){
            total = "2 ekor usia 1 tahun";
        }else if(jumlah_sapi >= 70 && jumlah_sapi<=79 ){
            total = "1 ekor usia 1 tahun dan 1 ekor usia 2 tahun";
        }else if (jumlah_sapi >= 80 && jumlah_sapi<=89){
            total = "2 ekor usia 2 tahun";
        }else if (jumlah_sapi >= 90 && jumlah_sapi<=99){
            total = "3 ekor usia 1 tahun";
        }else if (jumlah_sapi >= 100 && jumlah_sapi<=119){
            total = "2 ekor usia 1 tahun dan 1 ekor usia 2 tahun";
        }else if(jumlah_sapi >= 120){
            total = "untuk setiap tambahan 30 ekor = 1 ekor usia 1 tahun dan setiap 40 ekor = 1 ekor usia 2 tahun";
        }
        return total;
    }
    private String hitung_zakat_perdaganan(){
        int total_perdagangan;
        String vModal, vKeuntungan;
        if(modal.getText().toString().equals(""))
            vModal = "0";
        else
            vModal = modal.getText().toString();
        if(keuntungan.getText().toString().equals(""))
            vKeuntungan = "0";
        else
            vKeuntungan = keuntungan.getText().toString();
        total_perdagangan = Integer.valueOf(vModal) + Integer.valueOf(vKeuntungan);
        if(total_perdagangan > vtotal_nishab){
            long total;
            total = Long.valueOf(Math.round(total_perdagangan * 0.025));
            return String.valueOf(total);
        }else
            return "0";
    }
    private void zakat_hasil_panen(){
        int total_panen;
        if(hasil_panen.getText().toString().equals("") || hasil_panen.getText().toString().equals("0")){
            total_zakat_pemeliharaan.setText("0");
            total_zakat_tanpa_pemeliharaan.setText("0");
        }else{
            total_panen = Integer.valueOf(hasil_panen.getText().toString());
            if(total_panen >= 620) {
                long total_pemeliharaan, total_tanpa_pemeliharaan;
                total_pemeliharaan = Long.valueOf(Math.round(total_panen * 0.05));
                total_tanpa_pemeliharaan = Long.valueOf(Math.round(total_panen * 0.1));
                total_zakat_pemeliharaan.setText(String.valueOf(total_pemeliharaan));
                total_zakat_tanpa_pemeliharaan.setText(String.valueOf(total_tanpa_pemeliharaan));
            }else{
                total_zakat_pemeliharaan.setText("0");
                total_zakat_tanpa_pemeliharaan.setText("0");
            }
        }
    }
    private String totalZakatTabungan(){
        int tabungans;
        if(tabungan.getText().toString().equals(""))
            tabungans= 0;
        else
            tabungans = Integer.valueOf(tabungan.getText().toString());
        long total;
        total = Long.valueOf( Math.round(tabungans *0.025));

        if(total < 0)
            return "0" ;
        else
            return String.valueOf( total);
    }
    private String totalZakat(){
        int penghasilans, pendapatan_lains, hutangs;
        if(penghasilan.getText().toString().equals(""))
            penghasilans= 0;
        else
            penghasilans = Integer.valueOf(penghasilan.getText().toString());
        if(pendapatan_lain.getText().toString().equals(""))
            pendapatan_lains =0;
        else
            pendapatan_lains = Integer.valueOf(pendapatan_lain.getText().toString());
        if(hutang.getText().toString().equals(""))
            hutangs = 0;
        else
            hutangs = Integer.valueOf(hutang.getText().toString());

        long total;
        total = Long.valueOf( Math.round(((penghasilans + pendapatan_lains) - (hutangs))*0.025));

        if(total < 0)
        return "0" ;
        else
            return String.valueOf( total);
    }
    private String total_zakat_rikaz(){
        String jumlah;
        if(rikaz.getText().toString().equals(""))
            jumlah = "0";
        else
            jumlah = rikaz.getText().toString();

            long total;
            total = Long.valueOf(Math.round(Integer.valueOf(jumlah) *0.2));
            return String.valueOf(total);

    }
    private String total_zakat_emas(){
        String jumlah;
        if(jumlah_emas.getText().toString().equals(""))
            jumlah = "0";
        else
            jumlah = jumlah_emas.getText().toString();
        if( Integer.valueOf(jumlah) < 85 ){
            return "0";
        }else{
            long total;
            total = Long.valueOf(Math.round((Integer.valueOf(jumlah_emas.getText().toString()) * vharga_emas)*0.025));
            return String.valueOf(total);
        }
    }
    private class AttemptBayar extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ZakatOnlineActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;

            //String user = "brian";
            //String pass = "123456";
            String nid_attempt = nid;
            String nominal = jumlahDonasi+"00";

//            String uid = ((LaznasApp) getApplication()).getUid();
            String uid = sessionManager.getUID();
            String donatur = sessionManager.getName();
            Log.v(TAG, "uid: " + uid);

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("nid", nid_attempt));
//                params.add(new BasicNameValuePair("uid", uid));
                params.add(new BasicNameValuePair("nominal", nominal));
                params.add(new BasicNameValuePair("donatur", donatur));
                //params.add(new BasicNameValuePair("nominal", nominal));
                Log.d("nid", nid);
                Log.d("nominal", nominal);
                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        ORDER_URL, "POST", params);

                // check your log for json response
                Log.d("Json = ", json.toString());

                // json success tag
                //success = json.getInt(TAG_SUCCESS);
                //success = 0;
                if (json != null) {
                    Log.d("Login attempt", json.toString());

                    String order_id = json.get("order_number").toString();
                    int nomorPembayaran = Integer.parseInt(order_id);
                    nomorPembayaran += 300000000;
                    order_id = Integer.toString(nomorPembayaran);
                    Log.d("Order id = ", order_id);
                    pDialog.dismiss();
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    Date date1 = new Date();
                    Intent intent = new Intent(ZakatOnlineActivity.this, BeliTiketActivityLaznas_.class);
                    /*String tag =  (String) view.getTag();
                    intent.putExtra("acaraUser", tag);*/
                    intent.putExtra("deskripsi", keterangan.getText().toString());
                    intent.putExtra("nama", title.getText().toString());
                    intent.putExtra("batas_waktu", format.format(date1));
                    intent.putExtra("target", jumlahDonasi);
                    intent.putExtra("gambar", "https:\\/\\/bsmu.or.id\\/sites\\/default\\/files\\/infaq%20online.jpeg");
                    intent.putExtra("donasi", jumlahDonasi);
                    intent.putExtra("nomor_pembayaran", order_id);
                    intent.putExtra("uid", uid);
                    intent.putExtra("donatur", uid);
                    intent.putExtra("nid", nid);

                    startActivity(intent);

                } else {
                    pDialog.dismiss();
                    Log.d("Login attempt Front", "Failed");
                    Toast.makeText(ZakatOnlineActivity.this, "gagal",
                            Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            //pDialog.dismiss();



        }

    }
    private boolean isDonasiValid(String donasi) {
        if (donasi.length() < 5) {

            nominal.setError("Input tidak valid");
            nominal.setFocusableInTouchMode(true);
            nominal.setFocusable(true);
            nominal.requestFocus();

            return false;
        }
        int jumlah = Integer.parseInt(donasi);
        if(jumlah<10000){
            nominal.setError("Input tidak valid");
            nominal.setFocusableInTouchMode(true);
            nominal.setFocusable(true);
            nominal.requestFocus();
            return false;
        }
        return true;
    }
}

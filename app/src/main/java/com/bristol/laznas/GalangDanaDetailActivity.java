package com.bristol.laznas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bristol.laznas.helper.ConvertRupiah;
import com.bristol.laznas.helper.HitungPresentase;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@EActivity(R.layout.activity_galang_dana_detail)
public class GalangDanaDetailActivity extends AppCompatActivity {

    @ViewById(R.id.title_detail)
    TextView title_detail;
    @ViewById(R.id.tanggal)
    TextView tanggal;
    @ViewById(R.id.desc)
    TextView desc;
    @ViewById(R.id.target_donasi)
    TextView target_donasi;
    @ViewById(R.id.terkumpul)
    TextView terkumpul;
    @ViewById(R.id.sisa_hari)
    TextView sisa_hari;
    @ViewById(R.id.donasi)
    EditText donasi;
    @ViewById(R.id.btn_donasi)
    Button btn_donasi;
    @ViewById(R.id.img)
    ImageView img;
    @ViewById(R.id.persentaseProgress)
    TextView persentaseProgress;
    @ViewById(R.id.progressBar)
    ProgressBar progressBar;
    @ViewById(R.id.donatur)
    TextView donatur;
    ConvertRupiah convertRupiah;
    HitungPresentase hitungPresentase = new HitungPresentase();
    String lzId, lzTitle, lzDesc, lzTanggal, lzImg, lzTarget,lzTerkumpul, lzDonatur;

    @AfterViews
    void init() {
        convertRupiah = new ConvertRupiah();
        Intent intent = this.getIntent();
        if( getIntent().getExtras() != null){
            lzId = intent.getStringExtra("id");
            lzTitle = intent.getStringExtra("title");
            lzDesc = intent.getStringExtra("desc");
            lzTanggal = intent.getStringExtra("tanggal");
            lzImg = intent.getStringExtra("img");
            lzTarget = intent.getStringExtra("target");
            lzTerkumpul = intent.getStringExtra("terkumpul");
            lzDonatur = intent.getStringExtra("donatur");


            desc.setText(lzDesc);
            target_donasi.setText(convertRupiah.ConvertRupiah(lzTarget) );
            title_detail.setText(lzTitle);
            tanggal.setText(lzTanggal);
            donatur.setText(lzDonatur);
            terkumpul.setText(convertRupiah.ConvertRupiah(lzTerkumpul));
            int persen = hitungPresentase.totalPresentase(lzTerkumpul,lzTarget);
            persentaseProgress.setText(String.valueOf(persen)+"%");
            progressBar.setMax(100);
            progressBar.setProgress(persen);
            String newTanggal = lzTanggal.replace("-","/");
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date date1 = format.parse(newTanggal);
                Date date2 = new Date();
                long diff = date1.getTime() - date2.getTime();
                sisa_hari.setText(String.valueOf(diff / (24 * 60 * 60 * 1000)));
                tanggal.setText(String.valueOf(format.format(date1)));
            }catch (Exception e){
                e.printStackTrace();
            }

//            Picasso.with(getApplicationContext()).load(lzImg).into(img);
            Picasso.with(getApplicationContext()).load(lzImg).resize(631,346).into(img);
//            new DownloadImageTask(img).execute(lzImg);
        }


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


}

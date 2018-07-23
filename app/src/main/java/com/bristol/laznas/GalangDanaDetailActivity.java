package com.bristol.laznas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bristol.laznas.helper.AppConfig;
import com.bristol.laznas.helper.ConvertRupiah;
import com.bristol.laznas.helper.HitungPresentase;
import com.bristol.laznas.model.JSONParser;
import com.bristol.laznas.utils.SessionManager;
import com.squareup.picasso.Picasso;

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

    private String jumlahDonasi;
    ConvertRupiah convertRupiah;
    HitungPresentase hitungPresentase = new HitungPresentase();
    String lzId, lzTitle, lzDesc, lzTanggal, lzImg, lzTarget,lzTerkumpul, lzDonatur;
    SessionManager sessionManager;

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    AppConfig appConfig = new AppConfig();
    private static final String ORDER_URL = "laznas/mobile/order";

    @AfterViews
    void init() {
        appConfig = new AppConfig();
        convertRupiah = new ConvertRupiah();
        sessionManager = new SessionManager(this);
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

            btn_donasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jumlahDonasi = donasi.getText().toString();
                    boolean validDonasi = isDonasiValid(jumlahDonasi);

                    if (validDonasi) {

                        new GalangDanaDetailActivity.AttemptGalang().execute();
            /*Intent intent = new Intent(ZakatActivityLaznas.this, DetailPembayaranZakatActivity_.class);
            intent.putExtra("deskripsi", deskripsi);
            intent.putExtra("nama", nama);
            intent.putExtra("batas_waktu", batas_waktu);
            intent.putExtra("target", target);
            intent.putExtra("gambar", gambar);
            intent.putExtra("donasi", jumlahDonasi);
            startActivity(intent);*/
                    }
                    else{

                        Toast.makeText(GalangDanaDetailActivity.this, "Masukkan nominal Donasi (minimal 10.000)",
                                Toast.LENGTH_SHORT).show();

                    }
                }
            });

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

    private boolean isDonasiValid(String dons) {
        if (dons.length() < 5) {

            donasi.setError("Input tidak valid");
            donasi.setFocusableInTouchMode(true);
            donasi.setFocusable(true);
            donasi.requestFocus();
            return false;
        }
        int jumlah = Integer.parseInt(dons);
        if(jumlah<10000){
            donasi.setError("Input tidak valid");
            donasi.setFocusableInTouchMode(true);
            donasi.setFocusable(true);
            donasi.requestFocus();
            return false;
        }
        return true;
    }

    private class AttemptGalang extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(GalangDanaDetailActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            //  Auto-generated method stub
            // Check for success tag
            int success;

            //String user = "brian";
            //String pass = "123456";
            String nid_attempt = lzId;
            String nominal = jumlahDonasi+"00";
//            String uid = ((LaznasApp) getApplication()).getUid();
            String uid = sessionManager.getUID();

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("nid", nid_attempt));
                params.add(new BasicNameValuePair("uid", uid));
                params.add(new BasicNameValuePair("nominal", nominal));
                params.add(new BasicNameValuePair("donatur", sessionManager.getName()));
                //params.add(new BasicNameValuePair("nominal", nominal));
//                Log.d("nid", nid);
                Log.d("nominal", nominal);
                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        appConfig.api_url() + ORDER_URL, "POST", params);

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

                    Intent intent = new Intent(GalangDanaDetailActivity.this, BeliTiketActivityLaznas_.class);
                    /*String tag =  (String) view.getTag();
                    intent.putExtra("acaraUser", tag);*/
                    intent.putExtra("deskripsi", lzDesc);
                    intent.putExtra("nama", sessionManager.getName());
                    intent.putExtra("batas_waktu", lzTanggal);
                    intent.putExtra("target", lzTarget);
                    intent.putExtra("gambar", lzImg);
                    intent.putExtra("donasi", jumlahDonasi);
                    intent.putExtra("nomor_pembayaran", order_id);

                    startActivity(intent);
                } else {
                    pDialog.dismiss();
                    Log.d("Login attempt Front", "Failed");
                    Toast.makeText(GalangDanaDetailActivity.this, "gagal",
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


}

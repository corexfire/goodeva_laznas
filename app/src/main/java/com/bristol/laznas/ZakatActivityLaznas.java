package com.bristol.laznas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bristol.laznas.helper.AppConfig;
import com.bristol.laznas.model.JSONParser;
import com.bristol.laznas.utils.SessionManager;
import com.google.firebase.database.ValueEventListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_zakat_laznas)
public class ZakatActivityLaznas extends AppCompatActivity {

    private String id;

    private String deskripsi;
    private String nama;
    private String batas_waktu;
    private String target;
    private String gambar;
   // private FirebaseDatabase database = FirebaseDatabase.getInstance();
    //private DatabaseReference ref;
    private static final String TAG = ZakatActivityLaznas.class.getSimpleName();
    private ValueEventListener acaraListener;
    SessionManager sessionManager;
    @ViewById(R.id.tv_tanggal)
    TextView tvTanggal;
    @ViewById(R.id.tv_harga)
    TextView tvHarga;
    @ViewById(R.id.tv_deskripsi)
    TextView tvDeskripsi;
    @ViewById(R.id.tv_nama_acara)
    TextView tvNamaAcara;
    @ViewById(R.id.fotoAcara)
    ImageView tvGambar;

    @ViewById(R.id.jumlahDonasiLaznas)
    EditText et_jumlahDonasi;

    private String jumlahDonasi;
    private String nid;

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    AppConfig appConfig = new AppConfig();
    private static final String ORDER_URL = "laznas/mobile/order";




    @AfterViews
    void init() {
        sessionManager = new SessionManager(getApplicationContext());
        Intent intent = this.getIntent();
        if( getIntent().getExtras() != null){
            nid = intent.getStringExtra("nid");
            deskripsi = intent.getStringExtra("deskripsi");
            nama = intent.getStringExtra("nama");
            batas_waktu = intent.getStringExtra("batas_waktu");
            target = intent.getStringExtra("target");
            gambar = intent.getStringExtra("gambar");
            Log.v(TAG, "nid: " + nid);
            Log.v(TAG, "deskripsi of acara: " + deskripsi);
            Log.v(TAG, "nama of acara: " + nama);
            Log.v(TAG, "batas_waktu of acara: " + batas_waktu);
            Log.v(TAG, "target of acara: " + target);
            Log.v(TAG, "gambar of acara: " + gambar);
            tvNamaAcara.setText(nama);
            tvDeskripsi.setText(deskripsi);
            tvHarga.setText(target);
            tvTanggal.setText(batas_waktu);
            new DownloadImageTask(tvGambar).execute(gambar);
        }

        //ref = database.getReference("/acara/" + id);
        //Log.v(TAG, "reference: " + ref);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //ref.removeEventListener(acaraListener);
    }

    public void toPesertaAcara(View view) {
        Intent intent = new Intent(this, PesertaAcara.class);
        /*String tag =  (String) view.getTag();
        intent.putExtra("acaraUser", tag);*/
        startActivity(intent);
    }

    public void toBeliTiket(View view) {
        jumlahDonasi = et_jumlahDonasi.getText().toString();
        boolean validDonasi = isDonasiValid(jumlahDonasi);

        if (validDonasi) {

            new AttemptBayar().execute();
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

            Toast.makeText(ZakatActivityLaznas.this, "Masukkan nominal Donasi (minimal 10.000)",
                    Toast.LENGTH_SHORT).show();

        }
    }

    private boolean isDonasiValid(String donasi) {
        if (donasi.length() < 5) {

            et_jumlahDonasi.setError("Input tidak valid");
            et_jumlahDonasi.setFocusableInTouchMode(true);
            et_jumlahDonasi.setFocusable(true);
            et_jumlahDonasi.requestFocus();
            return false;
        }
        int jumlah = Integer.parseInt(donasi);
        if(jumlah<10000){
            et_jumlahDonasi.setError("Input tidak valid");
            et_jumlahDonasi.setFocusableInTouchMode(true);
            et_jumlahDonasi.setFocusable(true);
            et_jumlahDonasi.requestFocus();
            return false;
        }
        return true;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
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

    //Attempt Bayar
    private class AttemptBayar extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ZakatActivityLaznas.this);
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
            String nid_attempt = nid;
            String nominal = jumlahDonasi+"00";
//            String uid = ((LaznasApp) getApplication()).getUid();
            String uid = sessionManager.getUID();
            Log.v(TAG, "uid: " + uid);

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("nid", nid_attempt));
//                params.add(new BasicNameValuePair("uid", uid));
                params.add(new BasicNameValuePair("nominal", nominal));
//                params.add(new BasicNameValuePair("donatur", nominal));
                //params.add(new BasicNameValuePair("nominal", nominal));
                Log.d("nid", nid);
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

                    Intent intent = new Intent(ZakatActivityLaznas.this, DetailPembayaranZakatActivity_.class);
                    /*String tag =  (String) view.getTag();
                    intent.putExtra("acaraUser", tag);*/
                    intent.putExtra("deskripsi", deskripsi);
                    intent.putExtra("nama", nama);
                    intent.putExtra("batas_waktu", batas_waktu);
                    intent.putExtra("target", target);
                    intent.putExtra("gambar", gambar);
                    intent.putExtra("donasi", jumlahDonasi);
                    intent.putExtra("nomor_pembayaran", order_id);

                    startActivity(intent);
                } else {
                    pDialog.dismiss();
                    Log.d("Login attempt Front", "Failed");
                    Toast.makeText(ZakatActivityLaznas.this, "gagal",
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

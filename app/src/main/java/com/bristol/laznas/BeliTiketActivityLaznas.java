package com.bristol.laznas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bristol.laznas.helper.AppConfig;
import com.bristol.laznas.helper.ConvertRupiah;
import com.bristol.laznas.model.JSONParser;
import com.bristol.laznas.utils.SessionManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_beli_tiket)
public class BeliTiketActivityLaznas extends AppCompatActivity {
    private String deskripsi;
    private String nama;
    private String batas_waktu;
    private String target;
    private String gambar;
    private String donasi;
    private String nomor_pembayaran;
    String uid, donatur, nid;

    @ViewById(R.id.waktuAcaraDetail)
    TextView tvTanggal;
    @ViewById(R.id.target)
    TextView tvTarget;
    @ViewById(R.id.judulAcara)
    TextView tvJudul;
    @ViewById(R.id.fotoAcara)
    ImageView tvGambar;
    @ViewById(R.id.idNomorPembayaran)
    TextView tvNomorPembayaran;
    @ViewById(R.id.radioTransferATM)
    RadioButton va;
    @ViewById(R.id.radioTunai)
    RadioButton rTunai;
    @ViewById(R.id.tunai)
    RelativeLayout tunai;
    @ViewById(R.id.transferAtm)
    RelativeLayout transferAtm;
    ConvertRupiah convertRupiah;
    SessionManager sessionManager;

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    AppConfig appConfig = new AppConfig();
    private static final String ORDER_URL = "laznas/mobile/order/payment";

    private static final String TAG = BeliTiketActivityLaznas.class.getSimpleName();
    @AfterViews
    void init() {
        sessionManager = new SessionManager(getApplicationContext());
//        Spinner spinner = (Spinner) findViewById(R.id.spinnerJumlahTiket);
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.jumlahTiketArray, android.R.layout.simple_spinner_item);
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);
        convertRupiah = new ConvertRupiah();
        va.setChecked(true);
        Intent intent = this.getIntent();
        if( getIntent().getExtras() != null){
            deskripsi = intent.getStringExtra("deskripsi");
            nama = intent.getStringExtra("nama");
            batas_waktu = intent.getStringExtra("batas_waktu");
            target = intent.getStringExtra("target");
            gambar = intent.getStringExtra("gambar");
            donasi = intent.getStringExtra("donasi");
            uid = intent.getStringExtra("uid");
            donatur = intent.getStringExtra("donatur");
            nid = intent.getStringExtra("nid");
            nomor_pembayaran = intent.getStringExtra("nomor_pembayaran");
            Log.v(TAG, "deskripsi of acara: " + deskripsi);
            Log.v(TAG, "nama of acara: " + nama);
            Log.v(TAG, "batas_waktu of acara: " + batas_waktu);
            Log.v(TAG, "target of acara: " + target);
            Log.v(TAG, "gambar of acara: " + gambar);
            Log.v(TAG, "donasi of acara: " + donasi);
            tvJudul.setText(nama);
            tvTarget.setText(convertRupiah.ConvertRupiah(target) );
            tvTanggal.setText(batas_waktu);
            tvNomorPembayaran.setText(nomor_pembayaran);
            if(nama.toLowerCase().contains("gerai")){
                tunai.setVisibility(View.VISIBLE);
                transferAtm.setVisibility(View.GONE);
                rTunai.setChecked(true);
            }else{
                tunai.setVisibility(View.GONE);
                transferAtm.setVisibility(View.VISIBLE);
                va.setChecked(true);
            }
            new DownloadImageTask(tvGambar).execute(gambar);

        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioTransferATM:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }

    public void toTagihan(View view) {

        new AttemptBayar().execute();

        /*Intent intent = new Intent(this, TagihanActivity_.class);
        intent.putExtra("deskripsi", deskripsi);
        intent.putExtra("nama", nama);
        intent.putExtra("batas_waktu", batas_waktu);
        intent.putExtra("target", target);
        intent.putExtra("gambar", gambar);
        intent.putExtra("donasi", donasi);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);*/
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
            pDialog = new ProgressDialog(BeliTiketActivityLaznas.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // Auto-generated method stub
            // Check for success tag
            int success;

            //String user = "brian";
            //String pass = "123456";
            //String nid_attempt = nid;
            //String nominal = jumlahDonasi+"00";
            String uid = sessionManager.getUID();
            String donatur = sessionManager.getName();
            Log.v(TAG, "uid: " + uid);

            //try {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //params.add(new BasicNameValuePair("nid", nid_attempt));
            params.add(new BasicNameValuePair("nominal", donasi));
            params.add(new BasicNameValuePair("nid", nid));
            params.add(new BasicNameValuePair("donatur", donatur));
            //Log.d("nid", nid);
            //Log.d("nominal", nominal);
            Log.d("uid =", uid);
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
                //Log.d("Login attempt", json.toString());

                //String order_id = json.get("order_number").toString();
                //int nomorPembayaran = Integer.parseInt(order_id);
                //nomorPembayaran += 300000000;
                //order_id = Integer.toString(nomorPembayaran);
                //Log.d("Order id = ", order_id);
                pDialog.dismiss();

                Intent intent = new Intent(BeliTiketActivityLaznas.this, TagihanActivity_.class);
//                intent.putExtra("deskripsi", deskripsi);
//                intent.putExtra("nama", nama);
//                intent.putExtra("batas_waktu", batas_waktu);
//                intent.putExtra("target", target);
//                intent.putExtra("gambar", gambar);
                intent.putExtra("donasi", donasi);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();
            } else {
                pDialog.dismiss();
                Log.d("Login attempt Front", "Failed");
                Toast.makeText(BeliTiketActivityLaznas.this, "gagal",
                        Toast.LENGTH_SHORT).show();
            }
            //}catch (JSONException e) {
            //    e.printStackTrace();
            //}

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

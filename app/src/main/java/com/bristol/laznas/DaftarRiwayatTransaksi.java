package com.bristol.laznas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.bristol.laznas.helper.AppConfig;
import com.bristol.laznas.model.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import android.text.format.DateFormat;

public class DaftarRiwayatTransaksi extends AppCompatActivity {

    ListView lv;
    Context context;

//    public static int[] fotoAcara = {R.drawable.laznas, R.drawable.laznas, R.drawable.laznas, R.drawable.laznas, R.drawable.laznas};
//    public static String[] namaAcara = {"Acara 1", "Acara 2", "Acara 3", "Acara 4", "Acara 5"};
//    public static String[] deskripsiAcara = {"Design Sprint merupakan metodologi pengembangan produk teknologi yang dikembangkan oleh Google sejak 2012.",
//            "Design Sprint merupakan metodologi pengembangan produk teknologi yang dikembangkan oleh Google sejak 2012.",
//            "Design Sprint merupakan metodologi pengembangan produk teknologi yang dikembangkan oleh Google sejak 2012.",
//            "Design Sprint merupakan metodologi pengembangan produk teknologi yang dikembangkan oleh Google sejak 2012.",
//            "Design Sprint merupakan metodologi pengembangan produk teknologi yang dikembangkan oleh Google sejak 2012."};
//    public static String[] waktuAcara = {"24 Sept 2016", "25 Sept 2016", "1 Okt 2016", "8 Okt 2016", "8 Okt 2016"};
//    public static String[] hargaAcara = {"Gratis", "20K", "50K", "100K", "100K"};

    ArrayList<String> namaArray = new ArrayList<String>();
    ArrayList<String> hargaArray = new ArrayList<String>();
    ArrayList<String> waktuArray = new ArrayList<String>();
    ArrayList<String> deskripsiArray = new ArrayList<String>();
    ArrayList<String> imageArray = new ArrayList<String>();
    ArrayList<String> statusArray = new ArrayList<String>();

    private static final String TAG = DaftarRiwayatTransaksi.class.getSimpleName();
    JSONParser jsonParser = new JSONParser();
    AppConfig appConfig = new AppConfig();
    private String TRANSAKSI_URL = "laznas/mobile/order/riwayat";
    // Progress Dialog
    private ProgressDialog pDialog;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_transaksi);
        Intent intent = this.getIntent();
        if( getIntent().getExtras() != null){
            uid = intent.getStringExtra("id");
            Log.v(TAG, "uid : " + uid);
            Log.v(TAG, "TRANSAKSI_URL : " + TRANSAKSI_URL );
        }
        new AttempRiwayatBerita().execute();

        //TextView toolbarText = (TextView) findViewById(R.id.toolbarText);
        //toolbarText.setText(text);
    }

    public class AttempRiwayatBerita extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(DaftarRiwayatTransaksi.this);
            pDialog.setMessage("Loading Records...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... arg0) {
            updateJSONdata();
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            lv = (ListView) findViewById(R.id.listAcara);
            lv.setAdapter(new AdapterDaftarRiwayatTransaksi(DaftarRiwayatTransaksi.this, imageArray, namaArray, deskripsiArray, waktuArray, hargaArray, statusArray));
        }
    }

    /**
     * Retrieves recent post data from the server.
     */
    public void updateJSONdata() {

        // Instantiate the arraylist to contain all the JSON data.
        // we are going to use a bunch of key-value pairs, referring
        // to the json element name, and the content, for example,
        // message it the tag, and "I'm awesome" as the content..
        //mCommentList = new ArrayList<HashMap<String, String>>();
        Log.d("request 1!", "starting");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("uid",uid));
        // getting product details by making HTTP request
        JSONObject json = jsonParser.makeHttpRequest(
                appConfig.api_url() + TRANSAKSI_URL, "POST", params);

        // check your log for json response
        // json success tag
        //success = json.getInt(TAG_SUCCESS);
        //success = 0;
        if (json != null) {
            Log.d("Login attempt", json.toString());
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");

            int i = 0;
            int j = 1;

            for(i = 0; i<j;i++){
                try {
                    String id = json.getJSONObject(Integer.toString(i)).getJSONObject("product_id").getString("order_number");
                    String status = json.getJSONObject(Integer.toString(i)).getJSONObject("product_id").getString("status");
                    String total = json.getJSONObject(Integer.toString(i)).getJSONObject("product_id").getJSONObject("commerce_order_total").getJSONObject("und").getJSONObject("0").getString("amount");
                    String timestamp = json.getJSONObject(Integer.toString(i)).getJSONObject("product_id").getString("revision_timestamp");
                    long time = Long.parseLong(timestamp) * 1000L;

                    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                    cal.setTimeInMillis(time);
                    String date = DateFormat.format("dd-MM-yyyy", cal).toString();

                    Log.d("id", id);
                    Log.d("status", status);
                    Log.d("total", total);
                    Log.d("date", date);

                    if(status.contains("expired")){
                        status = "expired";
                        namaArray.add(id);
                        statusArray.add(status);
                        hargaArray.add(total);
                        waktuArray.add(date);
                    }
                    if(status.contains("pending")){
                        namaArray.add(id);
                        statusArray.add(status);
                        hargaArray.add(total);
                        waktuArray.add(date);
                    }
                    if(status.contains("complete")){
                        if(!status.contains("checkout")){
                            namaArray.add(id);
                            statusArray.add(status);
                            hargaArray.add(total);
                            waktuArray.add(date);
                        }

                    }
                    j++;


                }
                catch (JSONException e) {
                    i=j;
                }
            }
            //((LaznasApp) MasukActivityLaznas.this.getApplication()).setUid(uid);
            //pDialogProfil3.dismiss();
        } else {
            Log.d("Login attempt Front", "Login Failed");
        }

    }

//    public void toAcara(View view) {
//        Intent intent = new Intent(this, AcaraActivity_.class);
//
//        /*String tag =  (String) view.getTag();
//        intent.putExtra("acaraUser", tag);*/
//        startActivity(intent);
//    }
}
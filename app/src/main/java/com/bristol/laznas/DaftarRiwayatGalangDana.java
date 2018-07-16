package com.bristol.laznas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.bristol.laznas.helper.AppConfig;
import com.bristol.laznas.model.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DaftarRiwayatGalangDana extends AppCompatActivity {

    ListView lv;
    Context context;

//    public static int[] fotoAcara = {R.drawable.facebook_icon, R.drawable.facebook_icon, R.drawable.facebook_icon, R.drawable.facebook_icon, R.drawable.facebook_icon};
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

    private static final String TAG = DaftarRiwayatGalangDana.class.getSimpleName();
    JSONParser jsonParser = new JSONParser();
    AppConfig appConfig = new AppConfig();
    private String GALANG_DANA_URL = "laznas/mobile/crowdfunding/riwayat";
    // Progress Dialog
    private ProgressDialog pDialog;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_galang_dana);

        Intent intent = this.getIntent();
        if( getIntent().getExtras() != null){
            uid = intent.getStringExtra("id");
            Log.v(TAG, "uid : " + uid);
            Log.v(TAG, "GALANG_DANA_URL : " + GALANG_DANA_URL );
        }
        new AttempRiwayatGalangDana().execute();

        //TextView toolbarText = (TextView) findViewById(R.id.toolbarText);
        //toolbarText.setText(text);
    }

    public class AttempRiwayatGalangDana extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(DaftarRiwayatGalangDana.this);
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
            lv.setAdapter(new AdapterDaftarRiwayatGalangDana(DaftarRiwayatGalangDana.this, imageArray, namaArray, deskripsiArray, waktuArray, hargaArray, statusArray));
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
                appConfig.api_url() + GALANG_DANA_URL, "POST", params);

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
                    String target = json.getJSONObject(Integer.toString(i)).getJSONObject("field_funding_goal").getJSONObject("und").getJSONObject("0").getString("amount");
                    String judul = json.getJSONObject(Integer.toString(i)).getString("title");
                    String batas_waktu = json.getJSONObject(Integer.toString(i)).getJSONObject("field_funding_end").getJSONObject("und").getJSONObject("0").getString("value");
                    String status = json.getJSONObject(Integer.toString(i)).getString("status");
                    String deskripsi = json.getJSONObject(Integer.toString(i)).getString("body");
                    String image = json.getJSONObject(Integer.toString(i)).getString("image");

                    deskripsi = deskripsi.replaceAll("<(.*?)\\>"," ");//Removes all items in brackets
                    deskripsi = deskripsi.replaceAll("<(.*?)\\\n"," ");//Must be undeneath
                    deskripsi = deskripsi.replaceFirst("(.*?)\\>", " ");//Removes any connected item to the last bracket
                    deskripsi = deskripsi.replaceAll("&nbsp;"," ");
                    deskripsi = deskripsi.replaceAll("&amp;"," ");

                    target = target.substring(0, target.length() - 2);
                    batas_waktu = batas_waktu.substring(0, batas_waktu.length() - 9);
                    //Convert ke tanggal sesuai
                    //SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try {
                        date = fmt.parse(batas_waktu);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    //SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
                    batas_waktu =  fmtOut.format(date);

                    Log.d("judul", judul);
                    Log.d("target", "Rp"+target+",00");
                    Log.d("batas waktu", batas_waktu);
                    Log.d("status", status);
                    Log.d("deskripsi", deskripsi);
                    Log.d("image", image);

                        namaArray.add(judul);
                        hargaArray.add(target);
                        waktuArray.add(batas_waktu);
                        deskripsiArray.add(deskripsi);
                        imageArray.add(image);
                        statusArray.add(status);


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
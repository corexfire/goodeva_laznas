package com.bristol.laznas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DaftarRiwayatBerita extends AppCompatActivity {

    ListView lv;
    Context context;
    private static final String TAG = DaftarRiwayatBerita.class.getSimpleName();
    JSONParser jsonParser = new JSONParser();
    AppConfig appConfig = new AppConfig();
    private static final String DONASI_URL = "laznas/mobile/berita/all";
    // testing on Emulator:
    private static final String READ_COMMENTS_URL = "http://brianadi.xyz/laznas/comments.php";
    // Progress Dialog
    private ProgressDialog pDialog;
    ImageView btn_left;

    // manages all of our comments in a list.
    private ArrayList<HashMap<String, String>> mCommentList;

    /**public static int[] fotoAcara = {R.drawable.laznas, R.drawable.laznas, R.drawable.laznas, R.drawable.laznas, R.drawable.laznas};
    public static String[] namaAcara = {"Acara 1", "Acara 2", "Acara 3", "Acara 4", "Acara 5"};
    public static String[] deskripsiAcara = {"Design Sprint merupakan metodologi pengembangan produk teknologi yang dikembangkan oleh Google sejak 2012.",
            "Design Sprint merupakan metodologi pengembangan produk teknologi yang dikembangkan oleh Google sejak 2012.",
            "Design Sprint merupakan metodologi pengembangan produk teknologi yang dikembangkan oleh Google sejak 2012.",
            "Design Sprint merupakan metodologi pengembangan produk teknologi yang dikembangkan oleh Google sejak 2012.",
            "Design Sprint merupakan metodologi pengembangan produk teknologi yang dikembangkan oleh Google sejak 2012."};
    public static String[] waktuAcara = {"24 Sept 2016", "25 Sept 2016", "1 Okt 2016", "8 Okt 2016", "8 Okt 2016"};
    public static String[] hargaAcara = {"Gratis", "20K", "50K", "100K", "100K"};**/


    ArrayList<String> judulArray = new ArrayList<String>();
    ArrayList<String> deskripsiArray = new ArrayList<String>();
    ArrayList<String> imageArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_berita);
        new AttempRiwayatBerita().execute();
        btn_left = (ImageView) findViewById(R.id.btn_left);
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public class AttempRiwayatBerita extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DaftarRiwayatBerita.this);
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
            lv.setAdapter(new AdapterDaftarBerita(DaftarRiwayatBerita.this, imageArray, judulArray, deskripsiArray));
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
        // getting product details by making HTTP request
        JSONObject json = jsonParser.makeHttpRequest(
                appConfig.api_url() + DONASI_URL, "POST", params);

        // check your log for json response
        // json success tag
        //success = json.getInt(TAG_SUCCESS);
        //success = 0;
        if (json != null) {
            Log.d("Login attempt", json.toString());
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
            try {
                String target = json.getJSONObject("0").getJSONObject("field_funding_goal").getJSONObject("und").getJSONObject("0").getString("amount");
                String judul = json.getJSONObject("0").getString("title");

                target = target.substring(0, target.length() - 2);
                //Log.d("target", target);
                //Log.d("judul", judul);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            int i = 0;
            int j = 1;

            for(i = 0; i<j;i++){
                try {
                    String judul = json.getJSONObject(Integer.toString(i)).getString("title");
                    String status = json.getJSONObject(Integer.toString(i)).getString("status");
                    String deskripsi = json.getJSONObject(Integer.toString(i)).getString("body");
                    String image = json.getJSONObject(Integer.toString(i)).getString("image");

                    deskripsi = deskripsi.replaceAll("<(.*?)\\>"," ");//Removes all items in brackets
                    deskripsi = deskripsi.replaceAll("<(.*?)\\\n"," ");//Must be undeneath
                    deskripsi = deskripsi.replaceFirst("(.*?)\\>", " ");//Removes any connected item to the last bracket
                    deskripsi = deskripsi.replaceAll("&nbsp;"," ");
                    deskripsi = deskripsi.replaceAll("&amp;"," ");


                    Log.d("judul", judul);
                    Log.d("status", status);
                    Log.d("deskripsi", deskripsi);
                    Log.d("image", image);
                    if(status.contains("1")){
                        judulArray.add(judul);
                        deskripsiArray.add(deskripsi);
                        imageArray.add(image);
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
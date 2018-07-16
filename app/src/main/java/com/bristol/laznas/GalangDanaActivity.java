package com.bristol.laznas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bristol.laznas.adapter.GalangDanaAdapter;
import com.bristol.laznas.helper.AppConfig;
import com.bristol.laznas.helper.ConvertRupiah;
import com.bristol.laznas.model.GalangDanaModel;
import com.bristol.laznas.model.JSONParser;
import com.bristol.laznas.model.JSONParsers;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@EActivity(R.layout.activity_galang_dana)
public class GalangDanaActivity extends AppCompatActivity {
    @ViewById(R.id.listview)
    ListView lv;
    Context context;
    List<GalangDanaModel> mcList;
    ConvertRupiah convertRupiah;
    //private FirebaseRecyclerAdapter<Acara, BerandaFragment.ListViewHolder> mFirebaseAdapter;
    //private DatabaseReference mFirebaseDatabaseReference;
    private RecyclerView mListRecyclerViewAcara;
    private LinearLayoutManager mLinearLayoutManager;
    JSONParser jsonParser = new JSONParser();
    JSONParsers jsonParsers = new JSONParsers();
    View rootViewDonasi;
    //private static final String LOGIN_URL = "http://brianadi.xyz/laznas/login.php";
    AppConfig appConfig = new AppConfig();
//    private static final String DONASI_URL = "https://bsmu.or.id/api/laznas/mobile/crowdfunding/donasi";
private static final String DONASI_URL = "laznas/mobile/crowdfunding/all";
    private static final String REVENUE_URL = "laznas/mobile/revenue?nid=";
    private String uid;

    private ArrayList<HashMap<String, String>> mCommentList;
    // Progress Dialog
    private ProgressDialog pDialog;
    private GalangDanaAdapter mAdapter;
    ArrayList<GalangDanaModel> donasi;

//    ArrayList<String> nidArray = new ArrayList<String>();
//    ArrayList<String> judulArray = new ArrayList<String>();
//    ArrayList<String> targetArray = new ArrayList<String>();
//    ArrayList<String> batasWaktuArray = new ArrayList<String>();
//    ArrayList<String> deskripsiArray = new ArrayList<String>();
//    ArrayList<String> imageArray = new ArrayList<String>();
    private static final String TAG = GalangDanaActivity_.class.getSimpleName();

    @AfterViews
    void init() {
        context = getApplicationContext();
        mcList = new ArrayList<GalangDanaModel>();
        mLinearLayoutManager = new LinearLayoutManager(context);
        convertRupiah = new ConvertRupiah();

//        mListRecyclerViewAcara = (RecyclerView) findViewById(R.id.rv_galang_dana);
//        mListRecyclerViewAcara.setLayoutManager(mLinearLayoutManager);
        Donasi();
    }

    public void Donasi()
    {
        new AttemptDonasi().execute("");
    }

    private class AttemptDonasi extends AsyncTask<String, String, String> {

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
//        public AttemptDonasi(FragmentActivity activity, View rootView) {
//            mContex = activity;
//            view1 = rootView;
//        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            super.onPreExecute();
            pDialog = new ProgressDialog(GalangDanaActivity.this);
            pDialog.setMessage("Loading Records...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            //  Auto-generated method stub
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
                    appConfig.api_url() + DONASI_URL, "POST", params);

            // check your log for json response
            // json success tag
            //success = json.getInt(TAG_SUCCESS);
            success = 0;
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
                        String nid = json.getJSONObject(Integer.toString(i)).getString("nid");
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

//                        int frmt = Integer.valueOf(String.valueOf(Long.parseLong(target) - 300000000))  ;
//                        DecimalFormat dfmt = new DecimalFormat();
//                        DecimalFormatSymbols fmts = new DecimalFormatSymbols();
//
//                        fmts.setGroupingSeparator('.');
//
//                        dfmt.setGroupingSize(3);
//                        dfmt.setGroupingUsed(true);
//                        dfmt.setDecimalFormatSymbols(fmts);
//                        String outputNomor = dfmt.format(frmt);



                        Log.d("nid", nid);
                        Log.d("judul", judul);
                        Log.d("target", convertRupiah.ConvertRupiah(target));
                        Log.d("batas waktu", batas_waktu);
                        Log.d("status", status);
                        Log.d("deskripsi", deskripsi);
                        Log.d("image", image);
                        if(status.contains("0")){
                            if(!nid.contains("136") && !nid.contains("137")){
                                if(!judul.contains("Gerai")) {
                                    List<NameValuePair> paramss = new ArrayList<NameValuePair>();
                                    JSONObject jsons = jsonParsers.makeHttpRequest(
                                            appConfig.api_url() + REVENUE_URL + nid, "POST", paramss);
                                    String revenue ="";
                                    String donatur ="";
                                    if (jsons != null){

                                        revenue = jsons.getString("revenue");
                                        donatur = jsons.getString("donatur");

//                                        terkumpulArray.add("900000000");
                                    }else{
                                        revenue = "0";
                                    }
                                    GalangDanaModel galangDanaModel= new GalangDanaModel(nid, judul, deskripsi, batas_waktu,
                                            target, image,revenue, donatur);
                                    mcList.add(galangDanaModel);

//
//                                    nidArray.add(nid);
//                                    judulArray.add(judul);
//                                    targetArray.add(target);
//                                    batasWaktuArray.add(batas_waktu);
//                                    deskripsiArray.add(deskripsi);
//                                    imageArray.add(image);
                                }
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

            pDialog.dismiss();

            mAdapter = new GalangDanaAdapter(getApplicationContext(),R.layout.list_galang_dana, mcList);
            lv.setAdapter(mAdapter);



        }




    }
}

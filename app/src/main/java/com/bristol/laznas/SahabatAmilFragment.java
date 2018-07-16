package com.bristol.laznas;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bristol.laznas.helper.AppConfig;
import com.bristol.laznas.model.JSONParser;
import com.bristol.laznas.model.JSONParsers;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SahabatAmilFragment extends Fragment {

    ListView lv;
    Context context;
    //private FirebaseRecyclerAdapter<Acara, BerandaFragment.ListViewHolder> mFirebaseAdapter;
    //private DatabaseReference mFirebaseDatabaseReference;
    private RecyclerView mListRecyclerViewAcara;
    private LinearLayoutManager mLinearLayoutManager;
    JSONParser jsonParser = new JSONParser();
    JSONParsers jsonParsers = new JSONParsers();
    //private static final String LOGIN_URL = "http://brianadi.xyz/laznas/login.php";
    AppConfig appConfig = new AppConfig();
    //    private static final String DONASI_URL = "laznas/mobile/crowdfunding/donasi";
    private static final String DONASI_URL = "laznas/mobile/crowdfunding/all";
    private static final String REVENUE_URL = "laznas/mobile/revenue?nid=";
    private String uid;
    View rootViewDonasi;

    // manages all of our comments in a list.
    private ArrayList<HashMap<String, String>> mCommentList;
    // Progress Dialog
    private ProgressDialog pDialog;
    // testing on Emulator:
    private static final String READ_COMMENTS_URL = "http://brianadi.xyz/laznas/comments.php";

    // JSON IDS:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_TITLE = "title";
    private static final String TAG_POSTS = "posts";
    private static final String TAG_POST_ID = "post_id";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_STATUS = "status";;
    // An array of all of our comments
    private JSONArray mComments = null;
    private SahabatAdapter mAdapter;
    ArrayList<SahabatModel> donasi;

    ArrayList<String> nidArray = new ArrayList<String>();
    ArrayList<String> judulArray = new ArrayList<String>();
    ArrayList<String> targetArray = new ArrayList<String>();
    ArrayList<String> batasWaktuArray = new ArrayList<String>();
    ArrayList<String> deskripsiArray = new ArrayList<String>();
    ArrayList<String> imageArray = new ArrayList<String>();
    ArrayList<String> terkumpulArray = new ArrayList<String>();
    private static final String TAG = DonasiFragment.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootViewDonasi = inflater.inflate(R.layout.fragment_sahabat_amil, container, false);

        context = getContext();

        mLinearLayoutManager = new LinearLayoutManager(context);

        mListRecyclerViewAcara = (RecyclerView) rootViewDonasi.findViewById(R.id.rv_Donasi);
        mListRecyclerViewAcara.setLayoutManager(mLinearLayoutManager);

        int flag = ((LaznasApp) getActivity().getApplication()).getFlag();
        Log.v(TAG, "flag: " + flag);
        if(flag != 2){
            Donasi();
        }
        else{
            donasi = SahabatModel.createDonasiList(((LaznasApp) getActivity().getApplication()).getNidArray(),
                    ((LaznasApp) getActivity().getApplication()).getJudulArray(),
                    ((LaznasApp) getActivity().getApplication()).getTargetArray(),
                    ((LaznasApp) getActivity().getApplication()).getBatasWaktuArray(),
                    ((LaznasApp) getActivity().getApplication()).getDeskripsiArray(),
                    ((LaznasApp) getActivity().getApplication()).getImageArray(),
                    ((LaznasApp) getActivity().getApplication()).getTerkumpulArray());
            mAdapter = new SahabatAdapter(getActivity(), donasi);
            mListRecyclerViewAcara.setAdapter(mAdapter);
        }

        return  rootViewDonasi;
    }

    @Override
    public void onResume() {
        // Auto-generated method stub
        super.onResume();
        // loading the comments via AsyncTask
        //new LoadComments().execute();
    }

    public void Donasi()
    {
        new SahabatAmilFragment.AttemptSahabat(getActivity(), rootViewDonasi).execute("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Fragment fragment = getFragmentManager().findFragmentById(0);
//        if(fragment != null)
//            getFragmentManager().beginTransaction().remove(fragment).commit();
//        mFirebaseAdapter.cleanup();
    }

    private class AttemptSahabat extends AsyncTask<String, String, String> {

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
        public AttemptSahabat(FragmentActivity activity, View rootView) {
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

//                        int frmt = Integer.parseInt(target);
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
//                        Log.d("target", "Rp"+outputNomor+",00");
                        Log.d("batas waktu", batas_waktu);
                        Log.d("status", status);
                        Log.d("deskripsi", deskripsi);
                        Log.d("image", image);
                        if(status.contains("0")){
                            if(!nid.contains("136") && !nid.contains("137")){
                                if(judul.contains("Sahabat")) {
                                    List<NameValuePair> paramss = new ArrayList<NameValuePair>();
                                    JSONObject jsons = jsonParsers.makeHttpRequest(
                                            appConfig.api_url() + REVENUE_URL + nid, "POST", paramss);
                                    if (jsons != null){

                                        String revenue = jsons.getString("revenue");
                                        terkumpulArray.add(revenue);
//                                        terkumpulArray.add("900000000");
                                    }else{
                                        terkumpulArray.add("0");
                                    }
                                    nidArray.add(nid);
                                    judulArray.add(judul);
                                    targetArray.add(target);
                                    batasWaktuArray.add(batas_waktu);
                                    deskripsiArray.add(deskripsi);
                                    imageArray.add(image);
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
            // dismiss the dialog once product deleted
            //pDialogProfil3.dismiss();
            //Perform all UI changes here
            //tvNama.setText(name);

            ((LaznasApp) getActivity().getApplication()).setFlag(2);
            ((LaznasApp) getActivity().getApplication()).setNidArray(nidArray);
            ((LaznasApp) getActivity().getApplication()).setJudulArray(judulArray);
            ((LaznasApp) getActivity().getApplication()).setTargetArray(targetArray);
            ((LaznasApp) getActivity().getApplication()).setBatasWaktuArray(batasWaktuArray);
            ((LaznasApp) getActivity().getApplication()).setDeskripsiArray(deskripsiArray);
            ((LaznasApp) getActivity().getApplication()).setImageArray(imageArray);
            ((LaznasApp) getActivity().getApplication()).setTerkumpulArray(terkumpulArray);

            donasi = SahabatModel.createDonasiList(nidArray, judulArray, targetArray, batasWaktuArray,
                    deskripsiArray, imageArray, terkumpulArray);
            mAdapter = new SahabatAdapter(getActivity(), donasi);
            mListRecyclerViewAcara.setAdapter(mAdapter);


        }




    }

}

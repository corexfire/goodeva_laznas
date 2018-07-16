package com.bristol.laznas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bristol.laznas.helper.AppConfig;
import com.bristol.laznas.model.Acara;
import com.bristol.laznas.model.JSONParser;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static java.lang.String.format;

/**
 * Created by Brian Adi Kusumo on 3/9/2017.
 */

public class BerandaFragmentLaznas extends Fragment {
//    ListView lv;
    Context context;
    JSONParser jsonParser = new JSONParser();

    LinearLayout btn_galang_dana, btn_panduan_zakat,btn_kalkulator,btn_donasi_online,btn_infaq_online,btn_zakat_online;
    CarouselView carouselView;
    int[] sampleImages = {R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4};
    //private FirebaseRecyclerAdapter<Acara, BerandaFragment.ListViewHolder> mFirebaseAdapter;
    //private DatabaseReference mFirebaseDatabaseReference;
//    private RecyclerView mListRecyclerViewAcara;
//    private LinearLayoutManager mLinearLayoutManager;
//    JSONParser jsonParser = new JSONParser();
//    //private static final String LOGIN_URL = "http://brianadi.xyz/laznas/login.php";
//
    AppConfig appConfig = new AppConfig();
    private static final String IMAGE_URL = "laznas/mobile/image";
//    private static final String COMPLETE_URL = "https://bsmu.or.id/api/laznas/mobile/order/all";
//    private static final String DONASI_URL = "https://bsmu.or.id/api/laznas/mobile/crowdfunding/donasi";
//    private static final String ZAKAT_URL = "https://bsmu.or.id/api/laznas/mobile/crowdfunding/zakat";
//    private String uid;
    View rootViewBeranda;
    List<String> imageList = new ArrayList<String>();

    // manages all of our comments in a list.
    private ArrayList<HashMap<String, String>> mCommentList;
    // Progress Dialog
//    private ProgressDialog pDialog;
//    // testing on Emulator:
//    private static final String READ_COMMENTS_URL = "http://brianadi.xyz/laznas/comments.php";
//
//    // JSON IDS:
//    private static final String TAG_SUCCESS = "success";
//    private static final String TAG_TITLE = "title";
//    private static final String TAG_POSTS = "posts";
//    private static final String TAG_POST_ID = "post_id";
//    private static final String TAG_USERNAME = "username";
//    private static final String TAG_MESSAGE = "message";
//    private static final String TAG_STATUS = "status";;
//    // An array of all of our comments
//    private JSONArray mComments = null;
//    private ContactsAdapter mAdapter;
//    ArrayList<Contact> contacts;
//
//    ArrayList<String> nidArray = new ArrayList<String>();
//    ArrayList<String> judulArray = new ArrayList<String>();
//    ArrayList<String> targetArray = new ArrayList<String>();
//    ArrayList<String> batasWaktuArray = new ArrayList<String>();
//    ArrayList<String> deskripsiArray = new ArrayList<String>();
//    ArrayList<String> imageArray = new ArrayList<String>();
    private static final String TAG = BerandaFragment.class.getSimpleName();

//    ArrayList prgmName;
//    public static int [] fotoAcara={R.drawable.go,R.drawable.go,R.drawable.go,R.drawable.go,R.drawable.go};
//    public static String [] namaAcara={"Acara 1","Acara 2","Acara 3","Acara 4","Acara 5"};
//    public static String [] deskripsiAcara={"Design Sprint merupakan metodologi pengembangan produk " +
//            "teknologi yang dikembangkan oleh Google sejak 2012.",
//                                            "Design Sprint merupakan metodologi pengembangan produk " +
//                                                    "teknologi yang dikembangkan oleh Google sejak 2012.",
//                                            "Design Sprint merupakan metodologi pengembangan produk " +
//                                                    "teknologi yang dikembangkan oleh Google sejak 2012.",
//                                            "Design Sprint merupakan metodologi pengembangan produk " +
//                                                    "teknologi yang dikembangkan oleh Google sejak 2012.",
//                                            "Design Sprint merupakan metodologi pengembangan produk " +
//                                                    "teknologi yang dikembangkan oleh Google sejak 2012."};
//    public static String[] waktuAcara={"24 Sept 2016", "25 Sept 2016", "1 Okt 2016", "8 Okt 2016", "8 Okt 2016"};
//    public static String [] hargaAcara={"Gratis","20K","50K","100K","100K"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        rootViewBeranda = inflater.inflate(R.layout.fragment_beranda, container, false);

        context = getContext();
        carouselView = (CarouselView) rootViewBeranda.findViewById(R.id.carouselView);
        Slider();




        btn_galang_dana = (LinearLayout) rootViewBeranda.findViewById(R.id.btn_galang_dana);
        btn_panduan_zakat = (LinearLayout) rootViewBeranda.findViewById(R.id.btn_panduan_zakat);
        btn_kalkulator = (LinearLayout) rootViewBeranda.findViewById(R.id.btn_kalkulator);
        btn_donasi_online = (LinearLayout) rootViewBeranda.findViewById(R.id.btn_donasi_online);
        btn_infaq_online = (LinearLayout) rootViewBeranda.findViewById(R.id.btn_infaq_online);
        btn_zakat_online = (LinearLayout) rootViewBeranda.findViewById(R.id.btn_zakat_online);
        btn_galang_dana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GalangDanaActivity_.class);

                startActivity(intent);
            }
        });
        btn_panduan_zakat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PanduanActivity_.class);

                startActivity(intent);
            }
        });
        btn_kalkulator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), KalkulatorActivity_.class);

                startActivity(intent);
            }
        });
        btn_donasi_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZakatOnlineActivity_.class);
                intent.putExtra("desc","Donasi");
                startActivity(intent);
            }
        });
        btn_infaq_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZakatOnlineActivity_.class);
                intent.putExtra("desc","Infaq");
                startActivity(intent);
            }
        });
        btn_zakat_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZakatOnlineActivity_.class);
                intent.putExtra("desc","Zakat Online");
                startActivity(intent);
            }
        });
//        Log.v(TAG, "uid: " + uid);
//        //mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
//
//        mLinearLayoutManager = new LinearLayoutManager(context);
//
//        mListRecyclerViewAcara = (RecyclerView) rootViewBeranda.findViewById(R.id.Recycler_View);
//        mListRecyclerViewAcara.setLayoutManager(mLinearLayoutManager);
//
//        //Beranda();
//
//
//        int flag = ((LaznasApp) getActivity().getApplication()).getFlag();
//        Log.v(TAG, "flag: " + flag);
//        if(flag != 1){
//            Beranda();
//        }
//        else{
//            contacts = Contact.createContactsList(((LaznasApp) getActivity().getApplication()).getNidArray(),
//                    ((LaznasApp) getActivity().getApplication()).getJudulArray(),
//                    ((LaznasApp) getActivity().getApplication()).getTargetArray(),
//                    ((LaznasApp) getActivity().getApplication()).getBatasWaktuArray(),
//                    ((LaznasApp) getActivity().getApplication()).getDeskripsiArray(),
//                    ((LaznasApp) getActivity().getApplication()).getImageArray());
//            mAdapter = new ContactsAdapter(getActivity(), contacts);
//            mListRecyclerViewAcara.setAdapter(mAdapter);
//        }



        // Initialize contacts


        //populateList();
        return rootViewBeranda;
    }

    public void Slider()
    {
        new BerandaFragmentLaznas.AttemptSlider(getActivity(), rootViewBeranda).execute("");
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
            new DownloadImageTask(imageView).execute(imageList.get(position));
//            Picasso.with(context).load(imageList.get(position)).resize(631,346).into(imageView);
//            Picasso.with(getActivity())
//                    .load(imageList.get(position))
//                    .resize(631,346)
//                    .into(new Target() {
//                        @Override
//                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                            // loaded bitmap is here (bitmap)
//                            imageView.setImageBitmap(bitmap);
//                        }
//
//                        @Override
//                        public void onBitmapFailed(Drawable errorDrawable) {
//
//                        }
//
//                        @Override
//                        public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                        }
//                    });
//            imageView.setImageResource(sampleImages[position]);
        }
    };



    @Override
    public void onResume() {
        // Auto-generated method stub
        super.onResume();
        // loading the comments via AsyncTask
        //new LoadComments().execute();
    }

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
                        appConfig.api_url() + IMAGE_URL, "POST", params);


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

//    public void Beranda()
//    {
//        new AttemptBeranda(getActivity(), rootViewBeranda).execute("");
//    }
    @Override
    public void onDestroy() {
        super.onDestroy();
//        Fragment fragment = getFragmentManager().findFragmentById(0);
//        if(fragment != null)
//            getFragmentManager().beginTransaction().remove(fragment).commit();
//        mFirebaseAdapter.cleanup();
     }

//    public static class ListViewHolder extends RecyclerView.ViewHolder {
//        public TextView tv_judul;
//        public TextView tv_deskripsi;
//        public TextView tv_tanggal;
//        public TextView tv_harga;
//        View mView;
//
//        public ListViewHolder(View v) {
//            super(v);
//
//            mView = v;
//
//            tv_judul = (TextView) itemView.findViewById(R.id.namaAcara);
//            tv_deskripsi = (TextView) itemView.findViewById(R.id.deskripsiAcara);
//            tv_tanggal = (TextView) itemView.findViewById(R.id.waktuAcara);
//            tv_harga = (TextView) itemView.findViewById(R.id.hargaAcara);
//        }
//    }

    //Login
//    private class AttemptBeranda extends AsyncTask<String, String, String> {
//
//        ProgressDialog pDialogProfil3;
//        /**
//         * Before starting background thread Show Progress Dialog
//         * */
//        boolean failure = false;
//        //ProgressDialog dialog = new ProgressDialog(getParentFragment().getContext(),ProgressDialog.STYLE_SPINNER);
//        // The variable is moved here, we only need it here while displaying the
//        // progress dialog.
//        TextView txtView;
//        View view1;
//        Activity mContex;
//        public AttemptBeranda(FragmentActivity activity, View rootView) {
//            mContex = activity;
//            view1 = rootView;
//        }
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            ProgressDialog mAuthProgressDialog;
//            String nama = mContex.getLocalClassName();
//            Log.d("class", nama);
////            pDialogProfil3 = new ProgressDialog(view1.getContext());
////            //pDialog.setMessage("Loading...");
////            pDialogProfil3.setMessage("Mohon Tunggu...");
////            pDialogProfil3.setIndeterminate(false);
////            pDialogProfil3.setCancelable(true);
////            pDialogProfil3.show();
//        }
//
//        @Override
//        protected String doInBackground(String... args) {
//            // Auto-generated method stub
//            // Check for success tag
//            int success;
//
//
//                //--Perhatikan json baik-baik
//
//                Log.d("request 2!", "starting");
//                List<NameValuePair> params = new ArrayList<NameValuePair>();
//                // getting product details by making HTTP request
//                JSONObject json = jsonParser.makeHttpRequest(
//                        ZAKAT_URL, "POST", params);
//
//                // check your log for json response
//                // json success tag
//                //success = json.getInt(TAG_SUCCESS);
//                success = 0;
//                if (json != null) {
//                    Log.d("Login attempt", json.toString());
//                    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
//                    SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
//                    try {
//                        String target = json.getJSONObject("0").getJSONObject("field_funding_goal").getJSONObject("und").getJSONObject("0").getString("amount");
//                        String judul = json.getJSONObject("0").getString("title");
//
//                        target = target.substring(0, target.length() - 2);
//                        //Log.d("target", target);
//                        //Log.d("judul", judul);
//                    }
//                    catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                        int i = 0;
//                        int j = 1;
//
//                    for(i = 0; i<j;i++){
//                        try {
//                            String nid = json.getJSONObject(Integer.toString(i)).getString("nid");
//                            String target = json.getJSONObject(Integer.toString(i)).getJSONObject("field_funding_goal").getJSONObject("und").getJSONObject("0").getString("amount");
//                            String judul = json.getJSONObject(Integer.toString(i)).getString("title");
//                            String batas_waktu = json.getJSONObject(Integer.toString(i)).getJSONObject("field_funding_end").getJSONObject("und").getJSONObject("0").getString("value");
//                            String status = json.getJSONObject(Integer.toString(i)).getString("status");
//                            String deskripsi = json.getJSONObject(Integer.toString(i)).getString("body");
//                            String image = json.getJSONObject(Integer.toString(i)).getString("image");
//
//                            deskripsi = deskripsi.replaceAll("<(.*?)\\>"," ");//Removes all items in brackets
//                            deskripsi = deskripsi.replaceAll("<(.*?)\\\n"," ");//Must be undeneath
//                            deskripsi = deskripsi.replaceFirst("(.*?)\\>", " ");//Removes any connected item to the last bracket
//                            deskripsi = deskripsi.replaceAll("&nbsp;"," ");
//                            deskripsi = deskripsi.replaceAll("&amp;"," ");
//
//                            target = target.substring(0, target.length() - 2);
//                            batas_waktu = batas_waktu.substring(0, batas_waktu.length() - 9);
//
//                            target = " ";
//                            //Convert ke tanggal sesuai
//                            //SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
//                            Date date = null;
//                            try {
//                                date = fmt.parse(batas_waktu);
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//
//                            //SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
//                            batas_waktu =  fmtOut.format(date);
//                            batas_waktu = " ";
//
//
//
//                            Log.d("nid", nid);
//                            Log.d("judul", judul);
//                            Log.d("target", "Rp"+target+",00");
//                            Log.d("batas waktu", batas_waktu);
//                            Log.d("status", status);
//                            Log.d("deskripsi", deskripsi);
//                            Log.d("image", image);
//                            if(status.contains("1")){
//                                nidArray.add(nid);
//                                judulArray.add(judul);
//                                targetArray.add(target);
//                                batasWaktuArray.add(batas_waktu);
//                                deskripsiArray.add(deskripsi);
//                                imageArray.add(image);
//                            }
//
//                            j++;
//                        }
//                        catch (JSONException e) {
//                            i=j;
//                        }
//                    }
//                    //((LaznasApp) MasukActivityLaznas.this.getApplication()).setUid(uid);
//                    //pDialogProfil3.dismiss();
//                } else {
//                    Log.d("Login attempt Front", "Login Failed");
//                }
//
//            Log.d("request 3!", "starting");
//            json = null;
//            //List<NameValuePair> params = new ArrayList<NameValuePair>();
//            // getting product details by making HTTP request
//            json = jsonParser.makeHttpRequest(
//                    DONASI_URL, "POST", params);
//
//            // check your log for json response
//            // json success tag
//            //success = json.getInt(TAG_SUCCESS);
//            success = 0;
//            if (json != null) {
//                Log.d("Login attempt", json.toString());
//                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
//                SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
//                try {
//                    String target = json.getJSONObject("0").getJSONObject("field_funding_goal").getJSONObject("und").getJSONObject("0").getString("amount");
//                    String judul = json.getJSONObject("0").getString("title");
//
//                    target = target.substring(0, target.length() - 2);
//                    //Log.d("target", target);
//                    //Log.d("judul", judul);
//
//                }
//                catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                int i = 0;
//                int j = 1;
//
//
//                for(i = 0; i<j;i++){
//                    try {
//                        String nid = json.getJSONObject(Integer.toString(i)).getString("nid");
//                        String target = json.getJSONObject(Integer.toString(i)).getJSONObject("field_funding_goal").getJSONObject("und").getJSONObject("0").getString("amount");
//                        String judul = json.getJSONObject(Integer.toString(i)).getString("title");
//                        String batas_waktu = json.getJSONObject(Integer.toString(i)).getJSONObject("field_funding_end").getJSONObject("und").getJSONObject("0").getString("value");
//                        String status = json.getJSONObject(Integer.toString(i)).getString("status");
//                        String deskripsi = json.getJSONObject(Integer.toString(i)).getString("body");
//                        String image = json.getJSONObject(Integer.toString(i)).getString("image");
//
//                        deskripsi = deskripsi.replaceAll("<(.*?)\\>"," ");//Removes all items in brackets
//                        deskripsi = deskripsi.replaceAll("<(.*?)\\\n"," ");//Must be undeneath
//                        deskripsi = deskripsi.replaceFirst("(.*?)\\>", " ");//Removes any connected item to the last bracket
//                        deskripsi = deskripsi.replaceAll("&nbsp;"," ");
//                        deskripsi = deskripsi.replaceAll("&amp;"," ");
//
//                        target = target.substring(0, target.length() - 2);
//                        batas_waktu = batas_waktu.substring(0, batas_waktu.length() - 9);
//                        //Convert ke tanggal sesuai
//                        //SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
//                        Date date = null;
//                        try {
//                            date = fmt.parse(batas_waktu);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//
//                        //SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
//                        batas_waktu =  fmtOut.format(date);
//
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
//
//
//
//                        Log.d("nid", nid);
//                        Log.d("judul", judul);
//                        Log.d("target", "Rp"+outputNomor+",00");
//                        Log.d("batas waktu", batas_waktu);
//                        Log.d("status", status);
//                        Log.d("deskripsi", deskripsi);
//                        Log.d("image", image);
//                        if(status.contains("1")){
//                            if(!nid.contains("136") && !nid.contains("137")){
//                                if(!judul.contains("Gerai")) {
//                                    nidArray.add(nid);
//                                    judulArray.add(judul);
//                                    targetArray.add(target);
//                                    batasWaktuArray.add(batas_waktu);
//                                    deskripsiArray.add(deskripsi);
//                                    imageArray.add(image);
//                                }
//                            }
//                        }
//
//                        j++;
//                    }
//                    catch (JSONException e) {
//                        i=j;
//                    }
//                }
//                //((LaznasApp) MasukActivityLaznas.this.getApplication()).setUid(uid);
//                //pDialogProfil3.dismiss();
//            } else {
//                Log.d("Login attempt Front", "Login Failed");
//            }
//            //}
//            //catch (JSONException e) {
//                //e.printStackTrace();
//            //}
//
//
//            return null;
//        }
//        /**
//         * After completing background task Dismiss the progress dialog
//         * **/
//        protected void onPostExecute(String file_url) {
//            // dismiss the dialog once product deleted
//            //pDialogProfil3.dismiss();
//            //Perform all UI changes here
//            //tvNama.setText(name);
//
//            ((LaznasApp) getActivity().getApplication()).setFlag(1);
//            ((LaznasApp) getActivity().getApplication()).setNidArray(nidArray);
//            ((LaznasApp) getActivity().getApplication()).setJudulArray(judulArray);
//            ((LaznasApp) getActivity().getApplication()).setTargetArray(targetArray);
//            ((LaznasApp) getActivity().getApplication()).setBatasWaktuArray(batasWaktuArray);
//            ((LaznasApp) getActivity().getApplication()).setDeskripsiArray(deskripsiArray);
//            ((LaznasApp) getActivity().getApplication()).setImageArray(imageArray);
//
//            contacts = Contact.createContactsList(nidArray, judulArray, targetArray, batasWaktuArray,
//                    deskripsiArray, imageArray);
//            mAdapter = new ContactsAdapter(getActivity(), contacts);
//            mListRecyclerViewAcara.setAdapter(mAdapter);
//
//
//        }
//
//
//    }

//    public class LoadComments extends AsyncTask<Void, Void, Boolean> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(getContext());
//            pDialog.setMessage("Loading Records...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... arg0) {
//            updateJSONdata();
//            return null;
//
//        }
//
//        @Override
//        protected void onPostExecute(Boolean result) {
//            super.onPostExecute(result);
//            pDialog.dismiss();
//            //updateList();
//        }
//    }

//    public void updateJSONdata() {
//
//        // Instantiate the arraylist to contain all the JSON data.
//        // we are going to use a bunch of key-value pairs, referring
//        // to the json element name, and the content, for example,
//        // message it the tag, and "I'm awesome" as the content..
//
//        mCommentList = new ArrayList<HashMap<String, String>>();
//
//        // Bro, it's time to power up the J parser
//        JSONParser jParser = new JSONParser();
//        // Feed the beast our comments url, and it spits us
//        // back a JSON object. Boo-yeah Jerome.
//        JSONObject json = jParser.getJSONFromUrl(READ_COMMENTS_URL);
//
//        // when parsing JSON stuff, we should probably
//        // try to catch any exceptions:
//        try {
//
//            // I know I said we would check if "Posts were Avail." (success==1)
//            // before we tried to read the individual posts, but I lied...
//            // mComments will tell us how many "posts" or comments are
//            // available
//            mComments = json.getJSONArray(TAG_POSTS);
//
//            // looping through all posts according to the json object returned
//            for (int i = 0; i < mComments.length(); i++) {
//                JSONObject c = mComments.getJSONObject(i);
//
//                // gets the content of each tag
//                String title = c.getString(TAG_TITLE);
//                String content = c.getString(TAG_MESSAGE);
//                String username = c.getString(TAG_USERNAME);
//                String status = c.getString(TAG_STATUS);
//                //String  image=c.getString(TAG_IMG);
//
//
//
//                // creating new HashMap
//                HashMap<String, String> map = new HashMap<String, String>();
//
//                map.put(TAG_TITLE, title);
//                map.put(TAG_MESSAGE, content);
//                map.put(TAG_USERNAME, username);
//                map.put(TAG_STATUS, status);
//                //map.put(TAG_IMG, image);
//
//                // adding HashList to ArrayList
//                mCommentList.add(map);
//
////				byte[] rawImage = Base64.decode(image, Base64.DEFAULT);
////                Bitmap bmp = BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length);
////                imgv = (ImageView) findViewById(R.id.image);
////                imgv.setImageBitmap(bmp);
//
//
//                // annndddd, our JSON data is up to date same with our array
//                // list
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

//    public void toAcara(View view) {
//        Intent intent = new Intent(this.getActivity(), AcaraActivityLaznas_.class);
//
//        /*String tag =  (String) view.getTag();
//        intent.putExtra("acaraUser", tag);*/
//        startActivity(intent);
//    }


}


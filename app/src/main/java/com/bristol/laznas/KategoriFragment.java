package com.bristol.laznas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bristol.laznas.helper.AppConfig;
import com.bristol.laznas.model.JSONParser;
import com.squareup.picasso.Picasso;


public class KategoriFragment extends Fragment {

    private static final String TAG = KategoriFragment.class.getSimpleName();
    Context context;
    View rootViewBerita;
    JSONParser jsonParser = new JSONParser();
    AppConfig appConfig = new AppConfig();
    private static final String DONASI_URL = "laznas/mobile/berita/front";
    ArrayList<Contact> contacts;
    private ContactsAdapter mAdapter;
    private RecyclerView mListRecyclerViewAcara;
    TextView tvJudul1;
    TextView tvJudul2;
    TextView tvJudul3;
    TextView tvJudul4;
    ImageView ivPhoto1;
    ImageView ivPhoto2;
    ImageView ivPhoto3;
    ImageView ivPhoto4;
    RelativeLayout relative_berita;
    TextView tv_riwayat_berita;
    LinearLayout linear_1, linear_2, linear_3, linear_4;

    ArrayList<String> judulArray = new ArrayList<String>();
    ArrayList<String> deskripsiArray = new ArrayList<String>();
    ArrayList<String> imageArray = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        rootViewBerita = inflater.inflate(R.layout.fragment_kategori, container, false);


        tvJudul1 = (TextView) rootViewBerita.findViewById(R.id.judul1);
        tvJudul2 = (TextView) rootViewBerita.findViewById(R.id.judul2);
        tvJudul3 = (TextView) rootViewBerita.findViewById(R.id.judul3);
        tvJudul4 = (TextView) rootViewBerita.findViewById(R.id.judul4);
        ivPhoto1 = (ImageView) rootViewBerita.findViewById(R.id.gambar1);
        ivPhoto2 = (ImageView) rootViewBerita.findViewById(R.id.gambar2);
        ivPhoto3 = (ImageView) rootViewBerita.findViewById(R.id.gambar3);
        ivPhoto4 = (ImageView) rootViewBerita.findViewById(R.id.gambar4);
        linear_1 = (LinearLayout) rootViewBerita.findViewById(R.id.linear_1);
        linear_2 = (LinearLayout) rootViewBerita.findViewById(R.id.linear_2);
        linear_3 = (LinearLayout) rootViewBerita.findViewById(R.id.linear_3);
        linear_4 = (LinearLayout) rootViewBerita.findViewById(R.id.linear_4);



        context = getContext();
        Beranda();

        setRetainInstance(true);
        return rootViewBerita;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        tv_riwayat_berita = (TextView) rootViewBerita.findViewById(R.id.tv_semua_berita);
        relative_berita = (RelativeLayout) rootViewBerita.findViewById(R.id.relative_berita);


        relative_berita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mFirebaseAuth.signOut();
                Intent intent = new Intent(getActivity(), DaftarRiwayatBerita.class);
                startActivity(intent);
            }
        });

    }

    public void Beranda()
    {
        new AttemptBeranda(getActivity(), rootViewBerita).execute("");
    }

    //Login
    private class AttemptBeranda extends AsyncTask<String, String, String> {

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
        public AttemptBeranda(FragmentActivity activity, View rootView) {
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
            //  Auto-generated method stub
            // Check for success tag
            int success;


//            String user = "brian";
//            String pass = "123456";
            //try {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //params.add(new BasicNameValuePair("name", user));
            //params.add(new BasicNameValuePair("pass", pass));


            //--Perhatikan json baik-baik
            Log.d("request 2!", "starting");
            // getting product details by making HTTP request
            JSONObject json = jsonParser.makeHttpRequest(
                    appConfig.api_url() + DONASI_URL, "POST", params);

            // check your log for json response
            // json success tag
            //success = json.getInt(TAG_SUCCESS);
            success = 0;
            if (json != null) {
                Log.d("Login attempt", json.toString());
                try {
                    String judul = json.getJSONObject("0").getString("title");

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
            tvJudul1.setText(judulArray.get(0));
            tvJudul2.setText(judulArray.get(1));
            tvJudul3.setText(judulArray.get(2));
            tvJudul4.setText(judulArray.get(3));
            String imgUrl;

            imgUrl = imageArray.get(0);
            //Picasso
            Picasso.with(getActivity()).load(imgUrl).into(ivPhoto1);

            imgUrl = imageArray.get(1);
            //Picasso
            Picasso.with(getActivity()).load(imgUrl).into(ivPhoto2);

            imgUrl = imageArray.get(2);
            //Picasso
            Picasso.with(getActivity()).load(imgUrl).into(ivPhoto3);

            imgUrl = imageArray.get(3);
            //Picasso
            Picasso.with(getActivity()).load(imgUrl).into(ivPhoto4);

            //Set Click
            linear_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), BeritaActivityLaznas_.class);
                    intent.putExtra("deskripsi", deskripsiArray.get(0));
                    intent.putExtra("nama", judulArray.get(0));
                    intent.putExtra("gambar", imageArray.get(0));
                    v.getContext().startActivity(intent);
                }
            });

            //Set Click
            linear_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), BeritaActivityLaznas_.class);
                    intent.putExtra("deskripsi", deskripsiArray.get(1));
                    intent.putExtra("nama", judulArray.get(1));
                    intent.putExtra("gambar", imageArray.get(1));
                    v.getContext().startActivity(intent);
                }
            });


            //Set Click
            linear_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), BeritaActivityLaznas_.class);
                    intent.putExtra("deskripsi", deskripsiArray.get(2));
                    intent.putExtra("nama", judulArray.get(2));
                    intent.putExtra("gambar", imageArray.get(2));
                    v.getContext().startActivity(intent);
                }
            });

            //Set Click
            linear_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), BeritaActivityLaznas_.class);
                    intent.putExtra("deskripsi", deskripsiArray.get(3));
                    intent.putExtra("nama", judulArray.get(3));
                    intent.putExtra("gambar", imageArray.get(3));
                    v.getContext().startActivity(intent);
                }
            });

        }


    }




}

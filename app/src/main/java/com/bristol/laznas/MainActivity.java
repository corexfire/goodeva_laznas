package com.bristol.laznas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

//import com.bristol.laznas.model.JSONParser;

import com.bristol.laznas.adapter.TabFragmentUserAdapter;
import com.bristol.laznas.utils.SessionManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


@EActivity(R.layout.activity_main_laznas)
public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private static final String TAG = MainActivity_.class.getSimpleName();
    private GoogleApiClient client;
    private ViewPager viewPager;
    static InputStream is = null;
    String test;
    BottomBar bottomBar;
    @ViewById(R.id.sv)
    ImageView sv;
    SessionManager sessionManager;



    // Firebase instance variables
   // private FirebaseAuth mFirebaseAuth;
    //private FirebaseUser mFirebaseUser;
    //private DatabaseReference mFirebaseDatabaseReference;

    private String userLaznas;

    //private static final String LOGIN_URL = "https://bsmu.or.id/api/laznas/mobile/user";
    private String email;
    private String password;
    private String uid;


    private static final String LOGIN_URL = "https://bsmu.or.id/api/laznas/mobile/user/login";

    //private ProgressDialog pDialog;

    //JSONParser jsonParser = new JSONParser();

    @AfterViews
    void init() {
        // Making HTTP request
        sessionManager = new SessionManager(getApplicationContext());
        //mFirebaseAuth = FirebaseAuth.getInstance();
        //mFirebaseUser = mFirebaseAuth.getCurrentUser();
        //mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();


//        String user = ((LaznasApp) this.getApplication()).getUserName();
        String user = sessionManager.getUser();
//        int flag = ((LaznasApp) this.getApplication()).getFlag();
//        String pass = ((LaznasApp) this.getApplication()).getPassword();




//        uid = ((LaznasApp) MainActivity.this.getApplication()).getUid();
//        Log.v(TAG, "user: " + mFirebaseUser);
//        Log.v(TAG, "uid: " + uid);

//        if (mFirebaseUser == null) {
//            startActivity(new Intent(this, MasukActivity_.class));
//            finish();
//            return;
//        } else {
//
//        }
        if (!sessionManager.isLoggedIn()) {
            startActivity(new Intent(this, MasukActivityLaznas_.class));
            finish();
            return;
        } else {

        }

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        Pager adapter = new Pager(getSupportFragmentManager(), bottomBar.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);



        // Bottom bar. Paling bawah
        bottomBar.setActiveTabColor(getResources().getColor(R.color.orangetua));
        bottomBar.setInActiveTabColor(getResources().getColor(R.color.colorSecondaryGreen));

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                if (tabId == R.id.tabBeranda) {
                    sv.setVisibility(View.INVISIBLE);
                    viewPager.setCurrentItem(0);
                }
                if (tabId == R.id.tabDonasi) {
//                    if(!sessionManager.getRolesId().equals("1") && !sessionManager.getRolesId().equals("2")){
                        sv.setVisibility(View.VISIBLE);
//                    }else {
//                        sv.setVisibility(View.INVISIBLE);
//                    }

                    viewPager.setCurrentItem(1);
                }
                if (tabId == R.id.tabKategori) {
                    sv.setVisibility(View.INVISIBLE);
                    viewPager.setCurrentItem(2);
                }
                if (tabId == R.id.tabProfil) {
                    sv.setVisibility(View.INVISIBLE);
                    viewPager.setCurrentItem(3);
                }
            }
        });
    }

    @Click(R.id.sv)
    void openSearch(){
        Intent intent = new Intent(this, CariDonasiActivity_.class);
        startActivity(intent);
    }

    public void toEditProfil(View view) {
        Intent intent = new Intent(this, EditProfilActivity_.class);
        startActivity(intent);
    }





    public void toBerita(View view) {
        Intent intent = new Intent(this, AcaraActivityLaznas_.class);

        //String tag = (String) view.getTag();
        //intent.putExtra("acaraActivity", tag);
        startActivity(intent);
    }

    public void toAcaraPengguna(View view) {
        Intent intent = new Intent(this, DaftarAcara.class);

        String tag = (String) view.getTag();
        intent.putExtra("acaraUser", tag);
        startActivity(intent);
    }

    public void toAcaraKategori1(View view) {
        Intent intent = new Intent(this, DaftarAcara.class);
        String tag = (String) view.getTag();
        intent.putExtra("kategoriAcara", tag);
        startActivity(intent);
    }

    public void toAcaraKategori2(View view) {
        Intent intent = new Intent(this, DaftarAcara.class);

        String tag = (String) view.getTag();
        intent.putExtra("kategoriAcara", tag);
        startActivity(intent);
    }

    public void toAcaraKategori3(View view) {
        Intent intent = new Intent(this, DaftarAcara.class);

        String tag = (String) view.getTag();
        intent.putExtra("kategoriAcara", tag);
        startActivity(intent);
    }


    public void toAcaraKategori4(View view) {
        Intent intent = new Intent(this, DaftarAcara.class);

        String tag = (String) view.getTag();
        intent.putExtra("kategoriAcara", tag);
        startActivity(intent);
    }


}

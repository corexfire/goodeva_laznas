package com.bristol.laznas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.bristol.laznas.MasukActivity_;
//import com.bristol.laznas.R;

import com.bristol.laznas.helper.AppConfig;
import com.bristol.laznas.model.JSONParser;
import com.bristol.laznas.utils.SessionManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian Adi Kusumo on 3/7/2017.
 */

public class ProfilFragmentLaznas extends Fragment {

    private static final String TAG = ProfilFragmentLaznas.class.getSimpleName();
    TextView tv_logout;
    TextView tv_riwayat_donasi;
    TextView tv_riwayat_galang_dana;
    TextView tv_riwayat_transaksi;
    TextView tv_chat_with_ustadz;
    SessionManager sessionManager;
    //FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    //private FirebaseDatabase database = FirebaseDatabase.getInstance();

    //private DatabaseReference ref;
    TextView tvNama;
    ImageView ivPhoto;
    Context context;

    //private ProgressDialog pDialogProfil;

    JSONParser jsonParser = new JSONParser();
    AppConfig appConfig = new AppConfig();
    //private static final String LOGIN_URL = "http://brianadi.xyz/laznas/login.php";
    private static final String LOGIN_URL = "laznas/mobile/user/login";
    private static final String LOGOUT_URL = "laznas/mobile/user/logout";
    private String email;
    private String password;
    private String parent;
    private String name = "";
    private String uid = null;
    View rootViewProfil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //new AttemptLogin().execute("");
        sessionManager = new SessionManager(getActivity().getApplicationContext());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        rootViewProfil = inflater.inflate(R.layout.fragment_profil, parent, false);
        ivPhoto = (ImageView) rootViewProfil.findViewById(R.id.fotoPengguna);
        tvNama = (TextView) rootViewProfil.findViewById(R.id.namaPengguna);
        tvNama.setText(sessionManager.getName());
        //parent.getId();
        //new ProfilFragmentLaznas.AttemptLogin().execute("");
//        test();
        //mAuthProgressDialog = new ProgressDialog(getContext());
        //mAuthProgressDialog.setTitle("Loading..");
        //mAuthProgressDialog.setMessage("Please wait.");
        //mAuthProgressDialog.setCancelable(true);
        //this.parent = parent.toString();

        return rootViewProfil;
    }
    public void test()
    {
        new AttemptLogin(getActivity(), rootViewProfil).execute("");
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        //String tes = Integer.toString(rootView.getId());


        //new AttemptLogin().execute();
        //Log.d("Login attempt", test);


        tv_logout = (TextView) rootViewProfil.findViewById(R.id.tv_logout);


        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mFirebaseAuth.signOut();
                new AttemptLogout(getActivity(), rootViewProfil).execute("");

//                Intent intent = new Intent(getActivity(), MainActivity_.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
            }
        });

        //tv_riwayat_donasi = (TextView) rootViewProfil.findViewById(R.id.tv_riwayat_donasi);


        /*tv_riwayat_donasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mFirebaseAuth.signOut();
                Intent intent = new Intent(getActivity(), DaftarRiwayatDonasi.class);
                startActivity(intent);
            }
        });*/

        tv_riwayat_galang_dana = (TextView) rootViewProfil.findViewById(R.id.tv_riwayat_galang_dana);


        tv_riwayat_galang_dana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mFirebaseAuth.signOut();
                Intent intent = new Intent(getActivity(), DaftarRiwayatGalangDana.class);
                intent.putExtra("id", uid);
                startActivity(intent);
            }
        });

        tv_riwayat_transaksi = (TextView) rootViewProfil.findViewById(R.id.tv_riwayat_transaksi);


        tv_riwayat_transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mFirebaseAuth.signOut();
                Intent intent = new Intent(getActivity(), DaftarRiwayatTransaksi.class);

                intent.putExtra("id", uid);
                startActivity(intent);
            }
        });

        tv_chat_with_ustadz = (TextView) rootViewProfil.findViewById(R.id.tv_chat_with_ustadz);


        tv_chat_with_ustadz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mFirebaseAuth.signOut();
                String number = "6285693268369";
                openWhatsappContact(number);
            }
        });
    }

    private void openWhatsappContact(String number) {
        /*Uri uri = Uri.parse("smsto:" + number);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(i, ""));*/
        Intent intent = getContext().getPackageManager().getLaunchIntentForPackage("com.whatsapp");
        if (intent != null) {
            // We found the activity now start the activity
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //startActivity(intent);

            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number)+"@s.whatsapp.net");
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(sendIntent);
        } else {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + "com.whatsapp"));
            startActivity(intent);
        }

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

    //Login
    private class AttemptLogin extends AsyncTask<String, String, String> {

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
        public AttemptLogin(FragmentActivity activity, View rootView) {
            mContex = activity;
            view1 = rootView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialog mAuthProgressDialog;
//            ProfilFragmentLaznas myFragment = (ProfilFragmentLaznas)getFragmentManager().findFragmentByTag(TAG);
//            if (myFragment != null && myFragment.isVisible()) {
//                // add your code here
//                Log.d("yeya!", "yeay");
//            }
//
//
//            pDialogProfil3 = new ProgressDialog(view1.getContext());
//            //pDialog.setMessage("Logging in User...");
//            pDialogProfil3.setMessage("wkwk");
//            pDialogProfil3.setIndeterminate(false);
//            pDialogProfil3.setCancelable(true);
//            pDialogProfil3.show();
            //Log.d("parent!", parent);
        }

        @Override
        protected String doInBackground(String... args) {
            // Auto-generated method stub
            // Check for success tag
            int success;

            //String user = "brian";
            //String pass = "123456";
//            String user = ((LaznasApp) getActivity().getApplication()).getUserName();
//            String pass = ((LaznasApp) getActivity().getApplication()).getPassword();
            String user = sessionManager.getUser();
            String pass = sessionManager.getPass();
            Log.v(TAG, "user: " + user);
            Log.v(TAG, "pass: " + pass);
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("name", user));
                params.add(new BasicNameValuePair("pass", pass));

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        appConfig.api_url() + LOGIN_URL, "POST", params);

                // check your log for json response
                //Log.d("Login attempt", json.toString());

                // json success tag
                //success = json.getInt(TAG_SUCCESS);
                success = 0;
                if (json != null) {
                    Log.d("Login attempt", json.toString());

                    uid = json.get("uid").toString();
                    name = json.get("name").toString();
                    Log.d("Login attempt", uid);
                    int id = Integer.decode(uid);
                    //((LaznasApp) MasukActivityLaznas.this.getApplication()).setUid(uid);

                        //pDialogProfil3.dismiss();
                    if (id == 0 || uid == null){
                        Log.d("Login attempt Front", "Login Failed");
                        Intent intent = new Intent(getActivity(), MasukActivityLaznas_.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    //startActivity(new Intent(MasukActivityLaznas.this, MainActivity_.class));
                    //finish();
                } else {
                    Log.d("Login attempt Front", "Login Failed");
                    Intent intent = new Intent(getActivity(), MasukActivityLaznas_.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            }catch (JSONException e) {
                e.printStackTrace();
                Log.d("Login attempt Front", "Login Failed");
                Intent intent = new Intent(getActivity(), MasukActivityLaznas_.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            return name;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            //pDialogProfil3.dismiss();
            //Perform all UI changes here
            tvNama.setText(name);

        }


    }

    //Logout
    private class AttemptLogout extends AsyncTask<String, String, String> {

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
        public AttemptLogout(FragmentActivity activity, View rootView) {
            mContex = activity;
            view1 = rootView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialog mAuthProgressDialog;
            ProfilFragmentLaznas myFragment = (ProfilFragmentLaznas)getFragmentManager().findFragmentByTag(TAG);
            if (myFragment != null && myFragment.isVisible()) {
                // add your code here
                Log.d("yeya!", "yeay");
            }


            pDialogProfil3 = new ProgressDialog(view1.getContext());
            //pDialog.setMessage("Logging in User...");
            pDialogProfil3.setMessage("Log Out..");
            pDialogProfil3.setIndeterminate(false);
            pDialogProfil3.setCancelable(false);
            pDialogProfil3.show();
            //Log.d("parent!", parent);
        }

        @Override
        protected String doInBackground(String... args) {
            // Auto-generated method stub
            // Check for success tag
            int success;

//            String user = "brian";
//            String pass = "123456";
            //try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
//                params.add(new BasicNameValuePair("name", user));
//                params.add(new BasicNameValuePair("pass", pass));

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        appConfig.api_url() + LOGOUT_URL, "POST", params);

                // check your log for json response
                //Log.d("Login attempt", json.toString());

                // json success tag
                //success = json.getInt(TAG_SUCCESS);
                success = 0;
                if (json != null) {
                    Log.d("Login attempt", json.toString());
                } else {
                    Log.d("Login attempt Front", "Login Failed");
                    Intent intent = new Intent(getActivity(), MasukActivityLaznas_.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            //}
//            catch (JSONException e) {
//                e.printStackTrace();
//            }

            return name;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialogProfil3.dismiss();
            //Perform all UI changes here
//            ((LaznasApp) getActivity().getApplication()).setUserName(null);
//            ((LaznasApp) getActivity().getApplication()).setFlag(0);
            sessionManager.setLogin(false);
            sessionManager.clearUser();
            sessionManager.clearPass();
            sessionManager.clearUID();
            sessionManager.clearName();
            Intent intent = new Intent(getActivity(), MasukActivityLaznas_.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }


    }



}

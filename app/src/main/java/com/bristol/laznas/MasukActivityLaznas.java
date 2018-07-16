package com.bristol.laznas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bristol.laznas.helper.AppConfig;
import com.bristol.laznas.model.JSONParser;
import com.bristol.laznas.utils.SessionManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Brian Adi Kusumo on 3/6/2017.
 */

@EActivity(R.layout.activity_masuk)
public class MasukActivityLaznas extends AppCompatActivity {

    private ProgressDialog pDialog;
    SessionManager sessionManager;
    JSONParser jsonParser = new JSONParser();
    AppConfig appConfig = new AppConfig();
    //private static final String LOGIN_URL = "http://brianadi.xyz/laznas/login.php";
    private static final String LOGIN_URL = "laznas/mobile/user/login";
    private String email;
    private String password;


    private static final String TAG = MasukActivityLaznas_.class.getSimpleName();
    private ProgressDialog mAuthProgressDialog;

    @ViewById(R.id.et_email)
    EditText et_username;
    @ViewById(R.id.et_password)
    EditText et_password;

    //private FirebaseAuth mFirebaseAuth;

    @AfterViews
    void init() {
        sessionManager = new SessionManager(getApplicationContext());
        if(sessionManager.isLoggedIn()){
            startActivity(new Intent(MasukActivityLaznas.this, MainActivity_.class));
            finish();
        }
        //mFirebaseAuth = FirebaseAuth.getInstance();

        /* Setup the progress dialog that is displayed later when authenticating with Firebase */
        //mAuthProgressDialog = new ProgressDialog(this);
        //mAuthProgressDialog.setTitle("Loading..");
        //mAuthProgressDialog.setMessage("Please wait.");
        //mAuthProgressDialog.setCancelable(true);
    }

    @Click(R.id.btn_masuk)
    void masuk() {
        Log.v(TAG, "button masuk clicked");
        //mAuthProgressDialog.show();

        email = et_username.getText().toString();
        password = et_password.getText().toString();

        boolean validEmail = isEmailValid(email);
        boolean validPassword = isPasswordValid(password);

        if (validEmail || validPassword) {
            ((LaznasApp) this.getApplication()).setUserName(email);
            ((LaznasApp) this.getApplication()).setPassword(password);
            new AttemptLogin().execute();
        }
        else{
            Toast.makeText(MasukActivityLaznas.this, "gagal",
                    Toast.LENGTH_SHORT).show();
        }




        //Drupal Authorization
    }

    public void toDaftarActivity(View view) {
        Intent intent = new Intent(this, DaftarActivity_.class);
        startActivity(intent);
    }

    private boolean isEmailValid(String email) {
        boolean isGoodEmail =
                (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            et_username.setError(String.format(getString(R.string.error_invalid_email_not_valid), email));
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 6) {
            et_password.setError(getResources().getString(R.string.error_invalid_password_not_valid));
            return false;
        }
        return true;
    }

    //Login
    private class AttemptLogin extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MasukActivityLaznas.this);
            pDialog.setMessage("Logging in User...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            //  Auto-generated method stub
            // Check for success tag
            int success;

            //String user = "brian";
            //String pass = "123456";
            String user = email;
            String pass = password;

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("name", user));
                params.add(new BasicNameValuePair("pass", pass));
                Log.d("Username", user);
                Log.d("Password", pass);
                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        appConfig.api_url() + LOGIN_URL, "POST", params);

                // check your log for json response
                Log.d("Json = ", json.toString());

                // json success tag
                //success = json.getInt(TAG_SUCCESS);
                //success = 0;
                if (json != null) {
                    Log.d("Login attempt", json.toString());
                    String uid = json.get("uid").toString();
                    String name = json.get("name").toString();
//                    String roles_Id = "";
                    JSONObject jsonObject1 = json.getJSONObject("roles");
//                    for(int i =0; i < jsonObject1.length();i++){
//                         roles_Id = jsonObject1.getString(i);
//                    }

                    String roles_Id = "";
                    Iterator iteratorKey = jsonObject1.keys();
                    while (iteratorKey.hasNext()) {
                         roles_Id = (String) iteratorKey.next();
                        String roles_Name = jsonObject1.getString(roles_Id);
                    }

                    Log.d("Login uid", uid);
//                    ((LaznasApp) MasukActivityLaznas.this.getApplication()).setUid(uid);
//                    ((LaznasApp) MasukActivityLaznas.this.getApplication()).setName(name);
                    pDialog.dismiss();
                    sessionManager.setLogin(true);
                    sessionManager.setUser(((LaznasApp) getApplication()).getUserName());
                    sessionManager.setPass(((LaznasApp) getApplication()).getPassword());
                    sessionManager.setUID(uid);
                    sessionManager.setName(name);
                    sessionManager.setRolesId(roles_Id);
                    startActivity(new Intent(MasukActivityLaznas.this, MainActivity_.class));
                    finish();
                } else {
                    pDialog.dismiss();
                    Log.d("Login attempt Front", "Login Failed");
                    Toast.makeText(MasukActivityLaznas.this, "gagal",
                            Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e) {
                e.printStackTrace();
                pDialog.dismiss();
                Log.d("Login attempt Front", "Login Failed");
//                Toast.makeText(getApplicationContext(), "gagal",
//                        Toast.LENGTH_SHORT).show();
                runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {
                new AlertDialog.Builder(MasukActivityLaznas.this)
                        .setTitle("Gagal Login")
                        .setMessage("Cek email dan password anda")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Whatever...
                            }
                        }).show();
                                  }
                });
            }

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

package com.bristol.laznas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bristol.laznas.helper.AppConfig;
import com.bristol.laznas.model.JSONParser;
import com.bristol.laznas.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_daftar)
public class DaftarActivity extends AppCompatActivity {

    private static final String TAG = DaftarActivity.class.getSimpleName();
    //private FirebaseAuth mFirebaseAuth;
    private ProgressDialog mAuthProgressDialog;
    //private DatabaseReference mFirebaseDatabaseReference;

    @ViewById(R.id.email_et)
    EditText email_et;
    @ViewById(R.id.et_password)
    EditText password_et;
    @ViewById(R.id.et_password_konfirm)
    EditText password_konfirm_et;
    AppConfig appConfig = new AppConfig();
    private static final String REGISTER_URL = "laznas/mobile/user/register";
    private String email;
    private String password;
    private String passwordKonfirm;

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    @AfterViews
    void init() {

        // init the firebase
        //mFirebaseAuth = FirebaseAuth.getInstance();
        //mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        // init progress dialog
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading..");
        mAuthProgressDialog.setMessage("Please wait.");
        mAuthProgressDialog.setCancelable(false);

    }

    @Click(R.id.btn_daftar)
    void daftar() {
        //mAuthProgressDialog.show();
        email = email_et.getText().toString();
        password = password_et.getText().toString();
        passwordKonfirm = password_konfirm_et.getText().toString();

        boolean validEmail = isEmailValid(email);
        boolean validPassword = isPasswordValid(password);
        boolean validKonfirmPassword = isPasswordKonfirmValid(password, passwordKonfirm);

        if (!validEmail || !validPassword || !validKonfirmPassword) {
            //mAuthProgressDialog.dismiss();
            Log.v(TAG, "masuk ke if");
            return;
        }
        else{
            new AttemptRegister().execute();
        }


    }

    public void toMasukActivity(View view) {
        Intent intent = new Intent(this, MainActivity_.class);
        startActivity(intent);
    }

    private boolean isEmailValid(String email) {
        boolean isGoodEmail =
                (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            email_et.setError(String.format(getString(R.string.error_invalid_email_not_valid), email));
            return false;
        }
        return true;
    }


    private boolean isPasswordValid(String password) {
        if (password.length() < 6) {
            password_et.setError(getResources().getString(R.string.error_invalid_password_not_valid));
            return false;
        }
        return true;
    }

    private boolean isPasswordKonfirmValid(String password, String password_konfirm) {
        if (!password.equals(password_konfirm)) {
            password_konfirm_et.setError(getResources().getString(R.string.error_invalid_password_konfirm_not_valid));
            return false;
        }
        return true;
    }

    private void createUserHelper(final String email) {
        final String encodedEmail = Utils.encodeEmail(email);

        //final DatabaseReference userLocation = mFirebaseDatabaseReference.child("user").child(encodedEmail);

//        userLocation.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.getValue() == null) {
//                    User newUser = new User(namaLengkap, username, email, nomorHandphone, "");
//                    userLocation.setValue(newUser);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d(TAG, "error occurred: " + databaseError.getMessage());
//            }
//        });
    }

    //Login
    private class AttemptRegister extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DaftarActivity.this);
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
                        appConfig.api_url() + REGISTER_URL, "POST", params);

                // check your log for json response
                Log.d("Json = ", json.toString());

                // json success tag
                //success = json.getInt(TAG_SUCCESS);
                //success = 0;
                if (json != null) {
                    Log.d("Login attempt", json.toString());
                    String name = json.get("name").toString();
                    pDialog.dismiss();
                    startActivity(new Intent(DaftarActivity.this, MainActivity_.class));
                    finish();
                } else {
                    Log.d("Login attempt Front", "Login Failed");
                    Toast.makeText(DaftarActivity.this, "gagal",
                            Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e) {
                e.printStackTrace();
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
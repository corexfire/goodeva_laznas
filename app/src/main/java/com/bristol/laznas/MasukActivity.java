package com.bristol.laznas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_masuk)
public class MasukActivity extends AppCompatActivity {
    private static final String TAG = MasukActivity_.class.getSimpleName();
    private ProgressDialog mAuthProgressDialog;

    @ViewById(R.id.et_email)
    EditText et_username;
    @ViewById(R.id.et_password)
    EditText et_password;

    private FirebaseAuth mFirebaseAuth;

    @AfterViews
    void init() {
        mFirebaseAuth = FirebaseAuth.getInstance();

        /* Setup the progress dialog that is displayed later when authenticating with Firebase */
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading..");
        mAuthProgressDialog.setMessage("Please wait.");
        mAuthProgressDialog.setCancelable(false);
    }

    @Click(R.id.btn_masuk)
    void masuk() {
        Log.v(TAG, "button masuk clicked");
        mAuthProgressDialog.show();

        String email = et_username.getText().toString();
        String password = et_password.getText().toString();

        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            mAuthProgressDialog.dismiss();
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(MasukActivity.this, "gagal",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            mAuthProgressDialog.dismiss();
                            startActivity(new Intent(MasukActivity.this, MainActivity_.class));
                            finish();
                        }
                    }
                });
    }

    public void toDaftarActivity(View view) {
        Intent intent = new Intent(this, DaftarActivity_.class);
        startActivity(intent);
    }


}

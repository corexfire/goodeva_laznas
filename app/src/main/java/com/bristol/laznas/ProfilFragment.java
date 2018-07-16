package com.bristol.laznas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bristol.laznas.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;


public class ProfilFragment extends Fragment {

    private static final String TAG = ProfilFragment.class.getSimpleName();
    TextView tv_logout;
    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ProgressDialog mAuthProgressDialog;
    private DatabaseReference ref;
    TextView tvNama;
    ImageView ivPhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_profil, container, false);
        ivPhoto = (ImageView) rootView.findViewById(R.id.fotoPengguna);
        tvNama = (TextView) rootView.findViewById(R.id.namaPengguna);
        final ProgressDialog pd = ProgressDialog.show(context, "Loading...", "Please wait...", true);
        pd.setCancelable(true);

        String email = mFirebaseAuth.getCurrentUser().getEmail();
        email = Utils.encodeEmail(email);

        ref = database.getReference("/user/" + email);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                if (user.getFoto().equals("")) {
                    tvNama.setText(user.getNama());
                } else {
                    new DownloadImageTask(ivPhoto).execute(user.getFoto());
                    tvNama.setText(user.getNama());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAuthProgressDialog = new ProgressDialog(getContext());
        mAuthProgressDialog.setTitle("Loading..");
        mAuthProgressDialog.setMessage("Please wait.");
        mAuthProgressDialog.setCancelable(false);

        tv_logout = (TextView) rootView.findViewById(R.id.tv_logout);
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();

                Intent intent = new Intent(getActivity(), MasukActivity_.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        return rootView;
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

    public Context context;
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        context = activity;
    }

}

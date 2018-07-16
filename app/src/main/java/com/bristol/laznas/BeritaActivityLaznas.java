package com.bristol.laznas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@EActivity(R.layout.activity_berita_laznas)
public class BeritaActivityLaznas extends AppCompatActivity {

    private String id;

    private String deskripsi;
    private String nama;
    private String batas_waktu;
    private String target;
    private String gambar;
   // private FirebaseDatabase database = FirebaseDatabase.getInstance();
    //private DatabaseReference ref;
    private static final String TAG = BeritaActivityLaznas.class.getSimpleName();
    private ValueEventListener acaraListener;

    @ViewById(R.id.tv_deskripsi)
    TextView tvDeskripsi;
    @ViewById(R.id.tv_fb)
    TextView tvFb;
    @ViewById(R.id.tv_twitter)
    TextView tvTwitter;
    @ViewById(R.id.tv_website)
    TextView tvWebsite;
    @ViewById(R.id.tv_nama_acara)
    TextView tvNamaAcara;
    @ViewById(R.id.fotoAcara)
    ImageView tvGambar;


    @AfterViews
    void init() {

        Intent intent = this.getIntent();
        if( getIntent().getExtras() != null){
            deskripsi = intent.getStringExtra("deskripsi");
            nama = intent.getStringExtra("nama");
            gambar = intent.getStringExtra("gambar");
            Log.v(TAG, "deskripsi of acara: " + deskripsi);
            Log.v(TAG, "nama of acara: " + nama);
            Log.v(TAG, "gambar of acara: " + gambar);
            tvNamaAcara.setText(nama);
            tvDeskripsi.setText(deskripsi);
            new DownloadImageTask(tvGambar).execute(gambar);
        }

        //ref = database.getReference("/acara/" + id);
        //Log.v(TAG, "reference: " + ref);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //ref.removeEventListener(acaraListener);
    }

    public void toPesertaAcara(View view) {
        Intent intent = new Intent(this, PesertaAcara.class);
        /*String tag =  (String) view.getTag();
        intent.putExtra("acaraUser", tag);*/
        startActivity(intent);
    }

    public void toBeliTiket(View view) {
        Intent intent = new Intent(this, BeliTiketActivityLaznas_.class);
        /*String tag =  (String) view.getTag();
        intent.putExtra("acaraUser", tag);*/
        intent.putExtra("deskripsi", deskripsi);
        intent.putExtra("nama", nama);
        intent.putExtra("batas_waktu", batas_waktu);
        intent.putExtra("target", target);
        intent.putExtra("gambar", gambar);

        startActivity(intent);
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
                options.inSampleSize=2; //decrease decoded image
                mIcon11 = BitmapFactory.decodeStream(in,null,options);
                mIcon11.compress(Bitmap.CompressFormat.JPEG, 70, out);
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
}

package com.bristol.laznas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bristol.laznas.model.Acara;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_acara)
public class AcaraActivity extends AppCompatActivity {

    private String id;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref;
    private static final String TAG = AcaraActivity.class.getSimpleName();
    private ValueEventListener acaraListener;

    @ViewById(R.id.tv_tanggal)
    TextView tvTanggal;
    //@ViewById(R.id.tv_jam)
    //TextView tvJam;
    @ViewById(R.id.tv_harga)
    TextView tvHarga;
    @ViewById(R.id.tv_deskripsi)
    TextView tvDeskripsi;
    //@ViewById(R.id.tv_lokasi)
    //TextView tvLokasi;
    @ViewById(R.id.tv_lokasi_detail)
    TextView tvLokasiDetail;
    @ViewById(R.id.tv_no_tlp)
    TextView tvNoTlp;
    @ViewById(R.id.tv_fb)
    TextView tvFb;
    @ViewById(R.id.tv_twitter)
    TextView tvTwitter;
    @ViewById(R.id.tv_website)
    TextView tvWebsite;
    @ViewById(R.id.tv_nama_acara)
    TextView tvNamaAcara;

    @AfterViews
    void init() {

        Intent intent = this.getIntent();
        id = intent.getStringExtra("id");
        Log.v(TAG, "id of acara: " + id);

        ref = database.getReference("/acara/" + id);
        Log.v(TAG, "reference: " + ref);

        acaraListener = ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Acara acara = dataSnapshot.getValue(Acara.class);

                tvTanggal.setText(acara.getTanggal());
                //tvJam.setText(acara.getJam_mulai() + "-" + acara.getJam_selesai());
                //tvLokasi.setText(acara.getLokasi());
                tvHarga.setText(acara.getHarga());
                tvNamaAcara.setText(acara.getNama());
                tvDeskripsi.setText(acara.getDeskripsi());
                tvLokasiDetail.setText(acara.getLokasi());
                tvNoTlp.setText(acara.getTelepon());
                tvFb.setText(acara.getFb());
                Log.v(TAG, "fb: " + acara.getFb());
                tvTwitter.setText(acara.getTwitter());
                tvWebsite.setText(acara.getWebsite());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ref.removeEventListener(acaraListener);
    }

    public void toPesertaAcara(View view) {
        Intent intent = new Intent(this, PesertaAcara.class);
        /*String tag =  (String) view.getTag();
        intent.putExtra("acaraUser", tag);*/
        startActivity(intent);
    }

    public void toBeliTiket(View view) {
        Intent intent = new Intent(this, BeliTiketActivity_.class);
        /*String tag =  (String) view.getTag();
        intent.putExtra("acaraUser", tag);*/
        startActivity(intent);
    }
}

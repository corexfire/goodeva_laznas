package com.bristol.laznas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bristol.laznas.model.Acara;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class BerandaFragment extends Fragment {

    ListView lv;
    Context context;
    private FirebaseRecyclerAdapter<Acara, ListViewHolder> mFirebaseAdapter;
    private DatabaseReference mFirebaseDatabaseReference;
    private RecyclerView mListRecyclerViewAcara;
    private LinearLayoutManager mLinearLayoutManager;

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

    public BerandaFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_beranda, container, false);

        context = getContext();

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mLinearLayoutManager = new LinearLayoutManager(context);

        mListRecyclerViewAcara = (RecyclerView) rootView.findViewById(R.id.Recycler_View);
        mListRecyclerViewAcara.setLayoutManager(mLinearLayoutManager);


        //FireBase Activity. Getting data from firebase.
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Acara, ListViewHolder>(
                Acara.class, R.layout.list_acara,
                ListViewHolder.class,
                mFirebaseDatabaseReference.child("acara")) {
            @Override
            protected void populateViewHolder(ListViewHolder viewHolder, Acara model, int position) {

                viewHolder.tv_judul.setText(model.getNama());
                viewHolder.tv_deskripsi.setText(model.getDeskripsi());
                viewHolder.tv_tanggal.setText(model.getTanggal());
                viewHolder.tv_harga.setText(model.getHarga());

                Log.v(TAG, "getRef(position)" + getRef(position));
                final String id = getRef(position).getKey();

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), AcaraActivity_.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                });
            }
        };

        mListRecyclerViewAcara.setAdapter(mFirebaseAdapter);

        return rootView;
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Fragment fragment = getFragmentManager().findFragmentById(0);
//        if(fragment != null)
//            getFragmentManager().beginTransaction().remove(fragment).commit();
//        mFirebaseAdapter.cleanup();
//    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_judul;
        public TextView tv_deskripsi;
        public TextView tv_tanggal;
        public TextView tv_harga;
        View mView;

        public ListViewHolder(View v) {
            super(v);

            mView = v;

            tv_judul = (TextView) itemView.findViewById(R.id.namaAcara);
            tv_deskripsi = (TextView) itemView.findViewById(R.id.deskripsiAcara);
            tv_tanggal = (TextView) itemView.findViewById(R.id.waktuAcara);
            tv_harga = (TextView) itemView.findViewById(R.id.hargaAcara);
        }
    }


}

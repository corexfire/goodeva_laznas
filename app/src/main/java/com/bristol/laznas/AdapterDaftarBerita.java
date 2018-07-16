package com.bristol.laznas;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class AdapterDaftarBerita extends BaseAdapter {

    ArrayList<String> foto = new ArrayList<String>();
    ArrayList<String> nama = new ArrayList<String>();
    ArrayList<String> deskripsi = new ArrayList<String>();

    Context context;
    private static LayoutInflater inflater = null;

    public AdapterDaftarBerita(DaftarRiwayatBerita daftarAcara, ArrayList<String> fotoAcara, ArrayList<String> namaAcara, ArrayList<String> deskripsiArray) {
        //  Auto-generated constructor stub
        context = daftarAcara;

        foto = fotoAcara;
        nama = namaAcara;
        deskripsi = deskripsiArray;

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // Auto-generated method stub
        return nama.size();
    }

    @Override
    public Object getItem(int position) {
        // Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView namaAcara;
        ImageView fotoAcara;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Auto-generated method stub
        final Holder holder = new Holder();
        View rowView = inflater.inflate(R.layout.list_berita, null);

        holder.fotoAcara = (ImageView) rowView.findViewById(R.id.gambar1);
        holder.namaAcara = (TextView) rowView.findViewById(R.id.judul1);
        String imgUrl = foto.get(position);
        //Picasso
        Picasso.with(context).load(imgUrl).resize(300, 100).into(holder.fotoAcara);
        //holder.fotoAcara.setImageResource(foto[position]);
        holder.namaAcara.setText(nama.get(position));

        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Auto-generated method stub
                //Toast.makeText(context, "You Clicked "+nama[position], Toast.LENGTH_LONG).show();

                //String tag =  (String) v.getTag();
                //intent.putExtra("acaraUser", tag);

                //Intent intent = new Intent(context, MainActivity_.class);
                //context.startActivity(intent);

                Intent intent = new Intent(v.getContext(), BeritaActivityLaznas_.class);
                intent.putExtra("deskripsi", deskripsi.get(position));
                intent.putExtra("nama", nama.get(position));
                intent.putExtra("gambar", foto.get(position));
                v.getContext().startActivity(intent);
            }
        });
        return rowView;
    }

}
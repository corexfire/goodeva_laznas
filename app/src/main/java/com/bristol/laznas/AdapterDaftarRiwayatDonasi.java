package com.bristol.laznas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterDaftarRiwayatDonasi extends BaseAdapter {
    int[] foto;
    String[] nama;
    String[] deskripsi;
    String[] waktu;
    String[] harga;

    Context context;
    private static LayoutInflater inflater = null;

    public AdapterDaftarRiwayatDonasi(DaftarRiwayatDonasi daftarAcara, int[] fotoAcara, String[] namaAcara, String[] deskripsiAcara, String[] waktuAcara, String[] hargaAcara) {
        //  Auto-generated constructor stub
        context = daftarAcara;

        foto = fotoAcara;
        nama = namaAcara;
        deskripsi = deskripsiAcara;
        waktu = waktuAcara;
        harga = hargaAcara;

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // Auto-generated method stub
        return nama.length;
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
        TextView deskripsiAcara;
        TextView waktuAcara;
        TextView hargaAcara;
        ImageView fotoAcara;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Auto-generated method stub
        Holder holder = new Holder();
        View rowView = inflater.inflate(R.layout.list_riwayat_donasi, null);

        //holder.fotoAcara = (ImageView) rowView.findViewById(R.id.fotoAcara);
        holder.namaAcara = (TextView) rowView.findViewById(R.id.namaAcara);
        //holder.deskripsiAcara = (TextView) rowView.findViewById(R.id.deskripsiAcara);
        holder.waktuAcara = (TextView) rowView.findViewById(R.id.waktuAcara);
        holder.hargaAcara = (TextView) rowView.findViewById(R.id.hargaAcara);

        //holder.fotoAcara.setImageResource(foto[position]);
        holder.namaAcara.setText(nama[position]);
        //holder.deskripsiAcara.setText(deskripsi[position]);
        holder.waktuAcara.setText(waktu[position]);
        holder.hargaAcara.setText(harga[position]);

        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Auto-generated method stub
                //Toast.makeText(context, "You Clicked "+nama[position], Toast.LENGTH_LONG).show();

                //String tag =  (String) v.getTag();
                //intent.putExtra("acaraUser", tag);

                Intent intent = new Intent(context, AcaraActivity_.class);
                context.startActivity(intent);
            }
        });
        return rowView;
    }

}
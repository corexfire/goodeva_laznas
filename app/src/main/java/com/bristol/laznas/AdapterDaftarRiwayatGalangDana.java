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

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class AdapterDaftarRiwayatGalangDana extends BaseAdapter {
    ArrayList<String> foto;
    ArrayList<String> nama;
    ArrayList<String> deskripsi;
    ArrayList<String> waktu;
    ArrayList<String> harga;
    ArrayList<String> status;

    Context context;
    private static LayoutInflater inflater = null;

    public AdapterDaftarRiwayatGalangDana(DaftarRiwayatGalangDana daftarAcara, ArrayList<String> fotoAcara,
                                          ArrayList<String> namaAcara, ArrayList<String> deskripsiAcara,
                                          ArrayList<String> waktuAcara, ArrayList<String> hargaAcara,
                                          ArrayList<String> statusGalangDana) {
        //  Auto-generated constructor stub
        context = daftarAcara;

        foto = fotoAcara;
        nama = namaAcara;
        deskripsi = deskripsiAcara;
        waktu = waktuAcara;
        harga = hargaAcara;
        status = statusGalangDana;

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
        TextView deskripsiAcara;
        TextView waktuAcara;
        TextView hargaAcara;
        TextView status;
        ImageView fotoAcara;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Auto-generated method stub
        Holder holder = new Holder();
        View rowView = inflater.inflate(R.layout.list_riwayat_galang_dana, null);

        holder.fotoAcara = (ImageView) rowView.findViewById(R.id.fotoAcara);
        holder.namaAcara = (TextView) rowView.findViewById(R.id.namaAcara);
        holder.deskripsiAcara = (TextView) rowView.findViewById(R.id.deskripsiAcara);
        holder.waktuAcara = (TextView) rowView.findViewById(R.id.waktuAcara);
        holder.hargaAcara = (TextView) rowView.findViewById(R.id.hargaAcara);
        holder.status = (TextView) rowView.findViewById(R.id.statusGalangDana);

        int frmt = Integer.parseInt(harga.get(position));
        DecimalFormat dfmt = new DecimalFormat();
        DecimalFormatSymbols fmts = new DecimalFormatSymbols();

        fmts.setGroupingSeparator('.');

        dfmt.setGroupingSize(3);
        dfmt.setGroupingUsed(true);
        dfmt.setDecimalFormatSymbols(fmts);
        String out = dfmt.format(frmt);

        //holder.fotoAcara.setImageResource(foto.get(position));
        String imgUrl = foto.get(position);
        //Picasso
        Picasso.with(this.context).load(imgUrl).into(holder.fotoAcara);
        holder.namaAcara.setText(nama.get(position));
        holder.deskripsiAcara.setText(deskripsi.get(position));
        holder.waktuAcara.setText(waktu.get(position));
        String tempStatus = "";
        if(status.get(position).equals("1")){
            tempStatus = "Publish";
        }
        else{
            tempStatus = "Unpublish";
        }
        holder.status.setText(tempStatus);
        holder.hargaAcara.setText("Rp "+ out);

        /*rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Auto-generated method stub
                //Toast.makeText(context, "You Clicked "+nama[position], Toast.LENGTH_LONG).show();

                //String tag =  (String) v.getTag();
                //intent.putExtra("acaraUser", tag);

                Intent intent = new Intent(context, AcaraActivity_.class);
                context.startActivity(intent);
            }
        });*/
        return rowView;
    }

}
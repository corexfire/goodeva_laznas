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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class AdapterDaftarRiwayatTransaksi extends BaseAdapter {
    ArrayList<String> foto;
    ArrayList<String> nama;
    ArrayList<String> deskripsi;
    ArrayList<String> waktu;
    ArrayList<String> harga;
    ArrayList<String> status;

    Context context;
    private static LayoutInflater inflater = null;

    public AdapterDaftarRiwayatTransaksi(DaftarRiwayatTransaksi daftarAcara, ArrayList<String> fotoAcara, ArrayList<String> namaAcara, ArrayList<String> deskripsiAcara,
                                         ArrayList<String> waktuAcara, ArrayList<String> hargaAcara, ArrayList<String> statusTransaksi) {
        //  Auto-generated constructor stub
        context = daftarAcara;

        foto = fotoAcara;
        nama = namaAcara;
        deskripsi = deskripsiAcara;
        waktu = waktuAcara;
        harga = hargaAcara;
        status = statusTransaksi;

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
        TextView waktuTransaksi;
        TextView hargaAcara;
        TextView status;
        ImageView fotoAcara;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Auto-generated method stub
        Holder holder = new Holder();
        View rowView = inflater.inflate(R.layout.list_riwayat_transaksi, null);

        //holder.fotoAcara = (ImageView) rowView.findViewById(R.id.fotoAcara);
        holder.namaAcara = (TextView) rowView.findViewById(R.id.namaAcara);
        //holder.deskripsiAcara = (TextView) rowView.findViewById(R.id.deskripsiAcara);
        //holder.waktuAcara = (TextView) rowView.findViewById(R.id.waktuAcara);
        holder.hargaAcara = (TextView) rowView.findViewById(R.id.hargaAcara);
        holder.status = (TextView) rowView.findViewById(R.id.statusTransaksi);
        holder.waktuTransaksi = (TextView) rowView.findViewById(R.id.waktuAcara);
        String total = "0";
        if(position < harga.size()){
            total = harga.get(position).substring(0, harga.get(position).length() - 2);
        }


        int frmt = Integer.parseInt(total);
        DecimalFormat dfmt = new DecimalFormat();
        DecimalFormatSymbols fmts = new DecimalFormatSymbols();

        fmts.setGroupingSeparator('.');

        dfmt.setGroupingSize(3);
        dfmt.setGroupingUsed(true);
        dfmt.setDecimalFormatSymbols(fmts);
        String out = dfmt.format(frmt);

        //holder.fotoAcara.setImageResource(foto[position]);
        holder.namaAcara.setText(nama.get(position));
        //holder.deskripsiAcara.setText(deskripsi[position]);
        //holder.waktuAcara.setText(waktu.get(position));
        holder.hargaAcara.setText("Rp "+out);
        holder.status.setText(status.get(position));
        holder.waktuTransaksi.setText(waktu.get(position));

        /*rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Auto-generated method stub
                //Toast.makeText(context, "You Clicked "+nama[position], Toast.LENGTH_LONG).show();

                //String tag =  (String) v.getTag();
                //intent.putExtra("acaraUser", tag);

                Intent intent = new Intent(context, MainActivity_.class);
                context.startActivity(intent);
            }
        });*/
        return rowView;
    }

}
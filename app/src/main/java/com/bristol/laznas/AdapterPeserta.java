package com.bristol.laznas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterPeserta extends BaseAdapter {
    int[] foto;
    String[] nama;

    Context context;
    private static LayoutInflater inflater = null;

    public AdapterPeserta(PesertaAcara pesertaAcara, int[] fotoPeserta, String[] namaPeserta) {
        //  Auto-generated constructor stub
        context = pesertaAcara;

        foto = fotoPeserta;
        nama = namaPeserta;

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
        ImageView fotoPeserta;
        TextView namaPeserta;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Auto-generated method stub
        Holder holder = new Holder();
        View rowView = inflater.inflate(R.layout.list_peserta, null);

        holder.fotoPeserta = (ImageView) rowView.findViewById(R.id.fotoPeserta);
        holder.namaPeserta = (TextView) rowView.findViewById(R.id.namaPeserta);

        holder.fotoPeserta.setImageResource(foto[position]);
        holder.namaPeserta.setText(nama[position]);

        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Auto-generated method stub

                /*Intent intent = new Intent(context, AcaraActivity_.class);
                context.startActivity(intent);*/
            }
        });
        return rowView;
    }

}
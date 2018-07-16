package com.bristol.laznas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bristol.laznas.AdapterAcara;
import com.bristol.laznas.PanduanActivity;
import com.bristol.laznas.R;

public class PanduanAdapter extends BaseAdapter {
    String[] title;
    String[] desc;
    String[] id;

    Context context;
    private static LayoutInflater inflater = null;

    public PanduanAdapter(PanduanActivity panduanActivity, String[] title, String desc[] ){
        context = panduanActivity.getApplicationContext();
        this.title = title;
        this.desc = desc;

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView title;
        TextView desc;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PanduanAdapter.Holder holder = new PanduanAdapter.Holder();
        View rowView = inflater.inflate(R.layout.list_panduan, null);

        holder.title = (TextView) rowView.findViewById(R.id.title);
        holder.desc = (TextView) rowView.findViewById(R.id.desc);

        holder.title.setText(title[position]);
        holder.desc.setText(desc[position]);

        return rowView;
    }
}

package com.bristol.laznas.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bristol.laznas.AcaraActivityLaznas_;
import com.bristol.laznas.DonasiAdapter;
import com.bristol.laznas.DonasiModel;
import com.bristol.laznas.GalangDanaDetailActivity_;
import com.bristol.laznas.R;
import com.bristol.laznas.ZakatActivityLaznas_;
import com.bristol.laznas.helper.ConvertRupiah;
import com.bristol.laznas.helper.HitungPresentase;
import com.bristol.laznas.model.GalangDanaModel;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GalangDanaAdapter  extends ArrayAdapter<GalangDanaModel> {
        Context context;
public GalangDanaAdapter(Context context, int resourceId, List<GalangDanaModel> items){
        super(context, resourceId, items);
        this.context = context;
        }

private class ViewHolder{
    TextView title;
    TextView desc;
    TextView target_donasi;
    ImageView img;
    TextView terkumpul;
    ProgressBar progressBar;
    TextView persentaseProgress;
    TextView donatur;
    TextView sisa_hari;
    Button btn_donasi;
}

    public View getView(int position, View convertView, ViewGroup parent){
        GalangDanaAdapter.ViewHolder holder = null;
        final GalangDanaModel rowItem = getItem(position);
        ConvertRupiah convertRupiah;
        convertRupiah = new ConvertRupiah();
        HitungPresentase hitungPresentase = new HitungPresentase();

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.list_galang_dana, null);
            holder = new GalangDanaAdapter.ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.desc = (TextView) convertView.findViewById(R.id.desc);
            holder.target_donasi = (TextView) convertView.findViewById(R.id.target_donasi);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.terkumpul = (TextView) convertView.findViewById(R.id.terkumpul);
            holder.persentaseProgress = (TextView) convertView.findViewById(R.id.persentaseProgress);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
            holder.donatur = (TextView) convertView.findViewById(R.id.donatur);
            holder.sisa_hari = (TextView) convertView.findViewById(R.id.sisa_hari);
            holder.btn_donasi = (Button) convertView.findViewById(R.id.btn_donasi);
            convertView.setTag(holder);
        }else{
            holder = (GalangDanaAdapter.ViewHolder) convertView.getTag();
        }
        holder.title.setText(rowItem.getTitle());
        holder.desc.setText(rowItem.getDesc());
        holder.target_donasi.setText(convertRupiah.ConvertRupiah(rowItem.getTarget_donasi()) );
        String imgUrl = rowItem.getImage();
        holder.terkumpul.setText(convertRupiah.ConvertRupiah(rowItem.getTerkumpul()));
        int persen = hitungPresentase.totalPresentase(rowItem.getTerkumpul(),rowItem.getTarget_donasi());
        holder.persentaseProgress.setText(String.valueOf(persen) + "%");
        holder.progressBar.setMax(100);
        holder.progressBar.setProgress(persen);
        holder.donatur.setText(rowItem.getDonatur());
        String newTanggal = rowItem.getTanggal().replace("-","/");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date1 = format.parse(newTanggal);
            Date date2 = new Date();
            long diff = date1.getTime() - date2.getTime();
            holder.sisa_hari.setText(String.valueOf(diff / (24 * 60 * 60 * 1000)));
//            tanggal.setText(String.valueOf(format.format(date1)));
        }catch (Exception e){
            e.printStackTrace();
        }
        //Picasso
        Picasso.with(context).load(imgUrl).into(holder.img);
        holder.btn_donasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GalangDanaDetailActivity_.class);
                intent.putExtra("id", rowItem.getId());
                intent.putExtra("title", rowItem.getTitle());
                intent.putExtra("desc", rowItem.getDesc());
                intent.putExtra("tanggal", rowItem.getTanggal());
                intent.putExtra("img", rowItem.getImage());
                intent.putExtra("target", rowItem.getTarget_donasi());
                intent.putExtra("terkumpul", rowItem.getTerkumpul());
                intent.putExtra("donatur", rowItem.getDonatur());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent = new Intent(v.getContext(), GalangDanaDetailActivity_.class);
                        intent.putExtra("id", rowItem.getId());
                        intent.putExtra("title", rowItem.getTitle());
                        intent.putExtra("desc", rowItem.getDesc());
                        intent.putExtra("tanggal", rowItem.getTanggal());
                        intent.putExtra("img", rowItem.getImage());
                        intent.putExtra("target", rowItem.getTarget_donasi());
                        intent.putExtra("terkumpul", rowItem.getTerkumpul());
                        intent.putExtra("donatur", rowItem.getDonatur());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        v.getContext().startActivity(intent);

            }
        });
        return convertView;
    }


}

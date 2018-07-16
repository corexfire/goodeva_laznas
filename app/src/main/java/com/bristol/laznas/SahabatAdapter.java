package com.bristol.laznas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bristol.laznas.helper.ConvertRupiah;
import com.bristol.laznas.helper.HitungPresentase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class SahabatAdapter extends
        RecyclerView.Adapter<SahabatAdapter.ViewHolder> implements Filterable {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView judul;
        public TextView batasDonasi;
        public TextView tanggal;
        public TextView target_donasi;
        public TextView targetDonasi;
        public TextView deskripsiDonasi;
        public ImageView image;
        public ImageView imgView1;
        public ImageView imgView2;
        public TextView terkumpul;
        public TextView persentaseProgress;
        public ProgressBar progressBar;
        public Button btn_input;
        public View mview;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.

            super(itemView);

            mview = itemView;
            judul = (TextView) itemView.findViewById(R.id.namaAcara);
            batasDonasi = (TextView) itemView.findViewById(R.id.waktuAcara);
            targetDonasi = (TextView) itemView.findViewById(R.id.hargaAcara);
            deskripsiDonasi = (TextView) itemView.findViewById(R.id.deskripsiAcara);
            image = (ImageView) itemView.findViewById(R.id.fotoAcara);
            imgView1 = (ImageView) itemView.findViewById(R.id.iconHarga);
            imgView2 = (ImageView) itemView.findViewById(R.id.iconWaktu);
            tanggal = (TextView) itemView.findViewById(R.id.tanggal);
            target_donasi = (TextView) itemView.findViewById(R.id.target_donasi);
            terkumpul = (TextView) itemView.findViewById(R.id.terkumpul);
            persentaseProgress = (TextView) itemView.findViewById(R.id.persentaseProgress);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            btn_input = (Button) itemView.findViewById(R.id.btn_input);


            //messageButton = (Button) itemView.findViewById(R.id.message_button);
        }
    }

    // Store a member variable for the contacts
    private ArrayList<SahabatModel> mContacts;
    private ArrayList<SahabatModel> mArrayList;
    // Store the context for easy access
    private Context mContext;
    public ConvertRupiah convertRupiah;
    public HitungPresentase hitungPresentase;

    // Pass in the contact array into the constructor
    public SahabatAdapter(Context context, ArrayList<SahabatModel> contacts) {
        mArrayList = contacts;
        mContacts = contacts;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public SahabatAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.list_acara_laznas, parent, false);
        convertRupiah = new ConvertRupiah();
        hitungPresentase = new HitungPresentase();
        // Return a new holder instance
        SahabatAdapter.ViewHolder viewHolder = new SahabatAdapter.ViewHolder(contactView);
        //final String id = getRef(position).getKey();



        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(SahabatAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final SahabatModel contact = mContacts.get(position);

        // Set item views based on your views and data model

        TextView textViewJudul = viewHolder.judul;
        textViewJudul.setText(contact.getName());
        TextView textViewBatasDonasi = viewHolder.tanggal;
        textViewBatasDonasi.setText(contact.getBatasWaktu());
        TextView textViewTargetDonasi = viewHolder.target_donasi;
        TextView textViewTerkumpul = viewHolder.terkumpul;
        Button btn_input = viewHolder.btn_input;
        if(isInteger(contact.getTekumpul())) {
            textViewTerkumpul.setText(convertRupiah.ConvertRupiah(contact.getTekumpul()));
        }
        TextView textViewPresentaseProgress = viewHolder.persentaseProgress;

        if(isInteger(contact.getTarget())) {
            int persen = hitungPresentase.totalPresentase(contact.getTekumpul(), contact.getTarget());
            textViewPresentaseProgress.setText(String.valueOf(persen) + "%" );
            ProgressBar progressBar = viewHolder.progressBar;
            progressBar.setMax(100);

            progressBar.setProgress(persen);
        }


        TextView textViewDeskripsiDonasi = viewHolder.deskripsiDonasi;
        textViewDeskripsiDonasi.setText(contact.getDeskripsi());
        if(contact.getTarget().contains(" ")){

            textViewTargetDonasi.setText("" + contact.getTarget() + "");
            ImageView imageView1 = viewHolder.imgView1;
            imageView1.setVisibility(View.INVISIBLE);
            ImageView imageView2 = viewHolder.imgView2;
            imageView2.setVisibility(View.INVISIBLE);
            btn_input.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ZakatActivityLaznas_.class);
                    intent.putExtra("nid", contact.getNID());
                    intent.putExtra("deskripsi", contact.getDeskripsi());
                    intent.putExtra("nama", contact.getName());
                    intent.putExtra("batas_waktu", contact.getBatasWaktu());
                    intent.putExtra("target", "" + contact.getTarget() + "");
                    intent.putExtra("gambar", contact.getImage());
                    intent.putExtra("terkumpul", contact.getTekumpul());
                    v.getContext().startActivity(intent);
                }
            });

            viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ZakatActivityLaznas_.class);
                    intent.putExtra("nid", contact.getNID());
                    intent.putExtra("deskripsi", contact.getDeskripsi());
                    intent.putExtra("nama", contact.getName());
                    intent.putExtra("batas_waktu", contact.getBatasWaktu());
                    intent.putExtra("target", "" + contact.getTarget() + "");
                    intent.putExtra("gambar", contact.getImage());
                    intent.putExtra("terkumpul", contact.getTekumpul());
                    v.getContext().startActivity(intent);
                }
            });
        }
        else{
//            int frmt = Integer.parseInt(contact.getTarget());
//            DecimalFormat dfmt = new DecimalFormat();
//            DecimalFormatSymbols fmts = new DecimalFormatSymbols();
//
//            fmts.setGroupingSeparator('.');
//
//            dfmt.setGroupingSize(3);
//            dfmt.setGroupingUsed(true);
//            dfmt.setDecimalFormatSymbols(fmts);
//            String out = dfmt.format(frmt);
//

            if(isInteger(contact.getTarget())) {
                textViewTargetDonasi.setText(convertRupiah.ConvertRupiah(contact.getTarget()));
            }
            btn_input.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ZakatActivityLaznas_.class);
                    intent.putExtra("nid", contact.getNID());
                    intent.putExtra("deskripsi", contact.getDeskripsi());
                    intent.putExtra("nama", contact.getName());
                    intent.putExtra("batas_waktu", contact.getBatasWaktu());
                    intent.putExtra("target", "" + contact.getTarget() + "");
                    intent.putExtra("gambar", contact.getImage());
                    intent.putExtra("terkumpul", contact.getTekumpul());
                    v.getContext().startActivity(intent);
                }
            });
            viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), AcaraActivityLaznas_.class);
                    intent.putExtra("nid", contact.getNID());
                    intent.putExtra("deskripsi", contact.getDeskripsi());
                    intent.putExtra("nama", contact.getName());
                    intent.putExtra("batas_waktu", contact.getBatasWaktu());
                    intent.putExtra("target", contact.getTarget() );
                    intent.putExtra("gambar", contact.getImage());
                    intent.putExtra("terkumpul", contact.getTekumpul());
                    v.getContext().startActivity(intent);
                }
            });
        }


        //ImageView imageViewGambar = viewHolder.image;
        //imageViewGambar.setImageResource(contact.getImage());

        String imgUrl = contact.getImage();
        //Picasso
        Picasso.with(mContext).load(imgUrl).resize(300,100).into(viewHolder.image);



        //Button button = viewHolder.messageButton;
        //button.setText("Donasi");
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mContacts = mArrayList;
                } else {

                    ArrayList<SahabatModel> filteredList = new ArrayList<>();

                    for (SahabatModel contact : mArrayList) {

                        if (contact.getName().toLowerCase().contains(charString)) {

                            filteredList.add(contact);
                        }
                    }

                    mContacts = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mContacts;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mContacts = (ArrayList<SahabatModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public boolean isInteger( String input )
    {
        try
        {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e)
        {
            return false;
        }
    }


}

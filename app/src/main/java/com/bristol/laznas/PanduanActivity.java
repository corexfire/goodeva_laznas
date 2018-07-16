package com.bristol.laznas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bristol.laznas.adapter.PanduanAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_panduan)
public class PanduanActivity extends AppCompatActivity {
    @ViewById(R.id.listview)
    ListView listView;


    PanduanAdapter panduanAdapter;
    @AfterViews
    void init() {

        final String[] title = getResources().getStringArray(R.array.panduan_header);
        final String[] desc = getResources().getStringArray(R.array.panduan_description);
        panduanAdapter = new PanduanAdapter(this,title, desc);
        listView.setAdapter(panduanAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), PanduanDetailActivity_.class);

                String tag = (String) view.getTag();
                intent.putExtra("title", title[position]);
                intent.putExtra("desc", desc[position]);
                startActivity(intent);
            }
        });
    }
}

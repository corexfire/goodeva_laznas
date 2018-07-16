package com.bristol.laznas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_panduan_detail)
public class PanduanDetailActivity extends AppCompatActivity {

    @ViewById(R.id.txt_title)
    TextView txt_title;

    @ViewById(R.id.txt_desc)
    TextView txt_desc;

    @ViewById(R.id.btn_left)
    ImageView btn_left;

    String title, desc;

    @AfterViews
    void init() {

        Intent intent = this.getIntent();
        if( getIntent().getExtras() != null){
            title = intent.getStringExtra("title");
            desc = intent.getStringExtra("desc");
        }

        txt_title.setText(title);
        txt_desc.setText(desc);
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


}

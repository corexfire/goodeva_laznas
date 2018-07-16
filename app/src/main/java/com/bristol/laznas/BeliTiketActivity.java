package com.bristol.laznas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_beli_tiket)
public class BeliTiketActivity extends AppCompatActivity {

    @AfterViews
    void init() {
//        Spinner spinner = (Spinner) findViewById(R.id.spinnerJumlahTiket);
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.jumlahTiketArray, android.R.layout.simple_spinner_item);
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioTransferATM:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }

    public void toTagihan(View view) {
        Intent intent = new Intent(this, TagihanActivity_.class);

        /*String tag =  (String) view.getTag();
        intent.putExtra("acaraUser", tag);*/
        startActivity(intent);
    }
}

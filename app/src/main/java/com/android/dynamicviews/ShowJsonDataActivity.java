package com.android.dynamicviews;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/*
 * Created by Piyush J
 */
public class ShowJsonDataActivity extends AppCompatActivity {

    /*
     * UI elements
     * */
    TextView txtHeading;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_json_data);
        context = this;
        try {
            txtHeading = findViewById(R.id.txtHeading);

            String text = Utilities.loadJSONFromAsset(context, "layout");
            txtHeading.setText("" + text);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
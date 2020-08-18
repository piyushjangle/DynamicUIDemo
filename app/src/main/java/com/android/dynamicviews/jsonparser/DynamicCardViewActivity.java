package com.android.dynamicviews.jsonparser;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.dynamicviews.R;
import com.android.dynamicviews.Utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DynamicCardViewActivity extends AppCompatActivity {

    private DataAdapter dataAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_card_view);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView = (RecyclerView) findViewById(R.id.cars_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            parseJson();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseJson() {

        try {
            String carData = Utilities.loadJSONFromAsset(this, "car_data");
            JSONArray jsonArray = new JSONArray(carData);
            Log.d("parseJson", "jsonArray :: " + jsonArray.toString());
            ArrayList<CarModel> carModels = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                CarModel carModel = new CarModel();
                carModel.setId(jsonObject.optString("id"));
                carModel.setName(jsonObject.optString("name"));
                carModel.setDesc(jsonObject.optString("desc"));

                carModels.add(carModel);
            }
            Log.d("parseJson", "carModels size :: " + carModels.size());


            dataAdapter = new DataAdapter(carModels, DynamicCardViewActivity.this);
            mRecyclerView.setAdapter(dataAdapter);
            mProgressBar.setVisibility(View.GONE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
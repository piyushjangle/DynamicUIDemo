package com.android.dynamicviews;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.flipkart.android.proteus.Proteus;
import com.flipkart.android.proteus.ProteusBuilder;
import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusLayoutInflater;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.gson.ProteusTypeAdapterFactory;
import com.flipkart.android.proteus.support.v7.CardViewModule;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;

/*
 * Created by Piyush J
 */
public class MainActivity extends AppCompatActivity {

    /*
     * UI elements and Constants
     * */
    Context context;
    private String TAG = "MainActivity";

    /*
     * Proteus Elements
     * */
    Proteus proteus;
    ProteusTypeAdapterFactory proteusTypeAdapterFactory;
    ProteusContext proteusContext;
    ProteusLayoutInflater proteusLayoutInflater;

    //json data
    //used for layout
    private static final String LAYOUT = "{\n" +
            "  \"type\": \"LinearLayout\",\n" +
            "  \"orientation\": \"vertical\",\n" +
            "  \"padding\": \"16dp\",\n" +
            "  \"children\": [{\n" +
            "    \"type\": \"TextView\",\n" +
            "    \"layout_width\": \"200dp\",\n" +
            "    \"gravity\": \"center\",\n" +
            "    \"text\": \"Dynamic View Example\"\n" +
            "  }, {\n" +
            "    \"type\": \"HorizontalProgressBar\",\n" +
            "    \"layout_width\": \"200dp\",\n" +
            "    \"layout_marginTop\": \"8dp\",\n" +
            "    \"max\": 6000,\n" +
            "    \"progress\": \"4321\"\n" +
            "  }]\n" +
            "}";

    //used for data inside layout
    private static final String DATA = "{}";
    /*end*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        try {
            long statTime = System.currentTimeMillis();
            ViewGroup container = findViewById(R.id.container);

            // create a new instance of proteus
            proteus = new ProteusBuilder()
                    .register(CardViewModule.create())
                    .build();


            // register proteus with a ProteusTypeAdapterFactory to deserialize proteus jsons
            proteusTypeAdapterFactory = new ProteusTypeAdapterFactory(this);
            ProteusTypeAdapterFactory.PROTEUS_INSTANCE_HOLDER.setProteus(proteus);

            // deserialize layout and data
            Layout layout;
            ObjectValue data;
            try {
                layout = proteusTypeAdapterFactory.LAYOUT_TYPE_ADAPTER.read(new JsonReader(new StringReader(Utilities.loadJSONFromAsset(context, "layout"))));
                data = proteusTypeAdapterFactory.OBJECT_TYPE_ADAPTER.read(new JsonReader(new StringReader(DATA)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // create a new ProteusLayoutInflater
            proteusContext = proteus.createContextBuilder(this)
                    .build();
            proteusLayoutInflater = proteusContext.getInflater();

            // Inflate the layout
            ProteusView view = proteusLayoutInflater.inflate(layout, data, container, 0);
            // ProteusView view = inflater.inflate(layout, data);   //can use this too instead above

            // remove the current view
            container.removeAllViews();

            // Add the inflated layout into the container
            container.addView(view.getAsView());

            //calculate time
            Log.d(TAG, "Time taken to load the view :: " + (System.currentTimeMillis() - statTime) + " ms");

            /*end of proteus*/

            /*
             * to show the dynamiv alert
             * */
            //showAlert();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * fun to show the Alert Dynamically
     *  */
    public void showAlert() {
        // deserialize layout and data
        Layout layout;
        ObjectValue data;
        try {
            layout = proteusTypeAdapterFactory.LAYOUT_TYPE_ADAPTER.read(new JsonReader(new StringReader(Utilities.loadJSONFromAsset(context, "layout_alert_dialog"))));
            data = proteusTypeAdapterFactory.OBJECT_TYPE_ADAPTER.read(new JsonReader(new StringReader(DATA)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ProteusView view = proteusLayoutInflater.inflate(layout, data);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view.getAsView())
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_showSource:
                startActivity(new Intent(this, ShowJsonDataActivity.class));
                // Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
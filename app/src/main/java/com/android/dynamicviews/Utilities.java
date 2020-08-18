package com.android.dynamicviews;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/*
 * Created by Piyush J
 */
public class Utilities {

    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        //  Log.d("loadJSONFromAsset", "" + json);
        return json;
    }

}

package com.noni.Orderise;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;


public class FileOperations {

    private static final String FILENAME = "orders";

    public FileOperations() {
    }


    public void writeToFile(Activity a, ArrayList<CoffeeOrder> orders, SharedPreferences mPreferences) {

        Context c = a.getApplicationContext();

        try {
            for (int i = 0; i < orders.size(); i++) {
                Gson gson = new Gson();
                String filename = String.valueOf(i);
                OutputStreamWriter osw = new OutputStreamWriter(c.openFileOutput(filename,Context.MODE_PRIVATE));
                osw.write(gson.toJson(orders.get(i)));
                osw.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        mPreferences = a.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt("orderSize", orders.size());
        editor.commit();

        readFromFile(a, mPreferences);
    }

    public void readFromFile(Activity a, SharedPreferences prefs) {
        ArrayList<CoffeeOrder> orders = new ArrayList<>();
        Gson gson = new Gson();

        prefs = a.getPreferences(Context.MODE_PRIVATE);
        int defaultValue = 5999;
        int orderSize = prefs.getInt("orderSize", defaultValue);


        if (orderSize != defaultValue) {

            for (int i = 0; i < orderSize; i++) {
                try {
                    /*
                    FileInputStream fis = new FileInputStream();
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    CoffeeOrder order = gson.fromJson(ois.readObject(), CoffeeOrder); */
                    Reader reader = new FileReader(a.getApplicationContext().getFilesDir() + "/" + String.valueOf(i));
                    CoffeeOrder order = gson.fromJson(reader, CoffeeOrder.class);

                    Log.v("SomeTag", order.getOrderName());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}

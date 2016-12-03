package com.noni.Orderise;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Reader;

public class FileOperations {

    private static final String FILENAME = "orders";

    public FileOperations() {
    }


    public void writeToFile(Activity callingActivity, ArrayListRefreshable<CoffeeOrder> orders, SharedPreferences mPreferences) {

        Context c = callingActivity.getApplicationContext();

        try {
            for (int i = 0; i < orders.size(); i++) {
                Gson gson = new Gson();
                String filename = String.valueOf(i);
                OutputStreamWriter osw = new OutputStreamWriter(c.openFileOutput(filename, Context.MODE_PRIVATE));
                osw.write(gson.toJson(orders.get(i)));
                osw.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        //mPreferences = callingActivity.getApplication().getSharedPreferences("savedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt("orderSize", orders.size());
        editor.commit();
    }

    public ArrayListRefreshable<CoffeeOrder> readFromFile(Activity callingActivity, int orderSize) {
        ArrayListRefreshable<CoffeeOrder> orders = new ArrayListRefreshable<>();
        Gson gson = new Gson();

        for (int i = 0; i < orderSize; i++) {
            try {
                Reader reader = new FileReader(callingActivity.getApplicationContext().getFilesDir() + "/" + String.valueOf(i));
                CoffeeOrder order = gson.fromJson(reader, CoffeeOrder.class);
                orders.add(order);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return orders;
    }
}

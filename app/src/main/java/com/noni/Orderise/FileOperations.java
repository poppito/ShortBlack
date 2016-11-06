package com.noni.Orderise;

import android.content.Context;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.provider.Telephony.Mms.Part.FILENAME;


public class FileOperations {

    private Context c;
    private ArrayList<CoffeeOrder> orders;

    public FileOperations(Context c, ArrayList<CoffeeOrder> orders) {
        this.c = c;
        this.orders = orders;
    }


    public void writeToFile(Context c, ArrayList<CoffeeOrder> orders) {
        try {

            for (int i=0; i<orders.size(); i++) {
                FileOutputStream fos = c.openFileOutput(String.valueOf(i), Context.MODE_PRIVATE);
                AppendOutputStream aos = new AppendOutputStream(fos);
                aos.writeObject(serializeOrders(orders.get(i)));
                aos.close();
                fos.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Map readFromFile(Context c) {
        Map mOrders = new HashMap<>();
        mOrders.put("coffeType", "");
        try {
            FileInputStream fis = new FileInputStream(c.getFilesDir() + "/" + FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            mOrders = (Map) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Iterator it = mOrders.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
        }
        return mOrders;
    }

    private class AppendOutputStream extends ObjectOutputStream {

        public AppendOutputStream(OutputStream output) throws IOException {
            super(output);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            reset();
        }


    }

    private String serializeOrders(CoffeeOrder coffeeOrder) {
        Gson gson = new Gson();
        return gson.toJson(coffeeOrder);
    }
}

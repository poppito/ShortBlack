package com.noni.ShortBlack;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class FileOperations  {

    private Context c;
    private HashMap<String, String> mOrder;
    private static final String FILENAME = "Orders";

    public FileOperations(Context c, HashMap<String, String> mOrder) {
        this.c = c;
        this.mOrder = mOrder;
        writeToFile(c, mOrder);
    }


    public void writeToFile(Context c, HashMap<String, String> mOrders) {
        try {
            FileOutputStream fos = c.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            AppendOutputStream aos = new AppendOutputStream(fos);
            aos.writeObject(mOrders);
            aos.close();
            fos.close();
        }
        catch (Exception e) {
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        Iterator it = mOrders.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Log.v("SomeTag", pair.getKey().toString() + " key" + pair.getValue().toString() + " value");
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

}

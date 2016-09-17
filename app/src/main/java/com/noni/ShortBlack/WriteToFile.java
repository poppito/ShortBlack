package com.noni.shortblack;
import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class WriteToFile  {


    public void writeToFile(Context c, ArrayList<String> mNames, ArrayList<String> fNames)
    {
        File maleNames = new File(c.getFilesDir(),"maleNames");
        File femaleNames = new File(c.getFilesDir(), "femaleNames");
        FileOutputStream os;
        String string = "hellobutts";

        try
        {
            os = c.openFileOutput("maleNames", c.MODE_PRIVATE);
            os.write(string.getBytes());
            os.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}

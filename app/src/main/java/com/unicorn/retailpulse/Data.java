package com.unicorn.retailpulse;


import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class Data {
    private static String  TAG="dataharsh";
    private  Context mContext;int x=0;
    List<String> mLines = new ArrayList<>();
    Double a[][]=new Double[32][16];
    int label[]=new int[32];
    public Data(Context context)
    {
        mContext = context;
        Log.e(TAG, "Data: enterd" );
        InputStream is = null,ip=null;
        try {
            is = mContext.getAssets().open("rps_vecs.tsv");
            ip=mContext.getAssets().open("rps_labels.tsv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(is)),reader1=new BufferedReader(new InputStreamReader(ip));

            String line,line1;




            while ((line1 = reader1.readLine()) != null) {
                label[x++]=Integer.parseInt(line1);
            }
            x=0;




            while ((line = reader.readLine()) != null) {
                String[] strs = line.trim().split("\\s+");

                for (int i = 0; i < strs.length; i++) {
                    a[x][i] = Double.parseDouble(strs[i]);
                    Log.e(TAG, "Data: "+a[x][i]);
                }
                x++;
                }

               // Log.e(TAG, "Data: " + x);
            }
        catch (IOException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Data: "+is.toString() );

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson(a);

        editor.putString("arraydata", json);
        editor.commit();
    }
   public Double[][] getArray()
   {
       return a;
   }
   public int[] getLabel(){return label;}
}

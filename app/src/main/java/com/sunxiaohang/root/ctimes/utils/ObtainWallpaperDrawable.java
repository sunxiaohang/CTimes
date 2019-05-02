package com.sunxiaohang.root.ctimes.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class ObtainWallpaperDrawable extends AsyncTask<Object,Void,Bitmap>{
    private String requestAddress;
    private ImageView imageView;
    private HashMap<String,Bitmap> cacheBitMap;
    private final float screenWidth;

    public ObtainWallpaperDrawable(String requestAddress, ImageView imageView, HashMap<String,Bitmap> cacheBitMap) {
        System.out.println("TAG:ObtainImageDrawable:"+requestAddress);
        this.requestAddress = requestAddress;
        this.imageView = imageView;
        this.cacheBitMap = cacheBitMap;
        DisplayMetrics dm = imageView.getResources().getDisplayMetrics();
        float density = dm.density;
        screenWidth = dm.widthPixels*3/density;
    }

    @Override
    protected Bitmap doInBackground(Object[] objects) {
        Bitmap bitmap = null;
        if((bitmap = cacheBitMap.get(requestAddress))!=null)return bitmap;
        URL url = null;
        try {
            url = new URL(requestAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3*1000);
            InputStream stream = connection.getInputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 6;
            bitmap = BitmapFactory.decodeStream(stream,null,options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cacheBitMap.put(requestAddress,bitmap);
        return bitmap;
    }

    @Override
    protected void onPostExecute(final Bitmap o) {
        super.onPostExecute(o);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = (int) (screenWidth/2);
        layoutParams.height = o.getHeight()*layoutParams.width/o.getWidth();
        imageView.setLayoutParams(layoutParams);
        imageView.setImageBitmap(o);
    }
}

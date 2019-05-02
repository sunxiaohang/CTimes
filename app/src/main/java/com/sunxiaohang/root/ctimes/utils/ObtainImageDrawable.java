package com.sunxiaohang.root.ctimes.utils;

import android.os.AsyncTask;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.sunxiaohang.root.ctimes.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ObtainImageDrawable extends AsyncTask<Object,Void,RoundedBitmapDrawable>{
    private String requestAddress;
    private ImageView imageView;
    private float CORNER_RADIUS = 6;

    public ObtainImageDrawable(String requestAddress, ImageView imageView) {
        System.out.println("TAG:ObtainImageDrawable:"+requestAddress);
        this.requestAddress = requestAddress;
        this.imageView = imageView;
    }

    @Override
    protected RoundedBitmapDrawable doInBackground(Object[] objects) {
        RoundedBitmapDrawable drawable = null;
        URL url = null;
        try {
            url = new URL(requestAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3*1000);
            InputStream stream = connection.getInputStream();
            drawable = RoundedBitmapDrawableFactory.create(null,stream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawable;
    }

    @Override
    protected void onPostExecute(final RoundedBitmapDrawable o) {
        super.onPostExecute(o);
        if(o!=null){
            o.setCornerRadius(CORNER_RADIUS);
            imageView.setImageDrawable(o);
        }
        else {
            imageView.setImageDrawable(imageView.getContext().getDrawable(R.mipmap.logo));
        }
    }
}

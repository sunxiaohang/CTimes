package com.sunxiaohang.root.ctimes.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadFullImageToGallery extends AsyncTask<Void,Object,Boolean> {
    private Context context;
    private View view;
    private String requestImageAddress;

    public DownloadFullImageToGallery(Context context,View view, String requestImageAddress) {
        this.context = context;
        this.view = view;
        this.requestImageAddress = requestImageAddress;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Bitmap bitmap = null;
        URL url = null;
        try {
            url = new URL(requestImageAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3*1000);
            InputStream stream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(stream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Boolean result = Utils.saveImageToGallery(context,bitmap);
        return result;
    }

    @Override
    protected void onPostExecute(Boolean o) {
        super.onPostExecute(o);
        if(o){
            Snackbar.make(view,"Image download success to gallery.",Snackbar.LENGTH_SHORT).show();
        }else {
            Snackbar.make(view,"Image download failed please try again.",Snackbar.LENGTH_SHORT).show();
        }
    }
}

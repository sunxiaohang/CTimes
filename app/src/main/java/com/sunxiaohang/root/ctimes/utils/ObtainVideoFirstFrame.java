package com.sunxiaohang.root.ctimes.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.util.HashMap;

public class ObtainVideoFirstFrame extends AsyncTask<Object,Void,Bitmap> {
    private String requestUrl;
    private ImageView imageView;

    public ObtainVideoFirstFrame(String requestUrl, ImageView imageView) {
        this.requestUrl = requestUrl;
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(Object[] objects) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(requestUrl,new HashMap<String, String>());
        Bitmap firstFrame = mmr.getFrameAtTime();
        return firstFrame;
    }

    @Override
    protected void onPostExecute(Bitmap o) {
        super.onPostExecute(o);
        imageView.setImageBitmap(o);
    }
}

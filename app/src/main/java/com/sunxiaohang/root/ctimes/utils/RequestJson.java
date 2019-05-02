package com.sunxiaohang.root.ctimes.utils;

import android.os.AsyncTask;
import com.sunxiaohang.root.ctimes.interfaces.PostExecuteUpdate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RequestJson extends AsyncTask<Object,Void,String>{
    private String requestAddress;
    private PostExecuteUpdate update;
    private int position;

    public RequestJson(String requestAddress,int position, PostExecuteUpdate update) {
        if(requestAddress == null ||requestAddress.equals("")){
            System.out.println("TAG:RequestJson error request address is null.");
        }
        System.out.println("TAG:"+requestAddress);
        this.requestAddress = requestAddress;
        this.update = update;
        this.position = position;
    }

    @Override
    protected String doInBackground(Object[] o) {
        URL url = null;
        StringBuffer buffer = new StringBuffer();
        try {
            url = new URL(requestAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5*1000);
            InputStream inputStream = connection.getInputStream();
            InputStreamReader reader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(reader);
            String temp="";
            while ((temp=bufferedReader.readLine())!=null){
                buffer.append(temp);
            }
            inputStream.close();
            reader.close();
            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    @Override
    protected void onPostExecute(String requestResult) {
        super.onPostExecute(requestResult);
        if(update != null && position != -1)update.update(requestResult,position);
    }
}

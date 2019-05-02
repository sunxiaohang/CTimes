package com.sunxiaohang.root.ctimes;

import android.support.test.runner.AndroidJUnit4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        String resource = "";
        try {
            JSONObject object = new JSONObject(resource);
            if(object.getString("message").equals("请求成功")){
                JSONArray results = object.getJSONArray("data");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject item = results.getJSONObject(i);
                    String[] imgUrls = (String[]) item.get("imgUrls");
                    for (int j = 0; j < imgUrls.length; j++) {
                        System.out.println(imgUrls[i]);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

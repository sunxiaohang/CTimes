package com.sunxiaohang.root.ctimes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.sunxiaohang.root.ctimes.utils.RequestJson;
import com.sunxiaohang.root.ctimes.utils.Utils;

public class GuidActivity extends AppCompatActivity{
    private final int HANDLER_OK = 0;
    private AutoScrollRecycleView recyclerView;
    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == HANDLER_OK){
                AutoScrollRecycleView.appStop=true;
                Intent intent = new Intent(GuidActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setAlphaStatusBar(this);
        setContentView(R.layout.activity_guid);
        //初始化缓存实例
        Utils.initialACache(this);
        recyclerView = findViewById(R.id.recycleView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 6, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new GridRecyclerViewAdapter(Utils.getSubjects(this)));
        recyclerView.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = Message.obtain();
                message.what = HANDLER_OK;
                handler.sendMessage(message);
            }
        }).start();
    }
}

package com.sunxiaohang.root.ctimes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sunxiaohang.root.ctimes.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;

public class RegisterActivity extends AppCompatActivity{
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<String> registeredSubjects;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setAlphaStatusBar(this);
        setContentView(R.layout.activity_register);
        registeredSubjects = new ArrayList<>();
        sharedPreferences = this.getSharedPreferences("subscribe",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        HashSet<String> referenceHashSet = (HashSet<String>) sharedPreferences.getStringSet("subjects",null);
        if (referenceHashSet!=null){
            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
            Bundle bundle =new Bundle();
            ArrayList<String> subjectLists = new ArrayList<>();
            String[] subjects = Utils.getSubjects(this);
            for (int i = 0; i < subjects.length; i++) {
                if (referenceHashSet.contains(subjects[i])){
                    subjectLists.add(subjects[i]);
                }
            }
            bundle.putStringArrayList("subjects",subjectLists);
            intent.putExtra("args",bundle);
            startActivity(intent);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(registeredSubjects.size()>0){
                    Intent intent =new Intent(RegisterActivity.this,MainActivity.class);
                    Bundle bundle =new Bundle();
                    bundle.putStringArrayList("subjects",registeredSubjects);
                    intent.putExtra("args",bundle);
                    editor.putStringSet("subjects",new HashSet(registeredSubjects));
                    editor.apply();
                    startActivity(intent);
                }else{
                    Snackbar.make(view, "Sorry! please check your selection.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        recyclerView = findViewById(R.id.register_recycle_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(new RegisterRecyclerViewAdapter(Utils.getSubjects(this),registeredSubjects));
    }

//    @Override
//    public boolean onKey(View v, int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP){
//            if(backPressCount==0) {
//                Snackbar.make(getWindow().getDecorView(), "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                backPressCount++;
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(2000);
//                            if(backPressCount==1)backPressCount--;
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//            }
//            if(backPressCount==1){
//                System.exit(0);
//            }
//        }
//        return false;
//    }
}

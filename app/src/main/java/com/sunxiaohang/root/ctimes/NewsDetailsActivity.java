package com.sunxiaohang.root.ctimes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsDetailsActivity extends AppCompatActivity {

    private WebView newsContentWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        newsContentWebView = findViewById(R.id.news_details_web_view);
        newsContentWebView.getSettings().setJavaScriptEnabled(true);
        newsContentWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        newsContentWebView.getSettings().setSupportMultipleWindows(true);
        newsContentWebView.setWebViewClient(new WebViewClient());
        newsContentWebView.setWebChromeClient(new WebChromeClient());
        newsContentWebView.loadUrl(getIntent().getStringExtra("newsAddress"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}

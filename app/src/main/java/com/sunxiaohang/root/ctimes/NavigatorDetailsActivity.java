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

public class NavigatorDetailsActivity extends AppCompatActivity {
    private WebView navigatorDetailsWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //webView Setting
        navigatorDetailsWebView = findViewById(R.id.navigator_details_webView);
        navigatorDetailsWebView.getSettings().setJavaScriptEnabled(true);
        navigatorDetailsWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        navigatorDetailsWebView.getSettings().setSupportMultipleWindows(true);
        navigatorDetailsWebView.setWebViewClient(new WebViewClient());
        navigatorDetailsWebView.setWebChromeClient(new WebChromeClient());
        navigatorDetailsWebView.loadUrl(getIntent().getStringExtra("requestUrl"));

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

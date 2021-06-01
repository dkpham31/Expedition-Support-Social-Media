package com.example.project3;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.widget.Toolbar;


public class Mylog extends AppCompatActivity{
    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylog);
        webview = (WebView) findViewById(R.id.webView);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("My Blog");
           super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        WebSettings webSettings = webview.getSettings();

        webSettings.setJavaScriptEnabled(true);

        webview.setWebViewClient(new WebViewClient());

        webview.loadUrl("http://www.breakingthecycle.education/blog/");

    }

}

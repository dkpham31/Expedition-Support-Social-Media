package com.example.project3.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.project3.R;


public class Fragment_Blogs extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment__blogs, container, false);
        final WebView WebView = (WebView) v.findViewById(R.id.webView);
        WebSettings webSettings = WebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        WebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        WebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        WebView.getSettings().setAppCacheEnabled(true);
        WebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);
        WebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        WebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        WebView.getSettings().setAppCacheEnabled(true);
        WebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);
        WebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                WebView.loadUrl("javascript:(function() { " +
                        "var head = document.getElementsByTagName('header')[0].style.display='none';"
                        + "head.parentNode.removeChild(head);" +
                        "})()");

            }
        });
        WebView.loadUrl("http://www.breakingthecycle.education/blog/");

        return v;
    }
}
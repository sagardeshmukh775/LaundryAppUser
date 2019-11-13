package com.smartloan.smtrick.user_laundryapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.smartloan.smtrick.user_laundryapp.R;


public class Fragment_AboutUS extends Fragment {

    WebView webView;
    private ProgressBar progressBar;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aboutus, container, false);
        webView = (WebView) view.findViewById(R.id.webview);


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);



        webView.loadUrl("https://samarfloor.com/");


        this.webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });

        return view;
    }
}

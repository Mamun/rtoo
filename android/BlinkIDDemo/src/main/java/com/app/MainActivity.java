package com.app;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.microblink.blinkid.R;

import java.io.Serializable;

public class MainActivity extends Activity {


    private Button button;
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.activity_main_webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new IJavascriptHandler(), "cpjs");
        //webView.loadUrl("http://10.0.2.2:3001/");
       // webView.loadUrl("http://192.168.178.37:3001/");
        webView.loadUrl("http://pacific-mesa-65377.herokuapp.com");


        Singleton.getInstance().setWevView(webView);

/*
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent myIntent = new Intent(MainActivity.this, com.microblink.blinkid.MainActivity.class);
                //myIntent.putExtra("key", value); //Optional parameters
                //MainActivity.this.startActivity(myIntent);

                webView.loadUrl("javascript:scanResponse();void(0)");
            }
        });*/

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }


    final class IJavascriptHandler {
        IJavascriptHandler() {
        }

        // This annotation is required in Jelly Bean and later:
        @JavascriptInterface
        public void sendToAndroid(String text) {

            Intent myIntent = new Intent(MainActivity.this, com.microblink.blinkid.MainActivity.class);
            MainActivity.this.startActivity(myIntent);


            // this is called from JS with passed value
            //          Toast t = Toast.makeText(getApplicationContext(), text, 2000);
            //         t.show();


            //       webView.loadUrl("javascript:scanResponse();void(0)");
        }
    }


}

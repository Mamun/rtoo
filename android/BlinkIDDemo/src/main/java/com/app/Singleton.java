package com.app;

import android.webkit.WebView;

/**
 * Created by mamun on 06.01.17.
 */

public class Singleton {

    private WebView webView;

    private static Singleton singleton = new Singleton( );

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private Singleton() { }

    /* Static 'instance' method */
    public static Singleton getInstance( ) {
        return singleton;
    }

    public void setWevView(WebView wevView){
        this.webView = wevView;

    }

    public WebView getWebView(){
        return webView;
    }

}

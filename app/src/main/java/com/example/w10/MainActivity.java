package com.example.w10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    WebView web;
    EditText urlbar1;
    String sURL = "file:///android_asset/index.html";
    ArrayList<String> prevURL = new ArrayList<String>();
    ArrayList<String> nextURL = new ArrayList<String>();
    //additional permissions needed for internet in manifest file

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // webview widget configuration & find
        web = findViewById(R.id.wv1);
        urlbar1 = (EditText)findViewById(R.id.urlbar1);
        // construction
        web.setWebViewClient(new WebViewClient());
        // allow js to run
        web.getSettings().setJavaScriptEnabled(true);
        // load main page
        web.loadUrl(sURL);
        //setUrlText();
    }

    public void setUrlText(){
        // shows current site address at url bar 1
        urlbar1.setText(web.getUrl());
    }

    public void executeJS(View v){
        // js
        web.evaluateJavascript("javascript:shoutOut()",null);
    }

    public void executeJS2(View v){
        // js 2
        web.evaluateJavascript("javascript:initialize()",null);
    }

    public void goButton(View v){
        // add site to arrlist of previous sites
        // made to fit maximum amount of 0-> 10 sites to manage ram total 11
        if(prevURL.size()<=10){
        prevURL.add(0,web.getUrl());
        }else{
            prevURL.remove(10);
            prevURL.add(0,web.getUrl());
        }
        //clears all forward links when leaving history pipeline
        nextURL.clear();
        // fetch text from address field
        String rawURL = urlbar1.getText().toString();

        // check & fix url format
        if(rawURL.contains("http")){
            web.loadUrl(sURL);
            // correct formatting to not add extra http
            setUrlText();
            //sets address to text field on site change
        }
        else{
            sURL = "https://"+rawURL+"/";
            // correct formatting
            web.loadUrl(sURL);
            setUrlText();
        }

    }
    public void refreshButton(View v){
        // get current url and reload it
        String s = web.getUrl();
        web.loadUrl(s);

    }
    public void goBack(View v){
        //if list empty, prevent crash
        if(prevURL.size()==0) {
            System.out.println("Back array empty");
        }else{
            // if not empty, go back and rearrange list
            nextURL.add(0, web.getUrl());
            web.loadUrl(prevURL.get(0));
            prevURL.remove(0);
            //urlbar address show
            setUrlText();
        }

    }
    public void goFwd(View v){
            // same as backButton, if list empty, prevent crash
            if(nextURL.size()==0){
                System.out.println("Forward array empty");
            }else{
                //load and rearrange.
                prevURL.add(0,web.getUrl());
                web.loadUrl(nextURL.get(0));
                nextURL.remove(0);
                // show address
                setUrlText();
            }
    }
}
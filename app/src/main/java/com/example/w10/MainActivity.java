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
    int iB = 0; //used for back iteration
    int iF = 0;//used for fwd iteration

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        web = findViewById(R.id.wv1);
        urlbar1 = (EditText)findViewById(R.id.urlbar1);
        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(sURL);
        //setUrlText();
    }

    public void setUrlText(){
        urlbar1.setText(web.getUrl());
    }

    public void executeJS(View v){
        web.evaluateJavascript("javascript:shoutOut()",null);
    }

    public void executeJS2(View v){
        web.evaluateJavascript("javascript:initialize()",null);
    }

    public void goButton(View v){
        if(prevURL.size()<=9){
        prevURL.add(0,web.getUrl());
        }else{
            prevURL.remove(9);
            prevURL.add(0,web.getUrl());
        }
        nextURL.clear(); //clears all forward links when leaving history pipe

        String rawURL = urlbar1.getText().toString();
        if(rawURL.contains("http")){
            web.loadUrl(sURL);
            // correct formatting to not add extra http
            setUrlText();
        }
        else{
            sURL = "https://"+rawURL+"/";
            // correct formatting
            web.loadUrl(sURL);
            setUrlText();
        }

    }
    public void refreshButton(View v){
        String s = web.getUrl();
        web.loadUrl(s);

    }
    public void goBack(View v){
        if(prevURL.size()==0) {
            System.out.println("Back array empty");
        }else{
            nextURL.add(0, web.getUrl());
            web.loadUrl(prevURL.get(0));
            prevURL.remove(0);
            setUrlText();
        }

    }
    public void goFwd(View v){
            if(nextURL.size()==0){
                System.out.println("Forward array empty");
            }else{
                prevURL.add(0,web.getUrl());
                web.loadUrl(nextURL.get(0));
                nextURL.remove(0);
                setUrlText();
            }
    }
}
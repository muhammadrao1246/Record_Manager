package com.example.minibrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.net.URI;
import java.util.Base64;

public class MainActivity extends AppCompatActivity {

    public int anflag = 0;
    WebView page = null;
    Button search = null;
    EditText address = null;

    RelativeLayout loaderContainer = null;

    ImageView loadTarget = null;

    //toolbar buttons
    ImageView home = null;
    ImageView reload = null;
    ImageView back = null;
    ImageView forward = null;


    //when click to start load the page
    View.OnClickListener searchClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v)
        {
            String url = "https://"+address.getText().toString();
            page.loadUrl(url);
        }
    };

    //go to home page
    View.OnClickListener navHome = new View.OnClickListener(){
        public void onClick(View v)
        {
            page.loadUrl("https://www.google.com/");
        }
    };

    //go to same page again
    View.OnClickListener navReload = new View.OnClickListener(){
        public void onClick(View v)
        {
            page.loadUrl(page.getUrl());
        }
    };

    //go to back page
    View.OnClickListener navBack = new View.OnClickListener(){
        public void onClick(View v)
        {
            if(page.canGoBack())
                page.goBack();
        }
    };

    //go to forward page
    View.OnClickListener navForward = new View.OnClickListener(){
        public void onClick(View v)
        {
            if(page.canGoForward())
                page.goForward();
        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hiding actionbar
        getSupportActionBar().hide();


        //initialize the widget references
        page = findViewById(R.id.page);
        search = findViewById(R.id.searchButton);
        address = findViewById(R.id.sbar);

        //initialize toolbar references
        home = findViewById(R.id.home);
        reload = findViewById(R.id.reload);
        back = findViewById(R.id.back);
        forward = findViewById(R.id.forward);


        //loading references
        loaderContainer = findViewById(R.id.loader);
        loadTarget = findViewById(R.id.loadingRing);

        //browser config
        WebSettings setting = page.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setLoadsImagesAutomatically(true);

        page.setWebViewClient(new WebViewClient());



        Animation load = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.icon_animation);

        page.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.d("WebView", "onProgressChanged: "+newProgress);
                if(newProgress <= 10)
                {
                    if(anflag == 0)
                    {
                        loaderContainer.setVisibility(View.VISIBLE);
                        loadTarget.startAnimation(load);
                    }
                }
                load.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        anflag = 1;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if(newProgress < 100) {
                            load.reset();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                if(newProgress == 100)
                {
                    anflag = 0;
                    loaderContainer.setVisibility(View.INVISIBLE);
                }
            }
        });

        //defaultly this page will be loaded in the webview widget
        page.loadUrl("https://www.google.com/");

        //set the listeners for widgets and toolbars
        search.setOnClickListener(searchClicked);
        home.setOnClickListener(navHome);
        reload.setOnClickListener(navReload);
        back.setOnClickListener(navBack);
        forward.setOnClickListener(navForward);
    }


}
package com.kingdew.mrchefsl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.kingdew.mrchefsl.Adapters.IngAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class PostScreen extends AppCompatActivity {
    WebView youtubeWebView;
    String imgLink;
    TextView topTitle,title,desc;
    ImageView back,image;
    RecyclerView ingRV;
    Switch lang;
    AdView mAdView;


    String Stitle;
    String Sdesc;
    String Svideo;
    String Scate;
    String Sfimg;
    String Simg;
    String ing;
    String sinhalatitle;
    String Sinhaladesc;
    String Sinhalaing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_screen);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        youtubeWebView=findViewById(R.id.webview);
        topTitle=findViewById(R.id.top_title);
        title=findViewById(R.id.title);
        desc=findViewById(R.id.desc);
        back=findViewById(R.id.back);
        image=findViewById(R.id.backImg);
        ingRV=findViewById(R.id.ingRV);
        lang=findViewById(R.id.lang);

        try {
            Stitle=getIntent().getStringExtra("title");
            Sdesc=getIntent().getStringExtra("desc");
            Svideo=getIntent().getStringExtra("video");
            Scate=getIntent().getStringExtra("cate");
            Sfimg=getIntent().getStringExtra("fimage");
            Simg=getIntent().getStringExtra("image");
            ing=getIntent().getStringExtra("ing");
            sinhalatitle=getIntent().getStringExtra("Stitle");
            Sinhaladesc=getIntent().getStringExtra("Sdesc");
            Sinhalaing=getIntent().getStringExtra("Sing");
        }catch (Exception e){
            Toast.makeText(this, "mmm", Toast.LENGTH_SHORT).show();
        }




        ArrayList<String> ings= new ArrayList<>(Arrays.asList(ing.split(",")));

        setIngredients(ings);

        topTitle.setText(Stitle);
        title.setText(Stitle);
        desc.setText(Sdesc);
        Glide.with(this).load(Simg).centerCrop().into(image);

        youtubeWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });

        WebSettings webSettings = youtubeWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        youtubeWebView.loadUrl("https://www.youtube.com/embed/" + Svideo);


        back.setOnClickListener(view -> {
            youtubeWebView.destroy();
            finish();
        });

        lang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    ArrayList<String> ings= new ArrayList<>(Arrays.asList(Sinhalaing.split(",")));
                    setIngredients(ings);

                    topTitle.setText(sinhalatitle);
                    title.setText(sinhalatitle);
                    desc.setText(Sinhaladesc);
                }else{
                    setIngredients(ings);

                    topTitle.setText(Stitle);
                    title.setText(Stitle);
                    desc.setText(Sdesc);
                }
            }
        });

    }

    private void setIngredients(ArrayList<String> ings) {
        IngAdapter adapter=new IngAdapter(this,ings);
        ingRV.setAdapter(adapter);
    }


}
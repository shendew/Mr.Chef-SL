package com.kingdew.mrchefsl.Frags;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.kingdew.mrchefsl.Adapters.CategoryAdpter;
import com.kingdew.mrchefsl.CategoryScreen;
import com.kingdew.mrchefsl.Modles.CategoryItem;
import com.kingdew.mrchefsl.Modles.Post;
import com.kingdew.mrchefsl.R;

import java.util.ArrayList;
import java.util.Locale;

public class CateFrag extends Fragment {
    Switch lang;
    ArrayList<CategoryItem> categoryItems;
    RecyclerView rec;


    public CateFrag(ArrayList<CategoryItem> categoryItems) {
        if (this.categoryItems !=null){
            if (categoryItems.isEmpty()){
                this.categoryItems=categoryItems;
            }
        }else{
            this.categoryItems=categoryItems;
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_cate_frag, container, false);
        rec=view.findViewById(R.id.cateRec);


        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        rec.setLayoutManager(new GridLayoutManager(getContext(),2));

        CategoryAdpter adpter=new CategoryAdpter(getContext(),categoryItems);
        rec.setAdapter(adpter);


        lang=view.findViewById(R.id.lang);
        lang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    setLocate("si");
                }else{
                    setLocate("en");
                }
            }
        });


        return view;
    }

    private void setLocate(String sin) {
        Resources resources=getResources();
        DisplayMetrics metrics=resources.getDisplayMetrics();
        Configuration configuration=resources.getConfiguration();
        configuration.locale=new Locale(sin);
        resources.updateConfiguration(configuration,metrics);
        onConfigurationChanged(configuration);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }
}
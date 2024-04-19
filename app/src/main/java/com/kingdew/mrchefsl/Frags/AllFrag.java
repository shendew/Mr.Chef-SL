package com.kingdew.mrchefsl.Frags;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kingdew.mrchefsl.Adapters.PostAdapter;
import com.kingdew.mrchefsl.Modles.Post;
import com.kingdew.mrchefsl.R;

import java.util.ArrayList;

public class AllFrag extends Fragment {

    PostAdapter adapter;
    ArrayList<Post> posts;

    RecyclerView home_rv;

    public AllFrag(ArrayList<Post> posts) {
        if (this.posts != null){
            if (posts.isEmpty()){
                this.posts=posts;
            }

        }else{
            this.posts=posts;
        }

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_all_frag, container, false);


        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);







        //getData();

        home_rv=view.findViewById(R.id.all_rec);
        home_rv.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter=new PostAdapter(getContext(),posts,"normal");
        home_rv.setAdapter(adapter);
        return view;
    }


}
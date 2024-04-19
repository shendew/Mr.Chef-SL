package com.kingdew.mrchefsl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kingdew.mrchefsl.Adapters.PostAdapter;
import com.kingdew.mrchefsl.Frags.AllFrag;
import com.kingdew.mrchefsl.Frags.CateFrag;
import com.kingdew.mrchefsl.Frags.HomeFrag;
import com.kingdew.mrchefsl.Frags.MeFrag;
import com.kingdew.mrchefsl.Modles.CategoryItem;
import com.kingdew.mrchefsl.Modles.Post;
import com.kingdew.mrchefsl.helpers.LoadingDialog;

import java.util.ArrayList;

import io.paperdb.Paper;

public class HomeScreen extends AppCompatActivity {

    BottomNavigationView navi_view;
    FrameLayout container;
    ArrayList<Post> posts;
    ArrayList<CategoryItem> categories;
    HomeFrag homeFrag;
    AllFrag allFrag;
    CateFrag cateFrag;
    MeFrag meFrag;
    LoadingDialog dialog;
    private InterstitialAd mInterstitialAd;
    Window window;

    ReviewManager reviewManager;
    ReviewInfo reviewInfo = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        getReviewInfo();


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        AdRequest adRequest = new AdRequest.Builder().build();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Paper.init(this);
        Paper.book().write("width",width);


        InterstitialAd.load(this,getString(R.string.welcome_inters), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        interstitialAd.show(HomeScreen.this);

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Toast.makeText(HomeScreen.this, ""+loadAdError.getMessage(), Toast.LENGTH_SHORT).show();
                        mInterstitialAd = null;
                    }
                });


        FirebaseDatabase.getInstance().getReference("Control").child("review").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean requestre=snapshot.getValue(Boolean.class);
                if (requestre){
                    startReviewFlow();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        if (mInterstitialAd != null) {
//            mInterstitialAd.show(HomeScreen.this);
//        } else {
//
//        }

        window=this.getWindow();
        dialog=new LoadingDialog(this);
        navi_view=findViewById(R.id.home_navi);
        container=findViewById(R.id.container);
        posts=new ArrayList<>();
        categories=new ArrayList<>();
        dialog.showDialog();
        getData();
        getCategories();




        navi_view.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();
                switch (id){
                    case R.id.home:
                        window.setStatusBarColor(ContextCompat.getColor(HomeScreen.this,R.color.background_white));
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFrag).commit();
                        return true;
                    case R.id.all:
                        window.setStatusBarColor(ContextCompat.getColor(HomeScreen.this,R.color.background_white));
                        allFrag=new AllFrag(posts);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,allFrag).commit();
                        return true;
                    case R.id.cate:
                        window.setStatusBarColor(ContextCompat.getColor(HomeScreen.this,R.color.background_white));
                        cateFrag=new CateFrag(categories);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,cateFrag).commit();
                        return true;
                    case R.id.me:
                        window.setStatusBarColor(ContextCompat.getColor(HomeScreen.this,R.color.base));
                        meFrag=new MeFrag();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,meFrag).commit();
                        return true;
                }
                return false;
            }
        });
    }

    private void startReviewFlow() {
        if (reviewInfo!=null){
            com.google.android.play.core.tasks.Task<Void> flow=reviewManager.launchReviewFlow(this,reviewInfo);
            flow.addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    //Toast.makeText(getApplicationContext(), "In App Rating complete", Toast.LENGTH_LONG).show();

                }
            });
        }else {
            Toast.makeText(getApplicationContext(), "In App Rating failed", Toast.LENGTH_LONG).show();
        }
    }

    private void getReviewInfo() {
        reviewManager= ReviewManagerFactory.create(this);
        Task<ReviewInfo> manager=reviewManager.requestReviewFlow();
        manager.addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            @Override
            public void onComplete(Task<ReviewInfo> task) {
                if (task.isSuccessful()) {
                    reviewInfo = task.getResult();
                } else {
                    Toast.makeText(getApplicationContext(), "In App ReviewFlow failed to start", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getCategories() {
        FirebaseDatabase.getInstance().getReference("cate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()){
                    categories.add(item.getValue(CategoryItem.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getData() {
        FirebaseDatabase.getInstance().getReference("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()){
                    posts.add(new Post(
                            item.getKey(),
                            item.child("title").getValue(String.class),
                            item.child("desc").getValue(String.class),
                            item.child("image").getValue(String.class),
                            item.child("final_image").getValue(String.class),
                            item.child("video_link").getValue(String.class),
                            item.child("category").getValue(String.class),
                            item.child("ingredients").getValue(String.class),
                            item.child("Sititle").getValue(String.class),
                            item.child("Sidesc").getValue(String.class),
                            item.child("Siingredients").getValue(String.class)
                    ));
                }
                dialog.dismissDialog();
//                window.setStatusBarColor(ContextCompat.getColor(HomeScreen.this,R.color.base));
                homeFrag=new HomeFrag(posts);
                getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFrag).commit();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
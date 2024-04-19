package com.kingdew.mrchefsl.Frags;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kingdew.mrchefsl.R;
import com.kingdew.mrchefsl.helpers.LoadingDialog;

public class MeFrag extends Fragment {
    ImageView wa,fb,yt,gmail,propic;
    TextView no,name,address;

    String spn,sfb,syt,sgm,sadd;
    LoadingDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_me_frag, container, false);
        wa=view.findViewById(R.id.wA);
        fb=view.findViewById(R.id.fb);
        yt=view.findViewById(R.id.yt);
        gmail=view.findViewById(R.id.gmail);
        no=view.findViewById(R.id.txt_phone);
        propic=view.findViewById(R.id.propic);
        name=view.findViewById(R.id.txt_name);
        address=view.findViewById(R.id.txt_address);
        dialog=new LoadingDialog(getContext());
        dialog.showDialog();

        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        FirebaseDatabase.getInstance().getReference("contact").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                spn=snapshot.child("phone").getValue(String.class);
                sfb=snapshot.child("fb").getValue(String.class);
                syt=snapshot.child("yt").getValue(String.class);
                sgm=snapshot.child("gmail").getValue(String.class);
                sadd=snapshot.child("address").getValue(String.class);

                Glide.with(getContext()).load(snapshot.child("propic").getValue(String.class)).centerCrop().circleCrop().into(propic);
                no.setText(spn);
                name.setText("Dinuka Sadun");
                address.setText(sadd);
                dialog.dismissDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        wa.setOnClickListener(view1 -> {

            getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https:\\wa.me/"+spn)));
        });
        fb.setOnClickListener(view1 -> {
            getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sfb)));
        });
        yt.setOnClickListener(view1 -> {
            getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(syt)));
        });
        gmail.setOnClickListener(view1 -> {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/html");
            intent.putExtra(Intent.EXTRA_EMAIL,new String[]{sgm});
            intent.putExtra(Intent.EXTRA_SUBJECT,"Mr.Chef SL Contact");
            startActivity(Intent.createChooser(intent,"send a mail"));
        });

        return view;
    }
}
package com.kingdew.mrchefsl.Frags;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kingdew.mrchefsl.Adapters.PostAdapter;
import com.kingdew.mrchefsl.Modles.Post;
import com.kingdew.mrchefsl.R;

import java.util.ArrayList;

public class HomeFrag extends Fragment {
    PostAdapter adapter;
    ArrayList<Post> posts;
    ImageView pro;
    TextView bio;


    public HomeFrag(ArrayList<Post> posts) {
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
        View view=inflater.inflate(R.layout.activity_home_frag, container, false);



        RecyclerView home_rv=view.findViewById(R.id.home_rv);
        pro=view.findViewById(R.id.pro);
        bio=view.findViewById(R.id.view_bio);



//        home_rv.setLayoutManager(new GridLayoutManager(getContext(),2));

        home_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new PostAdapter(getContext(),posts,"home");
        home_rv.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Glide.with(getContext()).load(snapshot.child("contact").child("propic").getValue(String.class)).centerCrop().circleCrop().into(pro);
                bio.setText(snapshot.child("homeQuote").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

}
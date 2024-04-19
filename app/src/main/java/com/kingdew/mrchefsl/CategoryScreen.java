package com.kingdew.mrchefsl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kingdew.mrchefsl.Adapters.PostAdapter;
import com.kingdew.mrchefsl.Frags.HomeFrag;
import com.kingdew.mrchefsl.Modles.Post;
import com.kingdew.mrchefsl.helpers.LoadingDialog;

import java.util.ArrayList;

public class CategoryScreen extends AppCompatActivity {

    TextView title;
    ArrayList<Post> posts;
    String id ,name;
    LoadingDialog dialog;
    RecyclerView cateRec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_screen);
        title=findViewById(R.id.title);
        cateRec=findViewById(R.id.cateRec);

        cateRec.setLayoutManager(new GridLayoutManager(this,2));

        posts=new ArrayList<>();
        dialog=new LoadingDialog(this);
        id=getIntent().getStringExtra("id");
        name=getIntent().getStringExtra("name");
        title.setText(name);

        getData();




    }

    private void getData() {
        dialog.showDialog();
        FirebaseDatabase.getInstance().getReference("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()){

                    if (item.child("category").getValue(String.class).equals(id)){
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

                }
                dialog.dismissDialog();
                PostAdapter adapter=new PostAdapter(CategoryScreen.this,posts,"normal");
                cateRec.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
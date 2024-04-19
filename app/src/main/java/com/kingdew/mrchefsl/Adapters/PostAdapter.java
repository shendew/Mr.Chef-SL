package com.kingdew.mrchefsl.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kingdew.mrchefsl.Modles.Post;
import com.kingdew.mrchefsl.PostScreen;
import com.kingdew.mrchefsl.R;

import java.util.ArrayList;

import io.paperdb.Paper;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Context context;
    ArrayList<Post> posts;
    String mode;


    public PostAdapter(Context context, ArrayList<Post> posts,String mode) {
        this.context = context;
        this.posts = posts;
        this.mode=mode;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mode.equals("home")){
            View v= LayoutInflater.from(context).inflate(R.layout.home_item,parent,false);
            return new ViewHolder(v);
        }else {
            View v= LayoutInflater.from(context).inflate(R.layout.reciep,parent,false);
            return new ViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {

        Post post= posts.get(position);
        Paper.init(context);
        if (!mode.equals("home")){
            int width= Paper.book().read("width");
            holder.itemView.getLayoutParams().width=width/2;
        }


        holder.post_title.setText(post.getTitle());
        holder.post_body.setText(post.getDesc());
        Glide.with(context).load(post.getImage()).fitCenter().into(holder.post_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PostScreen.class);
                intent.putExtra("title",post.getTitle());
                intent.putExtra("image",post.getImage());
                intent.putExtra("desc",post.getDesc());
                intent.putExtra("fimage",post.getFinal_image());
                intent.putExtra("video",post.getVideo_link());
                intent.putExtra("cate",post.getCategory());
                intent.putExtra("ing",post.getIngredients());
                intent.putExtra("Stitle",post.getSititle());
                intent.putExtra("Sdesc",post.getSidesc());
                intent.putExtra("Sing",post.getSiingredients());

                context.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView post_image;
        TextView post_title,post_body;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            post_image=itemView.findViewById(R.id.post_image);
            post_title=itemView.findViewById(R.id.post_title);
            post_body=itemView.findViewById(R.id.post_body);

        }
    }
}

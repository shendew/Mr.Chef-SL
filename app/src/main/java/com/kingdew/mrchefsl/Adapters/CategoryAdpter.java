package com.kingdew.mrchefsl.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kingdew.mrchefsl.CategoryScreen;
import com.kingdew.mrchefsl.Modles.CategoryItem;
import com.kingdew.mrchefsl.R;

import java.util.ArrayList;

public class CategoryAdpter extends RecyclerView.Adapter<CategoryAdpter.ViewHolder> {

    Context context;
    ArrayList<CategoryItem> items;


    public CategoryAdpter(Context context, ArrayList<CategoryItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CategoryItem item= items.get(position);

        Glide.with(context).load(item.getImage()).into(holder.image);

        holder.title.setText(item.getName());

        holder.itemView.setOnClickListener(view -> {
            context.startActivity(new Intent(context, CategoryScreen.class).putExtra("id",item.getId()).putExtra("name",item.getName()));
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.image);
            title=itemView.findViewById(R.id.title);
        }
    }
}

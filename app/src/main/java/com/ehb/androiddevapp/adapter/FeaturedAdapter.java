package com.ehb.androiddevapp.adapter;

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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ehb.androiddevapp.DetailActivity;
import com.ehb.androiddevapp.R;
import com.ehb.androiddevapp.domain.Featured;

import java.util.List;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.ViewHolder> {
    Context context;
    List<Featured> myFeaturedList;


    public FeaturedAdapter(Context context, List<Featured> myFeaturedList) {
        this.context = context;
        this.myFeaturedList = myFeaturedList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_featured_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.myFetCost.setText(myFeaturedList.get(position).getPrice()+" €");
        holder.myFetName.setText(myFeaturedList.get(position).getName());
        Glide.with(context).load(myFeaturedList.get(position).getImg_url()).into(holder.myFetImage);
        holder.myFetImage.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("detail", myFeaturedList.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return myFeaturedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView myFetImage;
        TextView myFetCost;
        TextView myFetName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myFetImage= itemView.findViewById(R.id.f_img);
            myFetCost =itemView.findViewById(R.id.f_cost);
            myFetName =itemView.findViewById(R.id.f_name);
        }
    }

}

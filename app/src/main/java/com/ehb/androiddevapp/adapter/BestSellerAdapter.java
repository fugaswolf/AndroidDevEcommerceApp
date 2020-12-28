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
import com.ehb.androiddevapp.DetailActivity;
import com.ehb.androiddevapp.R;
import com.ehb.androiddevapp.domain.BestSeller;

import java.util.List;

public class BestSellerAdapter extends RecyclerView.Adapter<BestSellerAdapter.ViewHolder> {
    Context context;
    List<BestSeller> myBestSellerList;
    public BestSellerAdapter(Context context, List<BestSeller> myBestSellerList) {
        this.context=context;
        this.myBestSellerList=myBestSellerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_bestseller_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.myName.setText(myBestSellerList.get(position).getName());
        holder.myPrice.setText(myBestSellerList.get(position).getPrice()+" €");
        Glide.with(context).load(myBestSellerList.get(position).getImg_url()).into(holder.myImage);

        holder.myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("detail",myBestSellerList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myBestSellerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView myImage;
        TextView myName;
        TextView myPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myImage=itemView.findViewById(R.id.bs_img);
            myName=itemView.findViewById(R.id.bs_name);
            myPrice=itemView.findViewById(R.id.bs_cost);
        }
    }
}

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
import com.ehb.androiddevapp.HomeActivity;
import com.ehb.androiddevapp.R;
import com.ehb.androiddevapp.domain.Items;


import java.util.List;


public class ItemsRecyclerAdapter extends RecyclerView.Adapter<ItemsRecyclerAdapter.ViewHolder> {
    Context applicationContext;
    List<Items> myItemsList;
    public ItemsRecyclerAdapter(Context applicationContext, List<Items> myItemsList) {
        this.applicationContext = applicationContext;
        this.myItemsList = myItemsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(applicationContext).inflate(R.layout.single_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.myCost.setText("€ " + myItemsList.get(position).getPrice());
        holder.myName.setText(myItemsList.get(position).getName());
        if(!(applicationContext instanceof HomeActivity)){
            Glide.with(applicationContext).load(myItemsList.get(position).getImg_url()).onlyRetrieveFromCache(true).into(holder.myItemImage);

        }else
        {
            holder.myItemImage.setVisibility(View.GONE);
        }

        holder.myItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(applicationContext, DetailActivity.class);
                intent.putExtra("detail",myItemsList.get(position));
                applicationContext.startActivity(intent);
            }
        });
        holder.myName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(applicationContext, DetailActivity.class);
                intent.putExtra("detail",myItemsList.get(position));
                applicationContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myItemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView myItemImage;
        private TextView myCost;
        private TextView myName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myItemImage = itemView.findViewById(R.id.item_image);
            myCost = itemView.findViewById(R.id.item_cost);
            myName = itemView.findViewById(R.id.item_nam);
        }
    }
}

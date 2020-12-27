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
import com.ehb.androiddevapp.domain.Feature;

import java.util.List;

public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.ViewHolder> {
    Context context;
    List<Feature> myFeatureList;
    public FeatureAdapter(Context context, List<Feature> myFeatureList) {
        this.context=context;
        this.myFeatureList=myFeatureList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_feature_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.myFetCost.setText(myFeatureList.get(position).getPrice()+" $");
        holder.myFetName.setText(myFeatureList.get(position).getName());
        Glide.with(context).load(myFeatureList.get(position).getImg_url()).into(holder.myFetImage);
        holder.myFetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("detail",myFeatureList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myFeatureList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView myFetImage;
        TextView myFetCost;
        TextView myFetName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myFetImage=itemView.findViewById(R.id.f_img);
            myFetCost=itemView.findViewById(R.id.f_cost);
            myFetName=itemView.findViewById(R.id.f_name);
        }
    }

}

package com.ehb.androiddevapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import com.ehb.androiddevapp.ItemsActivity;
import com.ehb.androiddevapp.R;
import com.ehb.androiddevapp.domain.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context context;
    private List<Category> myCategoryList;

    public CategoryAdapter(Context context, List<Category> myCategoryList) {
        this.context = context;
        this.myCategoryList = myCategoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Glide.with(context).load(myCategoryList.get(position).getImg_url()).into(holder.myTypeImg);

        holder.myTypeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemsActivity.class);
                intent.putExtra("type", myCategoryList.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView myTypeImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myTypeImg = itemView.findViewById(R.id.category_img);
        }
    }
}

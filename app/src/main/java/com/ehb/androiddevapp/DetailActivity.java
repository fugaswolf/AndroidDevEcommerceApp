package com.ehb.androiddevapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ehb.androiddevapp.domain.BestSeller;
import com.ehb.androiddevapp.domain.Featured;
import com.ehb.androiddevapp.domain.Items;

public class DetailActivity extends AppCompatActivity {
    private ImageView myImage;
    private TextView myItemName;
    private TextView myPrice;
    private Button myItemRating;
    private TextView myItemRatDesc;
    private TextView myItemDesc;
    private Button myAddToCart;
    private Button myBuyBtn;
    Featured featured = null;
    BestSeller bestSeller = null;
    Items items=null;
    private Toolbar myToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        myToolbar=findViewById(R.id.detail_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myImage=findViewById(R.id.item_img);
        myItemName=findViewById(R.id.item_name);
        myPrice=findViewById(R.id.item_price);
        myItemRating=findViewById(R.id.item_rating);
        myItemRatDesc=findViewById(R.id.item_rat_des);
        myItemDesc=findViewById(R.id.item_des);
        myAddToCart=findViewById(R.id.item_add_cart);
        myBuyBtn=findViewById(R.id.item_buy_now);
        final Object obj=  getIntent().getSerializableExtra("detail");
        if(obj instanceof Featured){
            featured = (Featured) obj;
        }else if(obj instanceof BestSeller){
            bestSeller = (BestSeller) obj;
        }
        else if(obj instanceof Items){
            items= (Items) obj;
        }
        if(featured !=null){
            Glide.with(getApplicationContext()).load(featured.getImg_url()).into(myImage);
            myItemName.setText(featured.getName());
            myPrice.setText(featured.getPrice()+"€");
            myItemRating.setText(featured.getRating()+"");
            if(featured.getRating()>3){
                myItemRatDesc.setText("Very Good");
            }else{
                myItemRatDesc.setText("Good");
            }
            myItemDesc.setText(featured.getDescription());
        }
        if(bestSeller !=null){
            Glide.with(getApplicationContext()).load(bestSeller.getImg_url()).into(myImage);
            myItemName.setText(bestSeller.getName());
            myPrice.setText(bestSeller.getPrice()+"€");
            myItemRating.setText(bestSeller.getRating()+"");
            if(bestSeller.getRating()>3){
                myItemRatDesc.setText("Very Good");
            }else{
                myItemRatDesc.setText("Good");
            }
            myItemDesc.setText(bestSeller.getDescription());
        }
        if(items!=null){
            Glide.with(getApplicationContext()).load(items.getImg_url()).into(myImage);
            myItemName.setText(items.getName());
            myPrice.setText(items.getPrice()+"€");
            myItemRating.setText(items.getRating()+"");
            if(items.getRating()>3){
                myItemRatDesc.setText("Very Good");
            }else{
                myItemRatDesc.setText("Good");
            }
            myItemDesc.setText(items.getDescription());
        }



        myAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        myBuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailActivity.this,AddressActivity.class);
                if(featured!=null){
                    intent.putExtra("item", featured);
                }
                if(bestSeller!=null){
                    intent.putExtra("item", bestSeller);
                }
                if(items!=null){
                    intent.putExtra("item", items);
                }

                startActivity(intent);
            }
        });


    }
}

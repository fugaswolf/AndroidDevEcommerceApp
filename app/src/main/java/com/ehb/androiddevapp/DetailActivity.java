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
import com.ehb.androiddevapp.domain.BestSell;
import com.ehb.androiddevapp.domain.Feature;
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
    Feature feature = null;
    BestSell bestSell = null;
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
        if(obj instanceof Feature){
            feature= (Feature) obj;
        }else if(obj instanceof BestSell){
            bestSell= (BestSell) obj;
        }
        else if(obj instanceof Items){
            items= (Items) obj;
        }
        if(feature!=null){
            Glide.with(getApplicationContext()).load(feature.getImg_url()).into(myImage);
            myItemName.setText(feature.getName());
            myPrice.setText(feature.getPrice()+"$");
            myItemRating.setText(feature.getRating()+"");
            if(feature.getRating()>3){
                myItemRatDesc.setText("Very Good");
            }else{
                myItemRatDesc.setText("Good");
            }
            myItemDesc.setText(feature.getDescription());
        }
        if(bestSell!=null){
            Glide.with(getApplicationContext()).load(bestSell.getImg_url()).into(myImage);
            myItemName.setText(bestSell.getName());
            myPrice.setText(bestSell.getPrice()+"$");
            myItemRating.setText(bestSell.getRating()+"");
            if(bestSell.getRating()>3){
                myItemRatDesc.setText("Very Good");
            }else{
                myItemRatDesc.setText("Good");
            }
            myItemDesc.setText(bestSell.getDescription());
        }
        if(items!=null){
            Glide.with(getApplicationContext()).load(items.getImg_url()).into(myImage);
            myItemName.setText(items.getName());
            myPrice.setText(items.getPrice()+"$");
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



    }
}

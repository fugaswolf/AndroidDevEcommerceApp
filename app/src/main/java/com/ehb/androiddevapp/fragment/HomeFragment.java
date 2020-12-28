package com.ehb.androiddevapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ehb.androiddevapp.ItemsActivity;
import com.ehb.androiddevapp.R;
import com.ehb.androiddevapp.adapter.BestSellerAdapter;
import com.ehb.androiddevapp.adapter.CategoryAdapter;
import com.ehb.androiddevapp.adapter.FeaturedAdapter;
import com.ehb.androiddevapp.domain.BestSeller;
import com.ehb.androiddevapp.domain.Category;
import com.ehb.androiddevapp.domain.Featured;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private FirebaseFirestore myStore;
    //Category Tab
    private List<Category> myCategoryList;
    private CategoryAdapter myCategoryAdapter;
    private RecyclerView myCatRecyclerView;
    //Feature Tab
    private List<Featured> myFeaturedList;
    private FeaturedAdapter myFeaturedAdapter;
    private RecyclerView myFeatureRecyclerView;
    //BestSell Tab
    private List<BestSeller> myBestSellerList;
    private BestSellerAdapter myBestSellerAdapter;
    private RecyclerView myBestSellRecyclerView;
    private TextView mySeeAll;
    private TextView myFeatured;
    private TextView myBestSeller;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        myStore = FirebaseFirestore.getInstance();
        mySeeAll = view.findViewById(R.id.see_all);
        myFeatured = view.findViewById(R.id.featured_see_all);
        myBestSeller = view.findViewById(R.id.best_seller);
        myCatRecyclerView = view.findViewById(R.id.category_recycler);
        myFeatureRecyclerView = view.findViewById(R.id.featured_recycler);
        myBestSellRecyclerView = view.findViewById(R.id.bestseller_recycler);
        //For Category
        myCategoryList = new ArrayList<>();
        myCategoryAdapter = new CategoryAdapter(getContext(), myCategoryList);
        myCatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        myCatRecyclerView.setAdapter(myCategoryAdapter);

        //For Featured
        myFeaturedList = new ArrayList<>();
        myFeaturedAdapter = new FeaturedAdapter(getContext(), myFeaturedList);
        myFeatureRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        myFeatureRecyclerView.setAdapter(myFeaturedAdapter);
        //For BestSeller
        myBestSellerList = new ArrayList<>();
        myBestSellerAdapter = new BestSellerAdapter(getContext(), myBestSellerList);
        myBestSellRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        myBestSellRecyclerView.setAdapter(myBestSellerAdapter);


        myStore.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Category category = document.toObject(Category.class);
                                myCategoryList.add(category);
                                myCategoryAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w("TAG", "Error getting categories.", task.getException());
                        }
                    }
                });

        myStore.collection("Featured")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Featured featured = document.toObject(Featured.class);
                                myFeaturedList.add(featured);
                                myFeaturedAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w("TAG", "Error getting featured products.", task.getException());
                        }
                    }
                });


        myStore.collection("BestSeller")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                BestSeller bestSeller = document.toObject(BestSeller.class);
                                myBestSellerList.add(bestSeller);
                                myBestSellerAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w("TAG", "Error getting best sellers.", task.getException());
                        }
                    }
                });
        mySeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ItemsActivity.class);
                startActivity(intent);
            }
        });
        myBestSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ItemsActivity.class);
                startActivity(intent);
            }
        });
        myFeatured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ItemsActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }
}
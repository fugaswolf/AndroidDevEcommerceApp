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
import com.ehb.androiddevapp.adapter.BestSellAdapter;
import com.ehb.androiddevapp.adapter.CategoryAdapter;
import com.ehb.androiddevapp.adapter.FeatureAdapter;
import com.ehb.androiddevapp.domain.BestSell;
import com.ehb.androiddevapp.domain.Category;
import com.ehb.androiddevapp.domain.Feature;
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
    private List<Feature> myFeatureList;
    private FeatureAdapter myFeatureAdapter;
    private RecyclerView myFeatureRecyclerView;
    //BestSell Tab
    private List<BestSell> myBestSellList;
    private BestSellAdapter myBestSellAdapter;
    private RecyclerView myBestSellRecyclerView;
    private TextView mySeeAll;
    private TextView myFeature;
    private TextView myBestSell;

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
        myFeature = view.findViewById(R.id.fea_see_all);
        myBestSell = view.findViewById(R.id.best_sell);
        myCatRecyclerView = view.findViewById(R.id.category_recycler);
        myFeatureRecyclerView = view.findViewById(R.id.feature_recycler);
        myBestSellRecyclerView = view.findViewById(R.id.bestsell_recycler);
        //For Category
        myCategoryList = new ArrayList<>();
        myCategoryAdapter = new CategoryAdapter(getContext(), myCategoryList);
        myCatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        myCatRecyclerView.setAdapter(myCategoryAdapter);

        //For Feature
        myFeatureList = new ArrayList<>();
        myFeatureAdapter = new FeatureAdapter(getContext(), myFeatureList);
        myFeatureRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        myFeatureRecyclerView.setAdapter(myFeatureAdapter);
        //For BestSell
        myBestSellList = new ArrayList<>();
        myBestSellAdapter = new BestSellAdapter(getContext(), myBestSellList);
        myBestSellRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        myBestSellRecyclerView.setAdapter(myBestSellAdapter);


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
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });

        myStore.collection("Feature")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Feature feature = document.toObject(Feature.class);
                                myFeatureList.add(feature);
                                myFeatureAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });


        myStore.collection("BestSell")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                BestSell bestSell = document.toObject(BestSell.class);
                                myBestSellList.add(bestSell);
                                myBestSellAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
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
        myBestSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ItemsActivity.class);
                startActivity(intent);
            }
        });
        myFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ItemsActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }
}
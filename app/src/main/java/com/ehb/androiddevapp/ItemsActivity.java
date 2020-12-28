package com.ehb.androiddevapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.ehb.androiddevapp.adapter.ItemsRecyclerAdapter;
import com.ehb.androiddevapp.domain.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ItemsActivity extends AppCompatActivity {
    private FirebaseFirestore myStore;
    List<Items> myItemsList;
    private RecyclerView itemRecyclerView;
    private ItemsRecyclerAdapter itemsRecyclerAdapter;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        String type=getIntent().getStringExtra("type");
        myStore=FirebaseFirestore.getInstance();
        myItemsList=new ArrayList<>();
        myToolbar= findViewById(R.id.item_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Items");

        itemRecyclerView=findViewById(R.id.items_recycler);
        itemRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        itemsRecyclerAdapter = new ItemsRecyclerAdapter(this,myItemsList);
        itemRecyclerView.setAdapter(itemsRecyclerAdapter);
        if(type==null || type.isEmpty()){
            myStore.collection("All").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc:task.getResult().getDocuments()){
                            Log.i("TAG", "onComplete: "+doc.toString());
                            Items items=doc.toObject(Items.class);
                            myItemsList.add(items);
                            itemsRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        if(type!=null && type.equalsIgnoreCase("Gloves")){
            myStore.collection("All").whereEqualTo("type","Gloves").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc:task.getResult().getDocuments()){
                            Log.i("TAG", "onComplete: "+doc.toString());
                            Items items=doc.toObject(Items.class);
                            myItemsList.add(items);
                            itemsRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        if(type!=null && type.equalsIgnoreCase("Shorts")){
            myStore.collection("All").whereEqualTo("type","Shorts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc:task.getResult().getDocuments()){
                            Log.i("TAG", "onComplete: "+doc.toString());
                            Items items=doc.toObject(Items.class);
                            myItemsList.add(items);
                            itemsRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        if(type!=null && type.equalsIgnoreCase("Tops")){
            myStore.collection("All").whereEqualTo("type","Tops").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc:task.getResult().getDocuments()){
                            Log.i("TAG", "onComplete: "+doc.toString());
                            Items items=doc.toObject(Items.class);
                            myItemsList.add(items);
                            itemsRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem item= menu.findItem(R.id.search_it);
        SearchView searchView= (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchItem(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchItem(String newText) {
        myItemsList.clear();
        myStore.collection("All").whereGreaterThanOrEqualTo("name",newText).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot doc:task.getResult().getDocuments()){
                        Log.i("TAG", "onComplete: "+doc.toString());
                        Items items=doc.toObject(Items.class);
                        myItemsList.add(items);
                        itemsRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}

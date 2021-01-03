package com.ehb.androiddevapp;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ehb.androiddevapp.database.DatabaseHelper;
import com.ehb.androiddevapp.adapter.ItemsRecyclerAdapter;
import com.ehb.androiddevapp.domain.Items;
import com.ehb.androiddevapp.domain.Utils;
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
    //local db
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        String type=getIntent().getStringExtra("type");
        myStore = FirebaseFirestore.getInstance();
        myItemsList = new ArrayList<>();
        myToolbar = findViewById(R.id.item_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Items");
        //local db
        databaseHelper = new DatabaseHelper(this);

        itemRecyclerView=findViewById(R.id.items_recycler);
        itemRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        itemsRecyclerAdapter=new ItemsRecyclerAdapter(this,myItemsList);
        itemRecyclerView.setAdapter(itemsRecyclerAdapter);
        if(type==null || type.isEmpty()){
            //check internet availability
            if(Utils.isInternetConnected) {
                myStore.collection("All").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                Log.i("TAG", "onComplete: " + doc.toString());
                                Items items = doc.toObject(Items.class);
                                myItemsList.add(items);
                                itemsRecyclerAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }else {
                //in case internet not available all data will come from local d
                ArrayList<Items> itemArrayList = new ArrayList<Items>();
                itemArrayList = databaseHelper.getAllItems();
                if(!itemArrayList.isEmpty()){
                    for (int i=0;i<itemArrayList.size();i++){
                        myItemsList.add(itemArrayList.get(i));
                        itemsRecyclerAdapter.notifyDataSetChanged();

                    }
                }
            }
        }
        if(type!=null && type.equalsIgnoreCase("Gloves")){
            //internet status checker
            if(Utils.isInternetConnected) {
                myStore.collection("All").whereEqualTo("type", "Gloves").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                Log.i("TAG", "onComplete: " + doc.toString());
                                Items items = doc.toObject(Items.class);
                                myItemsList.add(items);
                                itemsRecyclerAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }else {
                //offline getting database
                ArrayList<Items> itemArrayList = new ArrayList<Items>();

                itemArrayList = databaseHelper.getAllItems();
                if(!itemArrayList.isEmpty()){
                    for (int i=0;i<itemArrayList.size();i++){
                        Log.d("itemtester",itemArrayList.get(i).getType());
                        if(itemArrayList.get(i).getType().toLowerCase().equals(("Gloves").toLowerCase())) {
                            myItemsList.add(itemArrayList.get(i));
                            itemsRecyclerAdapter.notifyDataSetChanged();
                        }

                    }
                }
            }
        }
        if(type!=null && type.equalsIgnoreCase("Shorts")){
            //online status
            if(Utils.isInternetConnected) {
                myStore.collection("All").whereEqualTo("type", "Shorts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                Log.i("TAG", "onComplete: " + doc.toString());
                                Items items = doc.toObject(Items.class);
                                myItemsList.add(items);
                                itemsRecyclerAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }else {
                //getting local database
                ArrayList<Items> itemArrayList = new ArrayList<Items>();

                itemArrayList = databaseHelper.getAllItems();
                if(!itemArrayList.isEmpty()){
                    for (int i=0;i<itemArrayList.size();i++){
                        if(itemArrayList.get(i).getType().toLowerCase().equals(("Shorts").toLowerCase())) {
                            myItemsList.add(itemArrayList.get(i));
                            itemsRecyclerAdapter.notifyDataSetChanged();
                        }

                    }
                }
            }
        }
        if(type!=null && type.equalsIgnoreCase("Tops")){
            //internet .
            if(Utils.isInternetConnected) {
                myStore.collection("All").whereEqualTo("type", "Tops").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                Log.i("TAG", "onComplete: " + doc.toString());
                                Items items = doc.toObject(Items.class);
                                myItemsList.add(items);
                                itemsRecyclerAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }
            else {
                ArrayList<Items> itemArrayList = new ArrayList<Items>();

                itemArrayList = databaseHelper.getAllItems();
                if(!itemArrayList.isEmpty()){
                    for (int i=0;i<itemArrayList.size();i++){
                        if(itemArrayList.get(i).getType().toLowerCase().equals(("Tops").toLowerCase())) {
                            myItemsList.add(itemArrayList.get(i));
                            itemsRecyclerAdapter.notifyDataSetChanged();
                        }

                    }
                }
            }
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

                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if(query.isEmpty()){
                    itemsRecyclerAdapter = new ItemsRecyclerAdapter(ItemsActivity.this,myItemsList);
                    itemRecyclerView.setAdapter(itemsRecyclerAdapter);
                }else {
                    searchItem(query);
                }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchItem(String newText) {
        if(!newText.isEmpty()) {
            ArrayList<Items> tempArrayList = new ArrayList<>();

            //search internet status checker
            if(Utils.isInternetConnected) {
                myStore.collection("All").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                Log.i("TAG", "onComplete: " + doc.toString());
                                Items items = doc.toObject(Items.class);
                                if (items.getName().toLowerCase().contains(newText)) {
                                    tempArrayList.add(items);
                                    itemsRecyclerAdapter = new ItemsRecyclerAdapter(ItemsActivity.this, tempArrayList);
                                    itemRecyclerView.setAdapter(itemsRecyclerAdapter);
                                }

                            }
                        }
                    }
                });
            }else {
                //if internet not available then searching done from local db
                ArrayList<Items> itemArrayList = new ArrayList<Items>();

                itemArrayList = databaseHelper.getAllItems();
                if(!itemArrayList.isEmpty() && !newText.isEmpty()){
                    for (int i=0;i<itemArrayList.size();i++){
                        if(itemArrayList.get(i).getName().toLowerCase().contains(newText.toLowerCase())){
                            tempArrayList.add(itemArrayList.get(i));
                            itemsRecyclerAdapter = new ItemsRecyclerAdapter(ItemsActivity.this, tempArrayList);
                            itemRecyclerView.setAdapter(itemsRecyclerAdapter);
                        }
                    }
                }
            }
        }
    }
}

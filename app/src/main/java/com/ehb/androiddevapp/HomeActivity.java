package com.ehb.androiddevapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.ehb.androiddevapp.database.DatabaseHelper;
import com.ehb.androiddevapp.adapter.ItemsRecyclerAdapter;
import com.ehb.androiddevapp.domain.Address;
import com.ehb.androiddevapp.domain.Items;
import com.ehb.androiddevapp.domain.User;
import com.ehb.androiddevapp.domain.Utils;
import com.ehb.androiddevapp.fragment.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {
    Fragment homeFragment;
    private Toolbar myToolbar;
    private FirebaseAuth myAuth;
    private EditText mySearchText;
    private FirebaseFirestore myStore;
    private List<Items> myItemsList;
    private RecyclerView myItemRecyclerView;
    private ItemsRecyclerAdapter itemsRecyclerAdapter;
    //database helper
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeFragment=new HomeFragment();
        loadFragment(homeFragment);
        myAuth= FirebaseAuth.getInstance();
        myToolbar=findViewById(R.id.home_toolbar);
        setSupportActionBar(myToolbar);
        mySearchText=findViewById(R.id.search_text);
        myStore= FirebaseFirestore.getInstance();
        myItemsList=new ArrayList<>();
        myItemRecyclerView=findViewById(R.id.search_recycler);
        myItemRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        itemsRecyclerAdapter=new ItemsRecyclerAdapter(this,myItemsList);
        myItemRecyclerView.setAdapter(itemsRecyclerAdapter);
        //init local db
        databaseHelper = new DatabaseHelper(this);
        mySearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    //if empty than empty the ist
                    myItemsList.clear();
                    myItemsList = new ArrayList<>();
                    itemsRecyclerAdapter = new ItemsRecyclerAdapter(HomeActivity.this,myItemsList);
                    myItemRecyclerView.setAdapter(itemsRecyclerAdapter);

                }else{
                    myItemsList.clear();
                    searchItem(s.toString());
                }

            }
        });

        syncDataFromOnlineServerToLocal();


    }
    // this data sync tha data of local asqlite database with firebase database
    private void syncDataFromOnlineServerToLocal() {
        if(myAuth.getUid()!=null) {
            myStore.collection("User").document(myAuth.getCurrentUser().getUid())
                    .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                            Address address = doc.toObject(Address.class);
                            boolean isAddressAdded = false;
                            ArrayList<Address> addressArrayList = new ArrayList<>();
                            addressArrayList = databaseHelper.getAllAddresses();
                            //matching if any new data added on firebase
                            for (int i=0;i<addressArrayList.size();i++){
                                if(address.getAddress().toLowerCase().equals(addressArrayList.get(i).getAddress().toLowerCase())) {
                                    isAddressAdded = true;
                                    break;
                                }
                            }
                            // if new data found it will add in local db
                            if(!isAddressAdded){
                                databaseHelper.insertAddress(address);
                            }

                        }
                    }
                }
            });
        }else {
            if (Utils.isInternetConnected) {
                User user = new User();

                user = getUserFromShared();
                if(!user.getEmail().isEmpty() && !user.getPassword().isEmpty()) {
                    myAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //bij success
                            if (task.isSuccessful()) {

                                //redirect naar de home activity eens de registratie voltooid is
                            }
                        }
                    });
                }

            }
        }

    }

    private void searchItem(String text) {
        // if internet available search from server
        if(Utils.isInternetConnected) {
            if (!text.isEmpty()) {
                myStore.collection("All").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful() && task.getResult() != null) {

                                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {

                                        Items items = doc.toObject(Items.class);
                                        Log.d("addingtest", items.getType());
                                        Log.d("working", items.getName());
                                        // if any item name contains the text that is matching with input than add in arraylist
                                        if(items.getName().toLowerCase().contains(text.toLowerCase())) {
                                            Log.d("working",text);
                                            myItemsList.add(items);
                                            itemsRecyclerAdapter.notifyDataSetChanged();
                                        }
                                        //algorithm to sync data of search if any one not present in local d
                                        boolean isItemAlreadyAdded = false;
                                        ArrayList<Items> itemArrayList = new ArrayList<Items>();
                                        itemArrayList = databaseHelper.getAllItems();
                                        for (int i=0;i<itemArrayList.size();i++){
                                            if(itemArrayList.get(i).getName().toLowerCase().equals(items.getName().toLowerCase())){
                                                isItemAlreadyAdded = true;
                                                break;
                                            }
                                        }

                                        if(!isItemAlreadyAdded){
                                            Log.d("addingtest", items.getName());
                                            itemArrayList.add(items);
                                            databaseHelper.insertItem(items);
                                        }

                                    }
                                }
                            }
                        });
            }
        }else {
            // in case if internet available the app will run
            ArrayList<Items> itemArrayList = new ArrayList<Items>();

            itemArrayList = databaseHelper.getAllItems();
            if(!itemArrayList.isEmpty() && !text.isEmpty()){
                for (int i=0;i<itemArrayList.size();i++){
                    Log.d("workingt",itemArrayList.get(i).getName());
                    if(itemArrayList.get(i).getName().toLowerCase().contains(text.toLowerCase())){
                        myItemsList.add(itemArrayList.get(i));
                        itemsRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }

        }


    }

    private void loadFragment(Fragment homeFragment) {

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container,homeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout_btn){

            databaseHelper.deleteAddress();
            setSharedPreferences(false);
            Intent intent=new Intent(HomeActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            myAuth.signOut();
        }
        if(item.getItemId()==R.id.change_lang){
            startActivity(new Intent(HomeActivity.this,ChangeLanguage.class));
        }
        return true;
    }
    //save in share preferences
    private void setSharedPreferences(boolean isLogin){
        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
        editor.putBoolean("isLogin", isLogin);
        editor.apply();
    }
    private void setUserToShared(User user){
        SharedPreferences.Editor editor = getSharedPreferences("userdata", MODE_PRIVATE).edit();
        editor.putString("email", user.getEmail());
        editor.putString("pass",user.getPassword());
        editor.apply();
    }

    private User getUserFromShared(){
        User user = new User();
        SharedPreferences prefs = getSharedPreferences("userdata", MODE_PRIVATE);
        user.setEmail(prefs.getString("email", ""));
        user.setPassword(prefs.getString("pass",""));

        return user;


    }


}
package com.ehb.androiddevapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ehb.androiddevapp.database.DatabaseHelper;
import com.ehb.androiddevapp.adapter.AddressAdapter;
import com.ehb.androiddevapp.domain.Address;
import com.ehb.androiddevapp.domain.BestSeller;
import com.ehb.androiddevapp.domain.Featured;
import com.ehb.androiddevapp.domain.Items;
import com.ehb.androiddevapp.domain.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress {

    private RecyclerView myAddressRecyclerView;
    private AddressAdapter myAddressAdapter;
    private Button paymentBtn;
    private Button myAddAddress;
    private List<Address> myAddressList;
    private FirebaseFirestore myStore;
    private FirebaseAuth myAuth;
    private Toolbar myToolbar;
    String address= "";


    //database class
    private DatabaseHelper databaseHelper;

    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        final Object obj=getIntent().getSerializableExtra("item");
        myAddressRecyclerView=findViewById(R.id.address_recycler);
        paymentBtn=findViewById(R.id.payment_btn);
        myAddAddress=findViewById(R.id.add_address_btn);
        myToolbar=findViewById(R.id.address_toolbar);
        backBtn = findViewById(R.id.back_btn);
        setSupportActionBar(myToolbar);
        myAuth=FirebaseAuth.getInstance();
        myStore=FirebaseFirestore.getInstance();
        myAddressList=new ArrayList<>();
        myAddressAdapter=new AddressAdapter(getApplicationContext(),myAddressList, this);
        myAddressRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        myAddressRecyclerView.setAdapter(myAddressAdapter);
        //init the local db
        databaseHelper = new DatabaseHelper(this);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        myAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddressActivity.this,AddAddressActivity.class);
                startActivity(intent);
            }
        });
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount=0.0;
                String url ="";
                String name ="";
                if(obj instanceof Featured){
                    Featured  f= (Featured) obj;
                    amount=f.getPrice();
                    url=f.getImg_url();
                    name=f.getName();
                }
                if(obj instanceof BestSeller){
                    BestSeller f= (BestSeller) obj;
                    amount=f.getPrice();
                    url=f.getImg_url();
                    name=f.getName();

                }
                if(obj instanceof Items){
                    Items  i= (Items) obj;
                    amount=i.getPrice();
                    url=i.getImg_url();
                    name=i.getName();

                }
                Intent intent=new Intent(AddressActivity.this,PaymentActivity.class);
                intent.putExtra("amount",amount);
                intent.putExtra("img_url",url);
                intent.putExtra("name",name);
                intent.putExtra("address", address);

                startActivity(intent);
            }
        });
    }
    //this function will send address data to firebase if any offline address added while internet not available
    private void sendDataToTheServer() {
        ArrayList<Address> addressArrayList = new ArrayList<>();
        addressArrayList = databaseHelper.getAllAddresses();

        for (int i = 0; i < addressArrayList.size(); i++) {

            Address address = new Address();
            address = addressArrayList.get(i);
            boolean isAddressPresent = false;
            for(int j=0 ; j<myAddressList.size();j++) {
                if (myAddressList.get(j).getAddress().equals(address.getAddress())){
                    isAddressPresent = true;
                    break;
                }
            }
            if(!isAddressPresent){
                Log.d("debugadressync","adding run");
                if(myAuth.getUid()!=null){
                    Map<String,String> myMap=new HashMap<>();
                    myMap.put("address",address.getAddress());
                    Address finalAddress = address;
                    myStore.collection("User").document(myAuth.getCurrentUser().getUid())
                            .collection("Address").add(myMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Log.d("debugadressync","Successfully added");
                                myAddressList.add(finalAddress);
                                myAddressAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        }
    }

    @Override
    public void setAddress(String s) {
        address = s;
    }

    //this function will run every time when address activity come in foreground
    @Override
    protected void onResume() {
        super.onResume();

        if(Utils.isInternetConnected && myAuth.getUid()!=null) {
            myAddressList.clear();
            myAddressList = new ArrayList<>();
            myAddressAdapter = new AddressAdapter(getApplicationContext(),myAddressList, this);
            myAddressRecyclerView.setAdapter(myAddressAdapter);
            myStore.collection("User").document(myAuth.getCurrentUser().getUid())
                    .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    int counter = 0;
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                            Address address = doc.toObject(Address.class);
                            Log.d("debugadressync",address.getAddress());
                            myAddressList.add(address);
                            myAddressAdapter.notifyDataSetChanged();
                            counter++;

                        }
                        sendDataToTheServer();
                    }
                }
            });

        }
        if (myAuth == null || !Utils.isInternetConnected) {
            {
                //if any update occur in local db it will update the adapter list as well
                myAddressList.clear();
                ArrayList<Address> addressArrayList = new ArrayList<>();
                addressArrayList = databaseHelper.getAllAddresses();

                for (int i = 0; i < addressArrayList.size(); i++) {

                    myAddressList.add(addressArrayList.get(i));
                    myAddressAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
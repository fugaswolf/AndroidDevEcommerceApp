package com.ehb.androiddevapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ehb.androiddevapp.adapter.AddressAdapter;
import com.ehb.androiddevapp.domain.Address;
import com.ehb.androiddevapp.domain.BestSeller;
import com.ehb.androiddevapp.domain.Featured;
import com.ehb.androiddevapp.domain.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        final Object obj=getIntent().getSerializableExtra("item");
        myAddressRecyclerView=findViewById(R.id.address_recycler);
        paymentBtn=findViewById(R.id.payment_btn);
        myAddAddress=findViewById(R.id.add_address_btn);
        myToolbar=findViewById(R.id.address_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myAuth=FirebaseAuth.getInstance();
        myStore=FirebaseFirestore.getInstance();
        myAddressList=new ArrayList<>();
        myAddressAdapter=new AddressAdapter(getApplicationContext(),myAddressList, this);
        myAddressRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        myAddressRecyclerView.setAdapter(myAddressAdapter);

        myStore.collection("User").document(myAuth.getCurrentUser().getUid())
                .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot doc:task.getResult().getDocuments()){
                        Address address=doc.toObject(Address.class);
                        myAddressList.add(address);
                        myAddressAdapter.notifyDataSetChanged();
                    }
                }
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

    @Override
    public void setAddress(String s) {
        address = s;
    }
}
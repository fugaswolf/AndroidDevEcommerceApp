package com.ehb.androiddevapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ehb.androiddevapp.database.DatabaseHelper;
import com.ehb.androiddevapp.domain.Address;
import com.ehb.androiddevapp.domain.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {
    private EditText myName;
    private EditText myCity;
    private EditText myAddress;
    private EditText myCode;
    private EditText myNumber;
    private Button myAddAddressBtn;
    private FirebaseFirestore myStore;
    private FirebaseAuth myAuth;
    private Toolbar myToolbar;

    //database class object
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        myName = findViewById(R.id.ad_name);
        myCity = findViewById(R.id.ad_city);
        myAddress = findViewById(R.id.ad_address);
        myCode = findViewById(R.id.ad_code);
        myNumber = findViewById(R.id.ad_phone);
        myAddAddressBtn = findViewById(R.id.ad_add_address);
        myStore = FirebaseFirestore.getInstance();
        myAuth = FirebaseAuth.getInstance();
        myToolbar = findViewById(R.id.add_address_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //init of local database
        databaseHelper = new DatabaseHelper(this);


        myAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=myName.getText().toString();
                String city=myCity.getText().toString();
                String address=myAddress.getText().toString();
                String code=myCode.getText().toString();
                String number=myNumber.getText().toString();
                String final_address="";
                if(!name.isEmpty()){
                    final_address+=name+", ";
                }
                if(!city.isEmpty()){
                    final_address+=city+", ";
                }
                if(!address.isEmpty()){
                    final_address+=address+", ";
                }
                if(!code.isEmpty()){
                    final_address+=code+", ";
                }
                if(!number.isEmpty()){
                    final_address+=number+", ";
                }
                Map<String,String> mMap=new HashMap<>();
                mMap.put("address",final_address);
                Address obj = new Address();
                obj.setAddress(final_address);
                //checking is internet available or not other wise save in local db
                if(Utils.isInternetConnected && myAuth.getUid()!=null) {
                    Log.d("debugaddress","with internet working");
                    myStore.collection("User").document(myAuth.getCurrentUser().getUid())
                            .collection("Address").add(mMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                //for synchronization purpose of online and offline database saving same object in local
                                databaseHelper.insertAddress(obj);
                                Toast.makeText(AddAddressActivity.this, "Address Added", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
                }else {
                    //if internet not availabel save it in local db
                    databaseHelper.insertAddress(obj);
                    Toast.makeText(AddAddressActivity.this, "Address Added", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }
}

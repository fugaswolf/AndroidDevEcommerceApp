package com.ehb.androiddevapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {
        TextView myTotal;
        Button payBtn;
        double amount=0.0;
        String name="";
        String img_url="";
        FirebaseFirestore myStore;
        FirebaseAuth myAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_payment);

                amount=getIntent().getDoubleExtra("amount",0.0);
                img_url=getIntent().getStringExtra("img_url");
                name=getIntent().getStringExtra("name");
                myStore= FirebaseFirestore.getInstance();
                myAuth=FirebaseAuth.getInstance();
                myTotal=findViewById(R.id.sub_total);
                payBtn=findViewById(R.id.pay_btn);
                myTotal.setText("€ "+amount+"");
                Checkout.preload(getApplicationContext());

                payBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                startPayment();
                        }
                });
        }
        public void startPayment() {

                Checkout checkout = new Checkout();


                final Activity activity = PaymentActivity.this;

                try {
                        JSONObject options = new JSONObject();
                        //Set Company Name
                        options.put("name", "MMA Store");
                        //Ref no
                        options.put("description", "Reference No. #123456");
                        //Image to be display
                        options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");

                        options.put("currency", "EUR");

                        amount = amount * 100;
                        //amount
                        options.put("amount", amount);
                        JSONObject preFill = new JSONObject();
                        //email
                        preFill.put("email", "mma@store.com");
                        //contact
                        preFill.put("contact", "7655566477");

                        options.put("prefill", preFill);

                        checkout.open(activity, options);
                } catch(Exception e) {
                        Log.e("TAG", "Error in starting Razorpay Checkout", e);
                }
        }

        @Override
        public void onPaymentSuccess(String s) {
                Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
                Map<String,Object> mMap = new HashMap<>();
                mMap.put("name",name);
                mMap.put("img_url",img_url);
                mMap.put("payment_id",s);

                myStore.collection("User").document(myAuth.getCurrentUser().getUid())
                        .collection("Orders").add(mMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                Intent intent=new Intent(PaymentActivity.this,HomeActivity.class);
                                startActivity(intent);
                                finish();
                        }
                });
        }

        @Override
        public void onPaymentError(int i, String s) {
                Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
        }

}

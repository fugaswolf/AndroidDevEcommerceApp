package com.ehb.androiddevapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {
        TextView myTotal;
        Button payBtn;
        double amount=0.0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                amount=getIntent().getDoubleExtra("amount",0.0);
                setContentView(R.layout.activity_payment);
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
                        options.put("name", "Jakob Broeder");
                        //Ref no
                        options.put("description", "Reference No. #123456");
                        //Image to be display
                        options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
                        //options.put("order_id", "order_9A33XWu170gUtm");
                        // Currency type
                        options.put("currency", "EUR");
                        //double total = Double.parseDouble(mAmountText.getText().toString());
                        //multiply with 100 to get exact amount in rupee
                        amount = amount * 100;
                        //amount
                        options.put("amount", amount);
                        JSONObject preFill = new JSONObject();
                        //email
                        preFill.put("email", "jakob@broeder.com");
                        //contact
                        preFill.put("contact", "46565605656");

                        options.put("prefill", preFill);

                        checkout.open(activity, options);
                } catch(Exception e) {
                        Log.e("TAG", "Error in starting Razorpay Checkout", e);
                }
        }

        @Override
        public void onPaymentSuccess(String s) {
                Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaymentError(int i, String s) {
                Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
        }
}

package com.example.mrfoodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Confirmation extends AppCompatActivity {
    Button mCustomer, mSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        mCustomer = findViewById(R.id.confirm_customer);
        mSeller = findViewById(R.id.confirm_seller);
        mCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

    }

    public void cseller(View view) {
        startActivity(new Intent(getApplicationContext(), SellerReg.class));
    }

    public void ccustomer(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
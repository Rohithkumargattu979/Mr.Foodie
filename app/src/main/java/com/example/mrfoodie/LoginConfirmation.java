package com.example.mrfoodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_confirmation);
    }

    public void lseller(View view) {
        startActivity(new Intent(getApplicationContext(), SellerHome.class));
    }

    public void lcustomer(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
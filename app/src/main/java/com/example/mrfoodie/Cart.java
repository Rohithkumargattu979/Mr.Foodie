package com.example.mrfoodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

public class Cart extends AppCompatActivity {
    CardView cardView;
    Button b1;
    ImageView iv;
    TextView tvTitle, tvDesc, tvPrice, ntc;
    LinearLayout ll;
    Uri a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cardView = findViewById(R.id.myCardView);
        b1 = findViewById(R.id.pbtn);
        iv = findViewById(R.id.ivimage);
        tvTitle = findViewById(R.id.tvTitle);
        tvDesc = findViewById(R.id.tvDesc);
        tvPrice = findViewById(R.id.tvPrice);
        ll = findViewById(R.id.ll);
        ntc = findViewById(R.id.ntc);

        Bundle mBundle = getIntent().getExtras();
        if(mBundle != null)
        {
             ll.setVisibility(View.VISIBLE);
             ntc.setVisibility(View.INVISIBLE);
             b1.setVisibility(View.VISIBLE);
             a = Uri.parse(mBundle.getString("itemImage"));
            Picasso.get().load(a).into(iv);
            //iv.setImageURI(Uri.parse(mBundle.getString("itemImage")));
            tvTitle.setText(mBundle.getString("itemName"));
            tvDesc.setText(mBundle.getString("itemDesc"));
            tvPrice.setText(mBundle.getString("itemPrice"));

        }

        /*BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.cart:
                        return true;
                }
                return false;
            }
        });*/

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.INVISIBLE);
                Toast.makeText(Cart.this, "Thanks For Ordering!", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
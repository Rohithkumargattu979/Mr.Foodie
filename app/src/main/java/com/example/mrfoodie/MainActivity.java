package com.example.mrfoodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
FirebaseAuth fAuth;
FirebaseFirestore fStore;
String userId;
String Id = SellerHome.Id;
CardView crv;
TextView ns,rs;
boolean temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        ns = findViewById(R.id.no_res);
        rs = findViewById(R.id.res);
        crv = findViewById(R.id.cv);

        crv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CustomerListFood.class));

            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(),Cart.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                }
                return false;
            }
        });



        // restaurant availability
        if(Id != null) {
            ns.setVisibility(View.INVISIBLE);
            rs.setVisibility(View.VISIBLE);
            crv.setVisibility(View.VISIBLE);
            DocumentReference documentReference1 = fStore.collection("Susersstat").document(Id);
            documentReference1.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot1, @Nullable FirebaseFirestoreException e) {
                    if (documentSnapshot1.exists()) {
                        temp = documentSnapshot1.getBoolean("Available");
                    } else {
                        Log.d("tag", "onEvent: Document do not exists");
                    }
                }
            });

            DocumentReference documentReference = fStore.collection("Susers").document(Id);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (documentSnapshot.exists()) {
                        if (temp) {
                            rs.setText(documentSnapshot.getString("Restaurant Name"));
                        }


                    } else {
                        Log.d("tag", "onEvent: Document do not exists");
                    }
                }
            });
            // restaurant availabilty end
        }

    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
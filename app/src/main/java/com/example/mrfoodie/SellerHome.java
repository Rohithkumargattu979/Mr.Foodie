package com.example.mrfoodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class SellerHome extends AppCompatActivity {
    SwitchCompat rswitch;
    FirebaseAuth sfAuth;
    FirebaseFirestore sfStore;
    TextView rsn;
    Button lsf;
    boolean status;
    Map<String,Object> user = new HashMap<>();
    String sUserID, TAG = "Seller Home";
    public static String Id = null;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        rswitch = findViewById(R.id.switch1);
        rsn = findViewById(R.id.res_name);
        sfAuth = FirebaseAuth.getInstance();
        sfStore = FirebaseFirestore.getInstance();
        Id = sfAuth.getCurrentUser().getUid();
        lsf = findViewById(R.id.lisf);
        rswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sUserID = sfAuth.getCurrentUser().getUid();
                DocumentReference documentReference = sfStore.collection("Susersstat").document(sUserID);
                status = compoundButton.isChecked();
                user.put("Available", status);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: user Profile is created for "+ sUserID);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });
            }
        });

        // restaurant name visibility
        DocumentReference documentReference = sfStore.collection("Susers").document(Id);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    rsn.setText(documentSnapshot.getString("Restaurant Name"));

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

    }

    public void Logout(View view)
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
        Toast.makeText(SellerHome.this, "You are logged out", Toast.LENGTH_SHORT).show();
    }

    public void lsf(View view) {
        startActivity(new Intent(getApplicationContext(), ListFood.class));
    }
}
package com.example.mrfoodie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.libraries.places.api.Places;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerReg extends AppCompatActivity {
    EditText mRestaurant, mAdd1, mAdd2, mAdd3;
    Button mLocation, mSave;
    ProgressBar progressBar;
    FirebaseAuth sfAuth;
    FirebaseFirestore sfStore;
    ProgressDialog progressDialog;
    TextView t1;
    WifiManager wifiManager;
    int PLACE_REQUEST = 1;
    String lon, lat, sUserID, apiKey = "AIzaSyDOkAefn9IuHnggD41CFMLdSm1bsmFTItk";
    Map<String,Object> user = new HashMap<>();
    private static final String TAG = "SellerReg";
    private final static int PLACE_PICKER_REQUEST = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_reg);
        mLocation = findViewById(R.id.location);
        mSave = findViewById(R.id.create_seller);
        mRestaurant = findViewById(R.id.seller_name);
        mAdd1 = findViewById(R.id.seller_address1);
        mAdd2 = findViewById(R.id.seller_address2);
        mAdd3 = findViewById(R.id.seller_address3);

        sfAuth = FirebaseAuth.getInstance();
        sfStore = FirebaseFirestore.getInstance();
        wifiManager= (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);



       /* mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(SellerReg.this),PLACE_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });*/

       mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Disable Wifi
                wifiManager.setWifiEnabled(false);
                openPlacePicker();
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Restaurant = mRestaurant.getText().toString().trim();
                String street = mAdd1.getText().toString().trim();
                final String city = mAdd2.getText().toString();
                final String pincode    = mAdd3.getText().toString();

                if(TextUtils.isEmpty(Restaurant)){
                    mRestaurant.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(city)){
                    mAdd1.setError("City Name is Required.");
                    return;
                }
                if(TextUtils.isEmpty(street)){
                    mAdd2.setError("Street Address Required.");
                    return;
                }
                if(TextUtils.isEmpty(pincode)){
                    mAdd3.setError("Pincode is Required.");
                    return;
                }
                progressDialog = new ProgressDialog(SellerReg.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Toast.makeText(SellerReg.this, "Seller Account Created!", Toast.LENGTH_SHORT).show();
                sUserID = sfAuth.getCurrentUser().getUid();
                DocumentReference documentReference = sfStore.collection("Susers").document(sUserID);
                user.put("Restaurant Name",Restaurant);
                user.put("Street",street);
                user.put("City",city);
                user.put("PinCode", pincode);
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
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                /*documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: user Profile is created for "+ sUserID);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });*/
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            /*else {
                Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }*/




            }
        });

}


    private void openPlacePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);

            //Enable Wifi
            wifiManager.setWifiEnabled(true);

        } catch (GooglePlayServicesRepairableException e) {
            Log.d("Exception",e.getMessage());

            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.d("Exception",e.getMessage());

            e.printStackTrace();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case PLACE_PICKER_REQUEST:
                    Place place = PlacePicker.getPlace(SellerReg.this, data);

                    double latitude = place.getLatLng().latitude;
                    double longitude = place.getLatLng().longitude;
                    lat = String.valueOf(latitude);
                    lon = String.valueOf(longitude);
                    user.put("Longitude", lon);
                    user.put("Latitude", lat);

            }
        }
    }
}
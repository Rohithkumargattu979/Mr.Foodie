package com.example.mrfoodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListFood extends AppCompatActivity {
    RecyclerView mRecyclerView;
    ArrayList<FoodData> myFoodList;
    FoodData mFoodData;
    ProgressDialog progressDialog;
    public static FloatingActionButton floating = null;

    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);
        mRecyclerView = findViewById(R.id.recyclerview);
        floating = findViewById(R.id.adding);
        floating.setVisibility(View.VISIBLE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ListFood.this, 1);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Items...");

        myFoodList = new ArrayList<>();
        MyAdapter myAdapter = new MyAdapter(ListFood.this, myFoodList);
        mRecyclerView.setAdapter(myAdapter);


        databaseReference = FirebaseDatabase.getInstance().getReference("Recipe");
        progressDialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                myFoodList.clear();
                for(DataSnapshot itemSnapshot: snapshot.getChildren())
                {
                    FoodData foodData = itemSnapshot.getValue(FoodData.class);
                    myFoodList.add(foodData);
                }
                myAdapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });
    }

    public void Addfood(View view) {
        startActivity(new Intent(getApplicationContext(), AddFood.class));

    }

}
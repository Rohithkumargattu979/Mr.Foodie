package com.example.mrfoodie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class AddFood extends AppCompatActivity {
    ImageView foodimg;
    Uri uri;
    EditText fname, fdesc, fcost;
    String imageUrl;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        foodimg = findViewById(R.id.fimg);
        fname = findViewById(R.id.itname);
        fdesc = findViewById(R.id.itdes);
        fcost = findViewById(R.id.itcost);
        button1 = findViewById(R.id.selimg);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent PhotoPicker = new Intent(Intent.ACTION_VIEW);
                PhotoPicker.setType("image/*");
                PhotoPicker.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(PhotoPicker,
                        "Select Picture"), 1);
                
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            uri = data.getData();
            foodimg.setImageURI(uri);
        }
        else{
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadfImage() {
        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference().child("FoodImage").child(uri.getLastPathSegment());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                uploadRecipe();

            }
        });
    }

    public void uploadfood(View view) {
        uploadfImage();
    }
     public void uploadRecipe() {
         ProgressDialog progressDialog = new ProgressDialog(this);
         progressDialog.setMessage("Adding Item...");
         progressDialog.show();

         FoodData foodData = new FoodData(
                 fname.getText().toString(),
                 fdesc.getText().toString(),
                 fcost.getText().toString(),
                 imageUrl

         );

         String myCurrentDateTime = DateFormat.getDateTimeInstance()
                 .format(Calendar.getInstance().getTime());
         FirebaseDatabase.getInstance().getReference("Recipe")
                 .child(myCurrentDateTime).setValue(foodData).addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {

                 if(task.isSuccessful())
                 {
                     Toast.makeText(AddFood.this, "Item Uploaded", Toast.LENGTH_SHORT).show();
                     progressDialog.dismiss();
                     finish();
                 }

             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(AddFood.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                 progressDialog.dismiss();
             }
         });
     }
}

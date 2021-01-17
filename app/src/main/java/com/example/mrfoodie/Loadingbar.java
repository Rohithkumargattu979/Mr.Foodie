package com.example.mrfoodie;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class Loadingbar {

    Activity activity;
    AlertDialog dialog;

    Loadingbar(Activity thisactivity){
        activity = thisactivity;
    }

    void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progress,null));
        dialog = builder.create();
        dialog.show();
    }

    void dismissbar(){
        dialog.dismiss();
    }
}
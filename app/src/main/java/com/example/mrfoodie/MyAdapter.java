 package com.example.mrfoodie;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<FoodViewHolder> {
    private Context mContext;
    private List<FoodData> myFoodList;

    public MyAdapter(Context mContext, List<FoodData> myFoodList) {
        this.mContext = mContext;
        this.myFoodList = myFoodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_item, parent, false);
        return new FoodViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int i) {
        Glide.with(mContext)
                .load(myFoodList.get(i).getItemImage())
                .into(holder.imageView);

        //holder.imageView.setImageResource(myFoodList.get(i).getItemImage());
        holder.mTitle.setText(myFoodList.get(i).getItemName());
        holder.mDesc.setText(myFoodList.get(i).getItemDesc());
        holder.mPrice.setText(myFoodList.get(i).getItemPrice());
        
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Cart.class);
                intent.putExtra("itemImage", myFoodList.get(holder.getAdapterPosition()).getItemImage());
                intent.putExtra("itemName", myFoodList.get(holder.getAdapterPosition()).getItemName());
                intent.putExtra("itemPrice", myFoodList.get(holder.getAdapterPosition()).getItemPrice());
                intent.putExtra("itemDesc", myFoodList.get(holder.getAdapterPosition()).getItemDesc());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return myFoodList.size();
    }
}
class FoodViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView mTitle,mDesc,mPrice;
    CardView mCardView;

    public FoodViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.ivimage);
        mTitle = itemView.findViewById(R.id.tvTitle);
        mDesc = itemView.findViewById(R.id.tvDesc);
        mPrice = itemView.findViewById(R.id.tvPrice);
        mCardView = itemView.findViewById(R.id.myCardView);
    }
}

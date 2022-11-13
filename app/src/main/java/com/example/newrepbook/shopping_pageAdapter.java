package com.example.newrepbook;

import android.app.Activity;
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
import java.util.ArrayList;

public class shopping_pageAdapter extends RecyclerView.Adapter<shopping_pageAdapter.gridViewHolder> {
    private ArrayList<shopping_pageInfo> mDataset;
    private Activity activity;

    static class gridViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView food_image;
        TextView food_name;
        TextView food_price;

        gridViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
            this.food_image = itemView.findViewById(R.id.food_image);
            food_image.setScaleType(ImageView.ScaleType.FIT_XY); // 이미지가 꽉차게 보임
            this.food_name = itemView.findViewById(R.id.food_name);
            this.food_price = itemView.findViewById(R.id.food_price);
        }
    }

    public shopping_pageAdapter(Activity activity, ArrayList<shopping_pageInfo> myDataset) {
        mDataset = myDataset;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public shopping_pageAdapter.gridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_page2, parent, false);
        final shopping_pageAdapter.gridViewHolder gridViewHolder = new shopping_pageAdapter.gridViewHolder(cardView);
//        cardView.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                Intent intent = new Intent(activity, PostActivity.class);
//                intent.putExtra("shopping_pageInfo", mDataset.get(gridViewHolder.getAdapterPosition()));
//                activity.startActivity(intent);;
//            }
//        });
        return gridViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final shopping_pageAdapter.gridViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(mDataset.get(position).getFood_image())
                .into(holder.food_image);
        holder.food_name.setText(mDataset.get(position).getFood_name());
        holder.food_price.setText(""+mDataset.get(position).getFood_price()); // int형은 textView에 출력이 안됨 따라서 앞에 ""를 붙여 String 값으로 인식되게 한다.
    }

    @Override
    public int getItemCount() {
        return mDataset.size() ;
    }

}

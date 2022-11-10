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

public class material_pushAdapter extends RecyclerView.Adapter<material_pushAdapter.materialViewHolder> {
    private ArrayList<material_info> mDataset;
    private Activity activity;

    static class materialViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView material_name;

        materialViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
            this.material_name = itemView.findViewById(R.id.material_name);
        }
    }

    public material_pushAdapter(Activity activity, ArrayList<material_info> myDataset) {
        mDataset = myDataset;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public material_pushAdapter.materialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.materials_design, parent, false);
        final material_pushAdapter.materialViewHolder materialViewHolder = new material_pushAdapter.materialViewHolder(cardView);
        return materialViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull material_pushAdapter.materialViewHolder holder, int position) {
        holder.material_name.setText(mDataset.get(position).getMaterial_name());
    }

    @Override
    public int getItemCount() {
        return mDataset.size() ;
    }

}

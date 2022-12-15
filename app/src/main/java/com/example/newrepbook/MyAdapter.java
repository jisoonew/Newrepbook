package com.example.newrepbook;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder>{


    ArrayList<UserModel> userModel;

    public MyAdapter(ArrayList<UserModel> userModel) {
        this.userModel = userModel;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.id.setText(userModel.get(position).getId());
        holder.name.setText(userModel.get(position).getName());
        Glide.with(holder.itemView)
                .load(userModel.get(position).getPurl())
                .override(250)
                .into(holder.purl);

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.name.getContext(),DetailsActivity.class);
                intent.putExtra("uname",userModel.get(position).getName());
                intent.putExtra("uid",userModel.get(position).getId());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.name.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userModel.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ImageView purl;
        TextView name, id;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
//            id = itemView.findViewById(R.id.id);
//            purl = itemView.findViewById(R.id.purl);


        }
    }
}

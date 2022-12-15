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

public class listMainAdapter1 extends RecyclerView.Adapter<listMainAdapter1.listViewHolder> {
    private ArrayList<PostInfo2> mDataset;
    private Activity activity;

    static class listViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView tv_profile;
        TextView tv_text1;

        listViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
            this.tv_profile = itemView.findViewById(R.id.tv_profile);
            tv_profile.setScaleType(ImageView.ScaleType.FIT_XY); // 이미지가 꽉차게 보임
            this.tv_text1 = itemView.findViewById(R.id.tv_text1);
        }
    }

    public listMainAdapter1(Activity activity, ArrayList<PostInfo2> myDataset) {
        mDataset = myDataset;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public listMainAdapter1.listViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.listviewactivity, parent, false);
        final listViewHolder listViewHolder = new listViewHolder(cardView);
        cardView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(activity, PostActivity2.class);
                intent.putExtra("postInfo2", mDataset.get(listViewHolder.getAdapterPosition()));
                activity.startActivity(intent);;
            }
        });
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final listViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(mDataset.get(position).getProfile())
                .into(holder.tv_profile);
        holder.tv_text1.setText(mDataset.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mDataset.size() ;
    }

    private void myStartActivity(Class c, PostInfo postInfo2) {
        Intent intent = new Intent(activity, c);
        intent.putExtra("postInfo2", postInfo2);
        activity.startActivity(intent);
    }

}
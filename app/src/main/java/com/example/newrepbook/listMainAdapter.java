package com.example.newrepbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.core.InFilter;

import java.util.ArrayList;

public class listMainAdapter extends RecyclerView.Adapter<listMainAdapter.listViewHolder> {
    private ArrayList<PostInfo> mDataset;
    private Activity activity;

    static class listViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        listViewHolder(CardView v) {
            super(v);
            cardView = v;

//            LinearLayout contentsLayout = cardView.findViewById(R.id.contentsLayout);
//            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            ArrayList<String> contentsList = position.getContents();
//
//            if(contentsLayout.getChildCount() == 0){
//                for (int i = 0; i < contentsList.size() ; i++){
//                    String contents = contentsList.get(i);
//                    if(Patterns.WEB_URL.matcher(contents).matches()){
//                        ImageView imageView = new ImageView(activity);
//                        imageView.setLayoutParams(layoutParams);
//                        imageView.setAdjustViewBounds(true);
//                        imageView.setScaleType(ImageView.ScaleType.FIT_XY); // 리스트 글의 썸네일이 꽉차게 보임
//                        contentsLayout.addView(imageView);
//                        Glide.with(activity).load(contents).override(1000).thumbnail(0.1f).into(imageView);
//                    }else {
//                        TextView textView = new TextView(activity);
//                        textView.setLayoutParams(layoutParams);
//                        textView.setText(contents);
//                        contentsLayout.addView(textView);
//                    }
//                }
//            }

        }
    }

    public listMainAdapter(Activity activity, ArrayList<PostInfo> myDataset) {
        mDataset = myDataset;
        this.activity = activity;

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public listMainAdapter.listViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.practicelistviewactivity, parent, false);
        final listViewHolder listViewHolder = new listViewHolder(cardView);
        cardView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(activity, PostActivity.class);
                intent.putExtra("postInfo", mDataset.get(listViewHolder.getAdapterPosition()));
                activity.startActivity(intent);
            }
        });


        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final listViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView tv_text1 = cardView.findViewById(R.id.tv_text1);
        TextView tv_text2 = cardView.findViewById(R.id.tv_text2);
        tv_text1.setText(mDataset.get(position).getTitle());
        tv_text2.setText(mDataset.get(position).getPublisher());
    }

    @Override
    public int getItemCount() {
        return mDataset.size() ;
    }

    private void myStartActivity(Class c, PostInfo postInfo) {
        Intent intent = new Intent(activity, c);
        intent.putExtra("postInfo", postInfo);
        activity.startActivity(intent);
    }

}
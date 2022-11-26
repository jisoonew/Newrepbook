package com.example.newrepbook;

import android.app.Activity;
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

public class shopping_basket_Adapter extends RecyclerView.Adapter<shopping_basket_Adapter.gridViewHolder> {
    private ArrayList<Shopping_basket_info> Dataset;
    private Activity activity;

    static class gridViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView shopping_basket_image;
        TextView shopping_basket_name;
        TextView shopping_basket_price;

        gridViewHolder(CardView cardView1) {
            super(cardView1);
            cardView = cardView1;
            this.shopping_basket_image = itemView.findViewById(R.id.shopping_basket_image);
            shopping_basket_image.setScaleType(ImageView.ScaleType.FIT_XY); // 이미지가 꽉차게 보임
            this.shopping_basket_name  = itemView.findViewById(R.id.shopping_basket_name);
//            this.shopping_basket_price = itemView.findViewById(R.id.shopping_basket_price);
        }
    }

    public shopping_basket_Adapter(Activity activity, ArrayList<Shopping_basket_info> myDataset) {
        Dataset = myDataset;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public shopping_basket_Adapter.gridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView1 = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_basket_design, parent, false);
//        final shopping_basket_Adapter.gridViewHolder gridViewHolder = new shopping_basket_Adapter.gridViewHolder((CardView) cardView1);
//        cardView1.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                Intent intent = new Intent(activity, shopping_detailed_page.class);
//                intent.putExtra("shopping_page2", Dataset.get(gridViewHolder.getAdapterPosition()));
//                activity.startActivity(intent);
//            }
//        });
        return new shopping_basket_Adapter.gridViewHolder((CardView) cardView1);
    }

    @Override
    public void onBindViewHolder(@NonNull final shopping_basket_Adapter.gridViewHolder holder, int position) {
        Shopping_basket_info shopping_basket_info = Dataset.get(position);

        Glide.with(holder.itemView)
                .load(shopping_basket_info.getShopping_image())
                .into(holder.shopping_basket_image);
        holder.shopping_basket_name.setText(shopping_basket_info.getShopping_name());
        //holder.shopping_basket_price.setText(""+Dataset.get(position).getShopping_price()); // int형은 textView에 출력이 안됨 따라서 앞에 ""를 붙여 String 값으로 인식되게 한다.
    }

    @Override
    public int getItemCount() {
        return Dataset.size() ;
    }

}

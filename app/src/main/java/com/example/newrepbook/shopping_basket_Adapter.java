package com.example.newrepbook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.service.autofill.Dataset;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.animation.content.Content;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class shopping_basket_Adapter extends RecyclerView.Adapter<shopping_basket_Adapter.gridViewHolder>{
    private ArrayList<Shopping_basket_info> dataset;
    private Activity activity;
    Content mContent;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    Task<Void> databaseReference1;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        class gridViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView shopping_basket_image;
        TextView shopping_basket_name;
        TextView shopping_basket_price;
        Button delete_button;
            private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            private DatabaseReference databaseReference = firebaseDatabase.getReference();
            private DatabaseReference databaseReference1;
            private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        public gridViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
            this.shopping_basket_image = itemView.findViewById(R.id.shopping_basket_image);
            shopping_basket_image.setScaleType(ImageView.ScaleType.FIT_XY); // 이미지가 꽉차게 보임
            this.shopping_basket_name  = itemView.findViewById(R.id.shopping_basket_name);
            this.shopping_basket_price = itemView.findViewById(R.id.shopping_basket_price);
            this.delete_button = itemView.findViewById(R.id.delete_button);
            }
    }

    public shopping_basket_Adapter(Activity activity, ArrayList<Shopping_basket_info> myDataset) {
        dataset = myDataset;
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
//
//            }
//        });

        return new shopping_basket_Adapter.gridViewHolder((CardView) cardView1);
    }


    @Override
    public void onBindViewHolder(@NonNull final shopping_basket_Adapter.gridViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Shopping_basket_info shopping_basket_info = dataset.get(position);

        Glide.with(holder.itemView)
                .load(shopping_basket_info.getShopping_image())
                .into(holder.shopping_basket_image);
        holder.shopping_basket_name.setText(shopping_basket_info.getShopping_name());
        holder.shopping_basket_price.setText(""+dataset.get(position).getShopping_price()); // int형은 textView에 출력이 안됨 따라서 앞에 ""를 붙여 String 값으로 인식되게 한다.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener confirm = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("삭제 테스트");
                        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                        databaseReference.child(user.getUid()).child("buy").child(dataset.get(position).getShopping_uid()).setValue(null);
                        dataset.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, dataset.size());
                    }
                };
                DialogInterface.OnClickListener cancle = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 취소 되었을 때
                    }
                };
                new AlertDialog.Builder(activity)
                        .setTitle("삭제하시겠습니까?")
                        .setPositiveButton("삭제", confirm)
                        .setNegativeButton("취소", cancle)
                        .show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataset.size() ;
    }

}

package com.example.newrepbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class shopping_detailed_page extends BasicActivity {
        private int count = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_detailed_page);

        shopping_pageInfo Shopping_pageInfo = (shopping_pageInfo) getIntent().getSerializableExtra("shopping_page2");

        LinearLayout shopping_detailed_pageLayout1 = (LinearLayout) findViewById(R.id.shopping_detailed_pageLayout1); // 상품 상세 레이아웃 이미지

        LinearLayout shopping_detailed_pageLayout2 = (LinearLayout) findViewById(R.id.shopping_detailed_pageLayout2); // 상품 상세 레이아웃 나머지

        TextView reckoning_number = (TextView) findViewById(R.id.reckoning_number);

        Button subtraction_button = (Button) findViewById(R.id.subtraction_button);
        Button addition_button = (Button) findViewById(R.id.addition_button);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        String food_area_of_production = Shopping_pageInfo.getFood_area_of_production();  // 생산지
        String food_storage = Shopping_pageInfo.getFood_storage();  // 보관법
        String food_image = Shopping_pageInfo.getFood_image();  // 상품 이미지
        String food_amount = Shopping_pageInfo.getFood_amount();  // 수량
        String food_name = Shopping_pageInfo.getFood_name();  // 상품 이름

        addition_button.setOnClickListener(new View.OnClickListener() { // 덧셈 버튼 기능
            @Override
            public void onClick(View v) {
                count++;
                reckoning_number.setText(count+"");
            }
        });

        subtraction_button.setOnClickListener(new View.OnClickListener() { // 뺄셈 버튼 기능
            @Override
            public void onClick(View v) {
                if(count>0){
                    count--;
                    reckoning_number.setText(count+"");
                }
            }
        });

        // 레시피 제목과 요리 완성 이미지
        if(shopping_detailed_pageLayout1.getTag() == null && shopping_detailed_pageLayout2.getTag() == null){
            shopping_detailed_pageLayout2.setTag(food_area_of_production);  // 생산지
            shopping_detailed_pageLayout2.setTag(food_storage);  // 보관법
            shopping_detailed_pageLayout1.setTag(food_image);  // 상품 이미지
            shopping_detailed_pageLayout2.setTag(food_amount);  // 수량
            shopping_detailed_pageLayout2.setTag(food_name);  // 상품 이름

            shopping_detailed_pageLayout1.removeAllViews(); // FrameLayout에 포함된 모든 자식(Children) 뷰 제거
            shopping_detailed_pageLayout2.removeAllViews(); // FrameLayout에 포함된 모든 자식(Children) 뷰 제거

            ImageView MainIamge = new ImageView(this);
            MainIamge.setLayoutParams(layoutParams);
            MainIamge.setAdjustViewBounds(true);
            MainIamge.setScaleType(ImageView.ScaleType.FIT_XY); // 이미지가 꽉차게 보임
            shopping_detailed_pageLayout1.addView(MainIamge);
            Glide.with(this).load(food_image).override(1000).thumbnail(0.1f).into(MainIamge); // 이미지 로딩

            TextView food_name_text = new TextView(this); // 상품 이름
            int size1 = 40;
            food_name_text.setTextSize(size1);
            food_name_text.setLayoutParams(layoutParams);
            food_name_text.setGravity(Gravity.CENTER); // 제목 가운데 정렬
            food_name_text.setText(food_name);
            shopping_detailed_pageLayout2.addView(food_name_text);

            TextView food_amount_text = new TextView(this); // 상품 총량
            int size2 = 20;
            food_amount_text.setTextSize(size2);
            food_amount_text.setLayoutParams(layoutParams);
            food_amount_text.setGravity(Gravity.CENTER); // 제목 가운데 정렬
            food_amount_text.setText(food_amount);
            shopping_detailed_pageLayout2.addView(food_amount_text);

            TextView food_area_of_production_text = new TextView(this); // 상품 생산지
            food_area_of_production_text.setTextSize(size2);
            food_area_of_production_text.setLayoutParams(layoutParams);
            food_area_of_production_text.setGravity(Gravity.CENTER); // 제목 가운데 정렬
            food_area_of_production_text.setText(food_area_of_production);
            shopping_detailed_pageLayout2.addView(food_area_of_production_text);

            TextView food_storage_text = new TextView(this); // 상품 생산지
            food_storage_text.setTextSize(size2);
            food_storage_text.setLayoutParams(layoutParams);
            food_storage_text.setGravity(Gravity.CENTER); // 제목 가운데 정렬
            food_storage_text.setText(food_storage);
            shopping_detailed_pageLayout2.addView(food_storage_text);
        }
    }
}

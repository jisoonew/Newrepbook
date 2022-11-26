package com.example.newrepbook;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;

public class shopping_detailed_page extends BasicActivity {
    private int count = 0; // 수량
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private int buy_num=1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_detailed_page);

        shopping_pageInfo Shopping_pageInfo = (shopping_pageInfo) getIntent().getSerializableExtra("shopping_page2");

        LinearLayout shopping_detailed_pageLayout1 = (LinearLayout) findViewById(R.id.shopping_detailed_pageLayout1); // 상품 상세 레이아웃 이미지

        LinearLayout shopping_detailed_pageLayout2 = (LinearLayout) findViewById(R.id.shopping_detailed_pageLayout2); // 상품 상세 레이아웃 나머지

        TextView reckoning_number = (TextView) findViewById(R.id.reckoning_number);

        Button subtraction_button = (Button) findViewById(R.id.subtraction_button);
        Button addition_button = (Button) findViewById(R.id.addition_button);
        Button buy_button = (Button) findViewById(R.id.buy_button);


        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        String food_area_of_production = Shopping_pageInfo.getFood_area_of_production();  // 생산지
        String food_storage = Shopping_pageInfo.getFood_storage();  // 보관법
        String food_image = Shopping_pageInfo.getFood_image();  // 상품 이미지
        String food_amount = Shopping_pageInfo.getFood_amount();  // 수량
        String food_name = Shopping_pageInfo.getFood_name();  // 상품 이름
        String food_feature = Shopping_pageInfo.getFood_feature(); // 상품 특징
        int food_price = (Shopping_pageInfo.getFood_price()); // 상품 가격

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

            TextView food_price_text = new TextView(this); // 상품 이름
            food_price_text.setTextSize(size1);
            food_price_text.setLayoutParams(layoutParams);
            food_price_text.setGravity(Gravity.CENTER); // 제목 가운데 정렬
            food_price_text.setText(food_price+"원");
            shopping_detailed_pageLayout2.addView(food_price_text);

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

            TextView food_storage_text = new TextView(this); // 상품 보관법
            food_storage_text.setTextSize(size2);
            food_storage_text.setLayoutParams(layoutParams);
            food_storage_text.setGravity(Gravity.CENTER); // 제목 가운데 정렬
            food_storage_text.setText(food_storage);
            shopping_detailed_pageLayout2.addView(food_storage_text);

            TextView food_feature_text = new TextView(this); // 상품 특징
            food_feature_text.setTextSize(size2);
            food_feature_text.setLayoutParams(layoutParams);
            food_feature_text.setGravity(Gravity.CENTER); // 제목 가운데 정렬
            food_feature_text.setText(food_feature);
            shopping_detailed_pageLayout2.addView(food_feature_text);


            buy_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                        databaseReference.child("Users").child(user.getUid()).child("buy").push().child("음식_이름").setValue(food_name_text.getText().toString());
//                        databaseReference.child("Users").child(user.getUid()).child("buy").push().child("음식_수량").setValue(food_amount_text.getText().toString());

                    //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                    HashMap<String, Object> hashMap = new HashMap<>();

                    if (food_name_text.getText().toString() == "0"){
                        Toast.makeText(getApplicationContext(), "수량을 선택해주세요!", Toast.LENGTH_SHORT).show();
                    } else {
                        hashMap.put("shopping_name", food_name_text.getText().toString());
                        hashMap.put("reckoning_number", reckoning_number.getText().toString());
                        hashMap.put("shopping_amount", food_amount_text.getText().toString());
                        hashMap.put("shopping_image", food_image);
                        hashMap.put("shopping_price", Integer.valueOf(food_price));

                        databaseReference.child("Users").child(user.getUid()).child("buy").push().setValue(hashMap);

                        Toast.makeText(getApplicationContext(), "장바구니 저장!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("VALUE",buy_num);
    }
}
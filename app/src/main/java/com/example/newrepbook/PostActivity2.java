package com.example.newrepbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PostActivity2 extends BasicActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post1);

        PostInfo2 postInfo = (PostInfo2) getIntent().getSerializableExtra("postInfo2");

        LinearLayout contentsLayout = (LinearLayout) findViewById(R.id.contentsLayout); // 레시피 내용
        LinearLayout thumbnailANDtitleLayout = (LinearLayout) findViewById(R.id.thumbnailANDtitleLayout); // 레시피 게시물 상단 음식 완성과 제목
        Button shopping_btn = (Button) findViewById(R.id.shopping_btn);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ArrayList<String> contentsList = postInfo.getContents();
        String contentsList2 = postInfo.getProfile();
        String contentsList3 = postInfo.getTitle();

        // 레시피 제목과 요리 완성 이미지
        if(thumbnailANDtitleLayout.getTag() == null || !thumbnailANDtitleLayout.getTag().equals(contentsList)){
            thumbnailANDtitleLayout.setTag(contentsList2);  // thumbnailANDtitleLayout에 contentsList 정보 담기
            thumbnailANDtitleLayout.setTag(contentsList3);  // thumbnailANDtitleLayout에 contentsList 정보 담기
            thumbnailANDtitleLayout.removeAllViews(); // FrameLayout에 포함된 모든 자식(Children) 뷰 제거

            ImageView MainIamge = new ImageView(this);
            MainIamge.setLayoutParams(layoutParams);
            MainIamge.setAdjustViewBounds(true);
            MainIamge.setScaleType(ImageView.ScaleType.FIT_XY); // 이미지가 꽉차게 보임
            thumbnailANDtitleLayout.addView(MainIamge);
            Glide.with(this).load(contentsList2).override(1000).thumbnail(0.1f).into(MainIamge); // 이미지 로딩

            TextView MainText = new TextView(this);
            int size = 40;
            MainText.setTextSize(size);
            MainText.setLayoutParams(layoutParams);
            MainText.setGravity(Gravity.CENTER); // 제목 가운데 정렬
            MainText.setText(contentsList3);
            thumbnailANDtitleLayout.addView(MainText);
        }

        int a = 1; // 방법1 방법2....

        // 레시피 UI중 제목 이후의 레시피 내용
        if(contentsLayout.getTag() == null || !contentsLayout.getTag().equals(contentsList)){
            contentsLayout.setTag(contentsList);  // contentsLayout에 contentsList 정보 담기
            contentsLayout.removeAllViews(); // FrameLayout에 포함된 모든 자식(Children) 뷰 제거
            for (int i = 0; i < contentsList.size() ; i++) {
                String contents = contentsList.get(i); // i번째의 정보

                    if (Patterns.WEB_URL.matcher(contents).matches()) { // contents가 URL 형식이라면
                        String procedurePattern = "방법" + (a++);
                        TextView procedureText = new TextView(this); // 방법1 방법2....
                        procedureText.setLayoutParams(layoutParams);
                        procedureText.setPadding(0,50 ,0,0);
                        procedureText.setText(procedurePattern);
                        contentsLayout.addView(procedureText);

                        ImageView imageView = new ImageView(this);
                        imageView.setLayoutParams(layoutParams);
                        imageView.setAdjustViewBounds(true);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY); // 이미지가 꽉차게 보임
                        contentsLayout.addView(imageView);
                        Glide.with(this).load(contents).override(1000).thumbnail(0.1f).into(imageView); // 이미지 로딩
                    } else { // contents가 URL 형식이 아니라면
                        TextView textView = new TextView(this);
                        String contents2 = contentsList.get(i);
                        textView.setLayoutParams(layoutParams);
                        textView.setText(contents2);
                        contentsLayout.addView(textView);
                    }
            }
        }

        shopping_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity2.this, material_push.class);
                startActivity(intent);
            }
        });
    }
}

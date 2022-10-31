package com.example.newrepbook;

import static com.example.newrepbook.Util.isStorageUrl;

import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PostActivity extends BasicActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        PostInfo2 postInfo = (PostInfo2) getIntent().getSerializableExtra("postInfo2");
        TextView titletextView = findViewById(R.id.titletextView);
        titletextView.setText(postInfo.getTitle());

        LinearLayout contentsLayout = (LinearLayout) findViewById(R.id.contentsLayout);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ArrayList<String> contentsList = postInfo.getContents();

        TextView createdAtTextView = findViewById(R.id.createdAtTextView);
        createdAtTextView.setText(postInfo.getPublisher());

        if(contentsLayout.getTag() == null || !contentsLayout.getTag().equals(contentsList)){
            contentsLayout.setTag(contentsList);  // contentsLayout에 contentsList 정보 담기
            contentsLayout.removeAllViews(); // FrameLayout에 포함된 모든 자식(Children) 뷰 제거

            for (int i = 0; i < contentsList.size() ; i++){
                String contents = contentsList.get(i); // i번째의 정보
                if(Patterns.WEB_URL.matcher(contents).matches()){ // contents가 URL 형식이라면
                    ImageView imageView = new ImageView(this);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setAdjustViewBounds(true);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY); // 이미지가 꽉차게 보임
                    contentsLayout.addView(imageView);
                    Glide.with(this).load(contents).override(1000).thumbnail(0.1f).into(imageView); // 이미지 로딩
                }else { // contents가 URL 형식이 아니라면
                    TextView textView = new TextView(this);
                    String contents2 = contentsList.get(1);
                    if(!contentsList.get(i).equals(contentsList.get(1))){ // contentsList의 정보중 contentsList.get(1)의 값이 같지 않다면
                        textView.setLayoutParams(layoutParams);
                        textView.setText(contents);
                        contentsLayout.addView(textView);
                    } else { // contentsList 2번째 값(제목) 출력을 위한 코드
                        int size = 40;
                        textView.setTextSize(size);
                        textView.setLayoutParams(layoutParams);
                        textView.setGravity(Gravity.CENTER); // 제목 가운데 정렬
                        textView.setText(contents2);
                        contentsLayout.addView(textView);
                    }
                }
            }
        }
    }
}

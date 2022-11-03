package com.example.newrepbook;

import static com.android.volley.VolleyLog.TAG;
import static com.example.newrepbook.Util.isStorageUrl;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

public class PostActivity extends BasicActivity {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        PostInfo2 postInfo = (PostInfo2) getIntent().getSerializableExtra("postInfo2");

        LinearLayout contentsLayout = (LinearLayout) findViewById(R.id.contentsLayout); // 레시피 내용
        LinearLayout thumbnailANDtitleLayout = (LinearLayout) findViewById(R.id.thumbnailANDtitleLayout); // 레시피 게시물 상단 음식 완성과 제목

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
    }
}

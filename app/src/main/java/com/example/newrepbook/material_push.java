package com.example.newrepbook;

import static com.android.volley.VolleyLog.TAG;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class material_push extends BasicActivity {
    private static final String TAG = "material_push";
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    private FirebaseUser firebaseUser2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchasing_materials);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.e("로그 : ", "데이터 : " + FirebaseAuth.getInstance().getCurrentUser().getUid());

        if (firebaseUser != null) { // 유저가 존재한다면
            firebaseFirestore.collection("materias").get() // collectionGroup으로 상위 콜렉션 밑에 하위 콜렉션을 찾을 수 있음
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) { // 성공적이면
                                ArrayList<material_info> material_info1 = new ArrayList<>();  // 글에 대한 정보를
                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    Log.d(TAG, document.getId() + " => " + document.getData()); // posts 저장된 정보 출력
                                    material_info1.add(new material_info( // postinfo에 저장되어 있는 정보들을 postList에 넣는다
                                            document.getData().get("material_name").toString()
                                    ));
                                }
                                RecyclerView material_List = findViewById(R.id.material_List);
                                material_List.setHasFixedSize(true);
                                material_List.setLayoutManager(new LinearLayoutManager(material_push.this)); // 아이템 뷰가 나열되는 형태를 관리하기 위한 요소를 제공하는데, 이를 레이아웃매니저라고 함
                                RecyclerView.Adapter mAdapter = new material_pushAdapter(material_push.this, material_info1);
                                material_List.setAdapter(mAdapter);
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }
    protected void onResume() {
        super.onResume();
    }
}

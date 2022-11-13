package com.example.newrepbook;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class shopping_page extends AppCompatActivity {
    private static final String TAG = "shopping_page";
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_page);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) { // 유저가 존재한다면
            firebaseFirestore.collection("shopping").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) { // 성공적이면
                                ArrayList<shopping_pageInfo> shopping_pageInfo1 = new ArrayList<>();  // 글에 대한 정보를
                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    Log.d(TAG, document.getId() + " => " + document.getData()); // posts 저장된 정보 출력
                                    shopping_pageInfo1.add(new shopping_pageInfo( // postinfo에 저장되어 있는 정보들을 postList에 넣는다
                                            document.getData().get("food_name").toString(),
                                            document.getData().get("food_image").toString(),
                                            Integer.parseInt(String.valueOf (document.getData().get("food_price"))),
                                            document.getData().get("food_amount").toString()));
                                }
                                RecyclerView recyclerView = findViewById(R.id.recyclerView);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new GridLayoutManager(shopping_page.this,3)); // 아이템 뷰가 나열되는 형태를 관리하기 위한 요소를 제공하는데, 이를 레이아웃매니저라고 함
                                RecyclerView.Adapter mAdapter = new shopping_pageAdapter(shopping_page.this, shopping_pageInfo1);
                                recyclerView.setAdapter(mAdapter);
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

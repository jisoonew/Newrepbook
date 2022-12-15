package com.example.newrepbook;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    boolean isMenuOpen = false;
    boolean ishateOpen = false;
    Activity activity;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private shopping_basket_Adapter adapter;
    Animation tranlateLeftAnim;
    Animation tranlateRightAnim;
    LinearLayout menu, bookmark, shopping_basket;
    ImageView search_btn;
    Button menubtn, delete_button;
    RecyclerView shopping_basket_list;
    ArrayList<Shopping_basket_info> shopping_basket_arraylist, shopping_basket_arraylist2;
    ImageButton cancel, shopping_basket_cancel;
    FirebaseDatabase database;
    DatabaseReference databaseReference, databaseReference1;
    private static final String TAG = "listMainActivity";
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    final int sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_home);
        mAuth = FirebaseAuth.getInstance();

//        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
//        ViewPager pager2 = (ViewPager) findViewById(R.id.viewpager2);

        Button btn_first = (Button) findViewById(R.id.btn_first);     // 홈 버튼
        Button btn_second = (Button) findViewById(R.id.btn_second);   // 레시피
//        Button btn_third = (Button) findViewById(R.id.btn_third);     // 랭킹
        Button shopping_button = (Button) findViewById(R.id.shopping_button); // 쇼핑

        Button tab_Item5 = (Button) findViewById(R.id.tab_Item5);
        Button tab_Item6 = (Button) findViewById(R.id.tab_Item6);
        Button tab_Item7 = (Button) findViewById(R.id.tab_Item7);

        search_btn = findViewById(R.id.search_img); // 검색 이미지

        findViewById(R.id.product_storage2_btn).setOnClickListener(onClickListener); // 장바구니
        findViewById(R.id.bookmark_btn).setOnClickListener(onClickListener); // 내가 본 레시피
        findViewById(R.id.bookmark_cancel).setOnClickListener(onClickListener); // 내가 본 레시피 취소
        findViewById(R.id.shopping_basket_cancel).setOnClickListener(onClickListener); // 장바구니 취소
        findViewById(R.id.shopping_button).setOnClickListener(onClickListener);

//        pager.setAdapter(new pagerAdapter(getSupportFragmentManager()));
//        pager.setCurrentItem(0);
//
//        View.OnClickListener movePageListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int tag = (int) view.getTag();
//                pager.setCurrentItem(tag);
//            }
//        };


//        btn_first.setOnClickListener(movePageListener);
//        btn_first.setTag(0);
//        btn_second.setOnClickListener(movePageListener); // 홈의 레시피
//        btn_second.setTag(1);
//
//
//        // 동그라미 보이기
//        pager2.setAdapter(new pagerAdapter2(getSupportFragmentManager()));
//        pager2.setCurrentItem(0);
//
//        View.OnClickListener movePageListener2 = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int tag = (int) view.getTag();
//                pager2.setCurrentItem(tag);
//            }
//        };

//        tab_Item5.setOnClickListener(movePageListener2);
//        tab_Item5.setTag(0);
//        tab_Item6.setOnClickListener(movePageListener2);
//        tab_Item6.setTag(1);
//        tab_Item7.setOnClickListener(movePageListener2);
//        tab_Item7.setTag(2);


        //메뉴 버튼을 누른 후

        menu = findViewById(R.id.menu);

        menu.bringToFront();  // 맨앞으로 보이기

        tranlateLeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        tranlateRightAnim = AnimationUtils.loadAnimation(this, R.anim.translate_right);

        SlidingPageAnimationListener animListener3 = new SlidingPageAnimationListener();

        tranlateLeftAnim.setAnimationListener(animListener3);
        tranlateRightAnim.setAnimationListener(animListener3);

        menubtn = findViewById(R.id.menubtn);

        menubtn.setOnClickListener(new View.OnClickListener() { // 홈의 메뉴 버튼 클릭 시
            @Override
            public void onClick(View v) {
                if (isMenuOpen) {
                    menu.startAnimation(tranlateRightAnim); // UI 오른쪽 방향으로 감추기
                    shopping_button.setVisibility(VISIBLE);
                } else {
                    menu.setVisibility(VISIBLE); // UI 생성
                    menu.startAnimation(tranlateLeftAnim); // 왼쪽 방향으로 등장
                    shopping_button.setVisibility(INVISIBLE);
                }
            }
        });


        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReMainActivity.class);
                startActivity(intent);
            }
        });


        cancel = findViewById(R.id.cancel);
        shopping_basket_cancel = findViewById(R.id.shopping_basket_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMenuOpen) {
                    menu.startAnimation(tranlateRightAnim); // UI 오른쪽 방향으로 감추기
                }
            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bookmark: //즐겨찾기 클릭
                    bookmark_click();
                    break;
//                case R.id.logout_btn: //로그아웃 누르면
//                    FirebaseAuth.getInstance().signOut();
//                    startMainActivity();
//                    break;
                case R.id.bookmark_cancel: //즐겨찾기 취소
                    bookmark_cancel();
                    break;
                case R.id.product_storage2_btn: // 장바구니 클릭
                    shopping_basket_click();
                    break;
                case R.id.shopping_basket_cancel: // 장바구니 취소
                    shopping_basket_cancel();
                    break;
                case R.id.shopping_button:
                    shopping_page();
                    break;
            }
        }
    };

    // 쇼핑 페이지
    private void shopping_page(){
        Intent intent = new Intent(this, shopping_page.class);
        startActivity(intent);
    }

    // 즐겨찾기 버튼 누른 후
    private void bookmark_click() {
        bookmark = findViewById(R.id.bookmark);
        bookmark.bringToFront();  // 맨앞으로 보이기
        bookmark.setVisibility(VISIBLE);
    }




    // 즐겨찾기 취소 버튼
    private void bookmark_cancel() {
        bookmark.setVisibility(INVISIBLE); // UI 오른쪽 방향으로 감추기
    }

    // 장바구니 버튼 누른 후
    private void shopping_basket_click() {

        shopping_basket_list= findViewById(R.id.shopping_basket_recycler); // 장바구니 리사이클러 뷰
        shopping_basket_list.setHasFixedSize(true);
        shopping_basket_list.setLayoutManager(new LinearLayoutManager(this));
        shopping_basket_arraylist = new ArrayList<>();

        TextView shopping_total_price = (TextView) findViewById(R.id.shopping_total_price); // 총 상품 가격
        TextView delivery_charge_fee = (TextView) findViewById(R.id.delivery_charge_fee); // 총 배송비
        TextView shopping_fix_price = (TextView) findViewById(R.id.shopping_fix_price); // 총 결제 예상금액

        databaseReference1 = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("buy");
//        databaseReference.child("Users").child(user.getUid()).child("buy").push().setValue(hashMap);

        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shopping_basket_arraylist.clear(); // 기존 배열리스트가 존재하지 않게 초기화
                Integer total = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //반복문으로 데이터 리스트를 추출
                    Shopping_basket_info shopping_ = snapshot.getValue(Shopping_basket_info.class); // User 객체에 데이터 담는다
                    Integer cost = Integer.valueOf(shopping_.getShopping_price());
                    total = total + cost;
                    shopping_basket_arraylist.add(shopping_); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                adapter = new shopping_basket_Adapter(MainActivity.this, shopping_basket_arraylist);
                shopping_basket_list.setAdapter(adapter);
                shopping_total_price.setText(total+"");
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        shopping_basket = findViewById(R.id.shopping_basket);
        shopping_basket.bringToFront();  // 맨앞으로 보이기
        shopping_basket.setVisibility(VISIBLE);

    }

    // 장바구니 취소 버튼
    private void shopping_basket_cancel() {
        shopping_basket.setVisibility(INVISIBLE); // UI 오른쪽 방향으로 감추기
    }


    // 다시 로그인 페이지로 돌아가기
    private void startMainActivity() {
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
    }

    // 다시 로그인 페이지로 돌아가기
    private void startAddPostActivity() {
        Intent intent = new Intent(this, AddPostActivity.class);
        startActivity(intent);
    }



    private class SlidingPageAnimationListener implements Animation.AnimationListener{
        @Override
        public void onAnimationStart(Animation animation) {

        }

        public void onAnimationEnd(Animation animation){

            if (isMenuOpen){
                menu.setVisibility(INVISIBLE);

                isMenuOpen = false;
            }
            else{
                isMenuOpen = true;
            }
        }
        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }



}
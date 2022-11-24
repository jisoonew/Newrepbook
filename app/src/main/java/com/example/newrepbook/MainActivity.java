package com.example.newrepbook;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    boolean isMenuOpen = false;
    boolean ishateOpen = false;
    Animation tranlateLeftAnim;
    Animation tranlateRightAnim;
    LinearLayout menu, bookmark, shopping_basket;
    Button menubtn,product_storage2_btn ;
    RecyclerView shopping_basket_list;
    ArrayList<shopping_basket_info> shopping_basket_arraylist;
    ImageButton cancel, shopping_basket_cancel;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_home);
        mAuth = FirebaseAuth.getInstance();

        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        ViewPager pager2 = (ViewPager) findViewById(R.id.viewpager2);

        Button btn_first = (Button) findViewById(R.id.btn_first);     // 홈 버튼
        Button btn_second = (Button) findViewById(R.id.btn_second);   // 레시피
        Button btn_third = (Button) findViewById(R.id.btn_third);     // 랭킹

        Button tab_Item5 = (Button) findViewById(R.id.tab_Item5);
        Button tab_Item6 = (Button) findViewById(R.id.tab_Item6);
        Button tab_Item7 = (Button) findViewById(R.id.tab_Item7);

        shopping_basket_list= findViewById(R.id.shopping_basket_list);
        shopping_basket_list.setHasFixedSize(true);
        shopping_basket_arraylist = new ArrayList<>();



        findViewById(R.id.product_storage2_btn).setOnClickListener(onClickListener); // 장바구니
        findViewById(R.id.bookmark_btn).setOnClickListener(onClickListener); // 내가 본 레시피
        findViewById(R.id.bookmark_cancel).setOnClickListener(onClickListener); // 내가 본 레시피 취소
        findViewById(R.id.shopping_basket_cancel).setOnClickListener(onClickListener); // 장바구니 취소

        pager.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        pager.setCurrentItem(0);

        View.OnClickListener movePageListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) view.getTag();
                pager.setCurrentItem(tag);
            }
        };

        View.OnClickListener movePageListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) view.getTag();
                pager.setCurrentItem(tag);
            }
        };

        btn_first.setOnClickListener(movePageListener);
        btn_first.setTag(0);
        btn_second.setOnClickListener(movePageListener1);
        btn_second.setTag(1);
        btn_third.setOnClickListener(movePageListener);
        btn_third.setTag(2);


        // 동그라미 보이기
        pager2.setAdapter(new pagerAdapter2(getSupportFragmentManager()));
        pager2.setCurrentItem(0);

        View.OnClickListener movePageListener2 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) view.getTag();
                pager2.setCurrentItem(tag);
            }
        };

        tab_Item5.setOnClickListener(movePageListener2);
        tab_Item5.setTag(0);
        tab_Item6.setOnClickListener(movePageListener2);
        tab_Item6.setTag(1);
        tab_Item7.setOnClickListener(movePageListener2);
        tab_Item7.setTag(2);


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
                } else {
                    menu.setVisibility(VISIBLE); // UI 생성
                    menu.startAnimation(tranlateLeftAnim); // 왼쪽 방향으로 등장
                }
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



//// 내가 본 레시피 버튼 누른 후

        LinearLayout identify_food = findViewById(R.id.identify_food);

        identify_food.bringToFront();  // 맨앞으로 보이기

        Button identift_food_btn = (Button) findViewById(R.id.identify_food_btn);

        identift_food_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                identify_food.setVisibility(VISIBLE);
            }
        });


//// 내가 본 레시피 UI의 취소 버튼

        ImageButton identify_food_cancel = findViewById(R.id.identify_food_cancel);

        identify_food_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                identify_food.setVisibility(INVISIBLE); // UI 오른쪽 방향으로 감추기

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
            }
        }
    };

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
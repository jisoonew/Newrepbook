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

        Button btn_first = (Button) findViewById(R.id.btn_first);     // ??? ??????
        Button btn_second = (Button) findViewById(R.id.btn_second);   // ?????????
//        Button btn_third = (Button) findViewById(R.id.btn_third);     // ??????
        Button shopping_button = (Button) findViewById(R.id.shopping_button); // ??????

        Button tab_Item5 = (Button) findViewById(R.id.tab_Item5);
        Button tab_Item6 = (Button) findViewById(R.id.tab_Item6);
        Button tab_Item7 = (Button) findViewById(R.id.tab_Item7);

        search_btn = findViewById(R.id.search_img); // ?????? ?????????

        findViewById(R.id.product_storage2_btn).setOnClickListener(onClickListener); // ????????????
        findViewById(R.id.bookmark_btn).setOnClickListener(onClickListener); // ?????? ??? ?????????
        findViewById(R.id.bookmark_cancel).setOnClickListener(onClickListener); // ?????? ??? ????????? ??????
        findViewById(R.id.shopping_basket_cancel).setOnClickListener(onClickListener); // ???????????? ??????
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
//        btn_second.setOnClickListener(movePageListener); // ?????? ?????????
//        btn_second.setTag(1);
//
//
//        // ???????????? ?????????
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


        //?????? ????????? ?????? ???

        menu = findViewById(R.id.menu);

        menu.bringToFront();  // ???????????? ?????????

        tranlateLeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        tranlateRightAnim = AnimationUtils.loadAnimation(this, R.anim.translate_right);

        SlidingPageAnimationListener animListener3 = new SlidingPageAnimationListener();

        tranlateLeftAnim.setAnimationListener(animListener3);
        tranlateRightAnim.setAnimationListener(animListener3);

        menubtn = findViewById(R.id.menubtn);

        menubtn.setOnClickListener(new View.OnClickListener() { // ?????? ?????? ?????? ?????? ???
            @Override
            public void onClick(View v) {
                if (isMenuOpen) {
                    menu.startAnimation(tranlateRightAnim); // UI ????????? ???????????? ?????????
                    shopping_button.setVisibility(VISIBLE);
                } else {
                    menu.setVisibility(VISIBLE); // UI ??????
                    menu.startAnimation(tranlateLeftAnim); // ?????? ???????????? ??????
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
                    menu.startAnimation(tranlateRightAnim); // UI ????????? ???????????? ?????????
                }
            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bookmark: //???????????? ??????
                    bookmark_click();
                    break;
//                case R.id.logout_btn: //???????????? ?????????
//                    FirebaseAuth.getInstance().signOut();
//                    startMainActivity();
//                    break;
                case R.id.bookmark_cancel: //???????????? ??????
                    bookmark_cancel();
                    break;
                case R.id.product_storage2_btn: // ???????????? ??????
                    shopping_basket_click();
                    break;
                case R.id.shopping_basket_cancel: // ???????????? ??????
                    shopping_basket_cancel();
                    break;
                case R.id.shopping_button:
                    shopping_page();
                    break;
            }
        }
    };

    // ?????? ?????????
    private void shopping_page(){
        Intent intent = new Intent(this, shopping_page.class);
        startActivity(intent);
    }

    // ???????????? ?????? ?????? ???
    private void bookmark_click() {
        bookmark = findViewById(R.id.bookmark);
        bookmark.bringToFront();  // ???????????? ?????????
        bookmark.setVisibility(VISIBLE);
    }




    // ???????????? ?????? ??????
    private void bookmark_cancel() {
        bookmark.setVisibility(INVISIBLE); // UI ????????? ???????????? ?????????
    }

    // ???????????? ?????? ?????? ???
    private void shopping_basket_click() {

        shopping_basket_list= findViewById(R.id.shopping_basket_recycler); // ???????????? ??????????????? ???
        shopping_basket_list.setHasFixedSize(true);
        shopping_basket_list.setLayoutManager(new LinearLayoutManager(this));
        shopping_basket_arraylist = new ArrayList<>();

        TextView shopping_total_price = (TextView) findViewById(R.id.shopping_total_price); // ??? ?????? ??????
        TextView delivery_charge_fee = (TextView) findViewById(R.id.delivery_charge_fee); // ??? ?????????
        TextView shopping_fix_price = (TextView) findViewById(R.id.shopping_fix_price); // ??? ?????? ????????????

        databaseReference1 = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("buy");
//        databaseReference.child("Users").child(user.getUid()).child("buy").push().setValue(hashMap);

        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shopping_basket_arraylist.clear(); // ?????? ?????????????????? ???????????? ?????? ?????????
                Integer total = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //??????????????? ????????? ???????????? ??????
                    Shopping_basket_info shopping_ = snapshot.getValue(Shopping_basket_info.class); // User ????????? ????????? ?????????
                    Integer cost = Integer.valueOf(shopping_.getShopping_price());
                    total = total + cost;
                    shopping_basket_arraylist.add(shopping_); // ?????? ??????????????? ?????????????????? ?????? ????????????????????? ?????? ??????
                }
                adapter = new shopping_basket_Adapter(MainActivity.this, shopping_basket_arraylist);
                shopping_basket_list.setAdapter(adapter);
                shopping_total_price.setText(total+"");
                adapter.notifyDataSetChanged(); // ????????? ?????? ??? ????????????
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        shopping_basket = findViewById(R.id.shopping_basket);
        shopping_basket.bringToFront();  // ???????????? ?????????
        shopping_basket.setVisibility(VISIBLE);

    }

    // ???????????? ?????? ??????
    private void shopping_basket_cancel() {
        shopping_basket.setVisibility(INVISIBLE); // UI ????????? ???????????? ?????????
    }


    // ?????? ????????? ???????????? ????????????
    private void startMainActivity() {
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
    }

    // ?????? ????????? ???????????? ????????????
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
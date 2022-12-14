package com.example.newrepbook;

import static com.example.newrepbook.Util.GALLERY_IMAGE;
import static com.example.newrepbook.Util.GALLERY_VIDEO;
import static com.example.newrepbook.Util.INTENT_MEDIA;
import static com.example.newrepbook.Util.INTENT_PATH;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

public class AddPostActivity extends AppCompatActivity {
        private static final String TAG = "AddPostActivity";
        private FirebaseUser user;
        private ArrayList<String> pathList = new ArrayList<>();
        private LinearLayout parent;
        private RelativeLayout buttonsBackground;
        private ImageView selectedImageView;
        private EditText selectedEdit;
        private RelativeLayout loading;
        private int pathCount, successCount;
        private PostInfo postInfo;

        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpost);
        parent = findViewById(R.id.contentsLayout);

        loading = findViewById(R.id.loading);
        buttonsBackground = findViewById(R.id.buttonsBackground);

        buttonsBackground.setOnClickListener(onClickListener);
        findViewById(R.id.addpost_btn).setOnClickListener(onClickListener);
        findViewById(R.id.video_addpost).setOnClickListener(onClickListener);
        findViewById(R.id.image_addpost).setOnClickListener(onClickListener);
        findViewById(R.id.imageModify).setOnClickListener(onClickListener);
        findViewById(R.id.videoModify).setOnClickListener(onClickListener);
        findViewById(R.id.delete).setOnClickListener(onClickListener);
        findViewById(R.id.contents_Edit).setOnFocusChangeListener(onFocusChangeListener);
        findViewById(R.id.title_edit).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    selectedEdit = null;
                }
            }
        });

            postInfo = (PostInfo) getIntent().getSerializableExtra("postInfo");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 0 :
                if (resultCode == Activity.RESULT_OK) {
                    String profilePath = data.getStringExtra(INTENT_PATH);
                    pathList.add(profilePath);

                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    LinearLayout linearLayout = new LinearLayout(AddPostActivity.this);
                    linearLayout.setLayoutParams(layoutParams);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);

                    if(selectedEdit == null){
                        parent.addView(linearLayout);
                    } else {
                        for (int i = 0; i < parent.getChildCount(); i++) {
                            if (parent.getChildAt(i) == selectedEdit.getParent()) {
                                parent.addView(linearLayout, i + 1);
                                break;
                            }
                        }
                    }
//                    parent.addView(linearLayout);
                    ImageView imageView = new ImageView(AddPostActivity.this);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonsBackground.setVisibility(View.VISIBLE);
                            selectedImageView = (ImageView) v;
                        }
                    });
                    Glide.with(this).load(profilePath).override(1000).into(imageView);
                    linearLayout.addView(imageView);

                    EditText editText = new EditText(AddPostActivity.this);
                    editText.setLayoutParams(layoutParams);
                    editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_CLASS_TEXT);
                    editText.setHint("??????");
                    editText.setOnFocusChangeListener(onFocusChangeListener);
                    linearLayout.addView(editText);
                }
                break;
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    String profilePath = data.getStringExtra(INTENT_PATH);
                    Glide.with(this).load(profilePath).override(1000).into(selectedImageView);
                }
                break;
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.addpost_btn: // ????????? ?????? ??????
                    storageUpdate();
                break;
                case R.id.video_addpost: // ???????????? ????????? ??????
                    myStartActivity(GalleryActivity.class, GALLERY_VIDEO, 0);
                    break;
                case R.id.image_addpost: // ???????????? ????????? ??????
                    myStartActivity(GalleryActivity.class, GALLERY_IMAGE, 0);
                    break;
                case R.id.buttonsBackground:
                if(buttonsBackground.getVisibility() == View.VISIBLE){
                        buttonsBackground.setVisibility(View.GONE);
                    }
                    break;
                case R.id.imageModify:
                    myStartActivity(GalleryActivity.class, GALLERY_IMAGE, 1);
                    buttonsBackground.setVisibility(View.GONE);
                    break;
                case R.id.videoModify:
                    myStartActivity(GalleryActivity.class, GALLERY_VIDEO, 1);
                    buttonsBackground.setVisibility(View.GONE);
                    break;
                case R.id.delete:
                    parent.removeView((View)selectedImageView.getParent());
                    buttonsBackground.setVisibility(View.GONE);
                    break;
            }
        }
    };

        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                            selectedEdit = (EditText) v;
                }
            }
        };

    private void storageUpdate() {
        final String title = ((EditText) findViewById(R.id.title_edit)).getText().toString();

        if(title.length() > 0){
//            loading.setVisibility(View.VISIBLE);
            final ArrayList<String> contentsList = new ArrayList<>();
            user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            final DocumentReference documentReference = postInfo == null ? firebaseFirestore.collection("posts").document() : firebaseFirestore.collection("posts").document(user.getUid());

            for (int i = 0; i < parent.getChildCount(); i++){
                LinearLayout linearLayout = (LinearLayout)parent.getChildAt(i);
                for (int ii = 0; ii <linearLayout.getChildCount() ; ii++){
                    View view = linearLayout.getChildAt(ii);
                    if(view instanceof EditText){
                        String text = ((EditText)view).getText().toString();
                        if(text.length() > 0){
                            contentsList.add(text);
                        }
                    } else {
                        contentsList.add(pathList.get(pathCount));
                        String[] pastArray = pathList.get(pathCount).split("\\.");
                        final StorageReference mountainImageRef = storageRef.child("posts/" + documentReference.getId() + "/"+pathCount+pastArray[pastArray.length-1]);

                        try{
                            InputStream stream = new FileInputStream(new File(pathList.get(pathCount)));

                            StorageMetadata metadata = new StorageMetadata.Builder().setCustomMetadata("index",""+(contentsList.size()-1)).build();

                            UploadTask uploadTask = mountainImageRef.putStream(stream, metadata);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                public void onFailure(@NonNull Exception exception) {

                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    final int index = Integer.parseInt(taskSnapshot.getMetadata().getCustomMetadata("index"));

                                    mountainImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        public void onSuccess(Uri uri){
                                            contentsList.set(index, uri.toString());
                                            successCount++;
                                            if(pathList.size() == successCount){
                                                // ??????
                                                PostInfo postInfo = new PostInfo(title, contentsList, user.getUid(), new Date());
                                                storeuploader(documentReference, postInfo);
                                                for (int a = 0; a < contentsList.size(); a++){
                                                    Log.e("??????: ", "?????????"+contentsList.get(a));
                                                }
                                            }
                                        }
                                    });
//                                contentsList.set(index, uri);
                                }
                            });
                        } catch (FileNotFoundException e){
                            Log.e("??????", "??????: " + e.toString());
                        }
                        pathCount++;
                    }
                }

            }
            if(pathList.size() == 0){
                PostInfo postInfo = new PostInfo(title, contentsList, user.getUid(), new Date());
                storeuploader(documentReference, postInfo);
            }
        } else {
            startToast("????????? ??????????????????");
        }
    }

    public void storeuploader(DocumentReference documentReference, PostInfo postInfo){
        documentReference.set(postInfo)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d(TAG, "???????????? ?????????");
//                    loading.setVisibility(View.GONE);
                    startActivity(listMainActivity.class);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "?????? ?????????", e);
//                    loading.setVisibility(View.GONE);
                }
            });
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void myStartActivity(Class c, int media, int requestCode) {
        Intent intent = new Intent(this, c);
        intent.putExtra(INTENT_MEDIA, media);
        startActivityForResult(intent, requestCode);
    }

    private void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}

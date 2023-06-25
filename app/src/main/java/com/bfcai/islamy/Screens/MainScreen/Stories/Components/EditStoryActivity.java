package com.bfcai.islamy.Screens.MainScreen.Stories.Components;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bfcai.islamy.MainActivity;
import com.bfcai.islamy.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class EditStoryActivity extends AppCompatActivity {
    Intent data;
    FirebaseUser user;
    ImageView img;
    EditText title , des;
    Button btn;
    ProgressBar progressBar;
    Uri image_url =null;

    StorageReference storageReference;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    String userId;
    String storyId;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_story);

        data = getIntent();
        img = findViewById(R.id.editPostImage);
        title = findViewById(R.id.EditNoteTitle);
        des = findViewById(R.id.EditNoteDes);
        String notetitle = data.getStringExtra("title");
        String notecontent = data.getStringExtra("content");
        storyId = data.getStringExtra("id");

        title.setText(notetitle);
        des.setText(notecontent);
        Glide.with(getApplicationContext()).load(data.getStringExtra("image")).into(img);


        btn = findViewById(R.id.editPostBtn);
        progressBar = findViewById(R.id.editProgressBar);
        toolbar = findViewById(R.id.editPost_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Story");

        storageReference = FirebaseStorage.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(3,2)
                        .setMinCropResultSize(512,512)
                        .start(EditStoryActivity.this);

            }
        });

        btn.setOnClickListener(v -> {
            String Title = title.getText().toString();
            String Des = des.getText().toString();

            progressBar.setVisibility(View.VISIBLE);

            if(!Title.isEmpty() && image_url != null){
                StorageReference ref = storageReference.child("StoryImage").child("story/" + storyId +".jpg");
                ref.putFile(image_url).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                                @Override
                                public void onSuccess(Uri uri) {

                                    HashMap<String,Object> map = new HashMap<>();
                                    map.put("image",uri.toString());
                                    map.put("title",Title);
                                    map.put("des",Des);
                                    map.put("date", FieldValue.serverTimestamp());
                                    DocumentReference reference = firestore.collection("Stories").document(storyId);

                                    reference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(EditStoryActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(EditStoryActivity.this, "Error, Try again.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(EditStoryActivity.this, task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                HashMap<String,Object> map = new HashMap<>();
                map.put("image", data.getStringExtra("image"));
                map.put("title",Title);
                map.put("des",Des);
                map.put("date", FieldValue.serverTimestamp());
                DocumentReference reference = firestore.collection("Stories").document(storyId);

                reference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditStoryActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditStoryActivity.this, "Error, Try again.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if( resultCode == RESULT_OK){
                image_url = result.getUri();

                    img.setImageURI(image_url);

            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Toast.makeText(getBaseContext(),result.getError().toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(),MainActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
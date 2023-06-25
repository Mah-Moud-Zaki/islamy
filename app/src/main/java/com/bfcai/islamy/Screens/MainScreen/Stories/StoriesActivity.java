package com.bfcai.islamy.Screens.MainScreen.Stories;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.bfcai.islamy.Adapter.StoryAdapter;
import com.bfcai.islamy.MainActivity;
import com.bfcai.islamy.Model.Story;
import com.bfcai.islamy.R;
import com.bfcai.islamy.Screens.MainScreen.Stories.Components.addPostActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StoriesActivity extends AppCompatActivity {
    FloatingActionButton fab;
    FirebaseAuth auth;
    String user;
    Toolbar toolbar;
    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    StoryAdapter adapter;
    List<Story> storyList;
    Query query;
    ListenerRegistration listenerRegistration;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);
        fab = findViewById(R.id.fab);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser().getUid();
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("قصص اسلامية");
        progressBar = findViewById(R.id.storyProgress);

        recyclerView = findViewById(R.id.storyRecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(StoriesActivity.this));
        storyList = new ArrayList<>();
        adapter = new StoryAdapter(storyList,StoriesActivity.this);
        recyclerView.setAdapter(adapter);

        /*if(user.equals("imwiknRNOcMpaBmOSelNSzMKaGw2")) {
            fab.setVisibility(View.VISIBLE);
        }*/
        fab.setVisibility(View.VISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), addPostActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
        query = firestore.collection("Stories").orderBy("date", Query.Direction.DESCENDING);
        listenerRegistration = query.addSnapshotListener(StoriesActivity.this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange doc : value.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        String storyId = doc.getDocument().getId();

                        Story story = doc.getDocument().toObject(Story.class).withId(storyId);
                        storyList.add(story);
                        progressBar.setVisibility(View.INVISIBLE);
                        adapter.notifyDataSetChanged();
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }
    @Override
    public void onBackPressed(){

          startActivity(new Intent(getBaseContext(), MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
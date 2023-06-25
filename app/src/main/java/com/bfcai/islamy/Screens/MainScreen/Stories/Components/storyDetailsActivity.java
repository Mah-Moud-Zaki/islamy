package com.bfcai.islamy.Screens.MainScreen.Stories.Components;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bfcai.islamy.MainActivity;
import com.bfcai.islamy.R;
import com.bfcai.islamy.Screens.MainScreen.Stories.StoriesActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class storyDetailsActivity extends AppCompatActivity {
    Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_details);
        Toolbar toolbar = findViewById(R.id.storyToolbar);
        setSupportActionBar(toolbar);
        data = getIntent();
        TextView title = findViewById(R.id.noteTitleContent);
        TextView content = findViewById(R.id.noteDetailsContent);
        ImageView imageView = findViewById(R.id.imagePost);
        content.setText(data.getStringExtra("des"));
        title.setText(data.getStringExtra("title"));
       // imageView.setImage("image");
        Glide.with(getApplicationContext()).load(data.getStringExtra("image")).into(imageView);

        FloatingActionButton fab = findViewById(R.id.fabDetails);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), EditStoryActivity.class);
                i.putExtra("title",data.getStringExtra("title"));
                i.putExtra("content",data.getStringExtra("des"));
                i.putExtra("image",data.getStringExtra("image"));
                i.putExtra("id",data.getStringExtra("id"));
                startActivity(i);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
        @Override
        public void onBackPressed() {
            super.onBackPressed();
            Intent intent=new Intent(getApplicationContext(), StoriesActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        }

}
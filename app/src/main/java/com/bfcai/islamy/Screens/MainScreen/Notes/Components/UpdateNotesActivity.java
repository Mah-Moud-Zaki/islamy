package com.bfcai.islamy.Screens.MainScreen.Notes.Components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bfcai.islamy.Database.NoteDatabase;
import com.bfcai.islamy.MainActivity;
import com.bfcai.islamy.R;
import com.bfcai.islamy.Screens.MainScreen.Notes.NoteHomeActivity;
import com.google.android.material.textfield.TextInputEditText;

public class UpdateNotesActivity extends AppCompatActivity {
    TextInputEditText title , description;
    Button updateNotes;
    String id;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notes);
        toolbar = findViewById(R.id.updateNote_toolbar);
        setSupportActionBar(toolbar);
        title = findViewById(R.id.update_notes_et_title);
        description = findViewById(R.id.update_notes_et_description);

        updateNotes = findViewById(R.id.update_notes_btn);

        Intent intent=getIntent();
        title.setText(intent.getStringExtra("title"));
        description.setText(intent.getStringExtra("description"));
        id = intent.getStringExtra("id");

        //we will only update notes when both fields contains text
        updateNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description.getText().toString())){

                    NoteDatabase db = new NoteDatabase(UpdateNotesActivity.this);
                    db.updateNotes(title.getText().toString(),description.getText().toString(),id);

                    Intent i =new Intent(UpdateNotesActivity.this, NoteHomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }
                else //is empty
                {
                    Toast.makeText(UpdateNotesActivity.this, "Both Fields Required", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(), NoteHomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }
}
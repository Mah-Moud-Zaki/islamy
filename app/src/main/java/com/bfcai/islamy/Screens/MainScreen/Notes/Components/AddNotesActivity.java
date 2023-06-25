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
import com.bfcai.islamy.R;
import com.bfcai.islamy.Screens.MainScreen.Notes.NoteHomeActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddNotesActivity extends AppCompatActivity {
    TextInputEditText title,description;
    Button addNote;
    Toolbar toolbar;
    NoteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        toolbar = findViewById(R.id.addNote_toolbar);
        setSupportActionBar(toolbar);
        title = findViewById(R.id.add_notes_et_title);
        description = findViewById(R.id.add_notes_et_description);

        addNote = findViewById(R.id.add_notes_btn);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description.getText().toString())) //will be true only when both of the fields will be non-empty
                {
                    db = new NoteDatabase(AddNotesActivity.this);
                    db.addNotes(title.getText().toString(),description.getText().toString());

                    Intent intent = new Intent(AddNotesActivity.this, NoteHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }
                else // false
                {
                    Toast.makeText(AddNotesActivity.this,"Both Fields Required",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
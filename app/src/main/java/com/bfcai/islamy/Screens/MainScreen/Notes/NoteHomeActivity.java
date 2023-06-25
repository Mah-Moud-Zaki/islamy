package com.bfcai.islamy.Screens.MainScreen.Notes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bfcai.islamy.Adapter.NoteAdapter;
import com.bfcai.islamy.Database.NoteDatabase;
import com.bfcai.islamy.MainActivity;
import com.bfcai.islamy.Model.Notes;
import com.bfcai.islamy.R;
import com.bfcai.islamy.Screens.MainScreen.Notes.Components.AddNotesActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class NoteHomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    NoteAdapter adapter;
    ArrayList<Notes> notesList;
    NoteDatabase db;
    CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_home);

        coordinatorLayout =findViewById(R.id.layout_main);
        recyclerView = findViewById(R.id.noteRecycle);
        fab = findViewById(R.id.note_fab);

        Toolbar toolbar = findViewById(R.id.toll_bar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(v-> {
                Intent intent=new Intent(NoteHomeActivity.this, AddNotesActivity.class);
                startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        });


        notesList = new ArrayList<>();

        db = new NoteDatabase(this);
        //create method fetching notes from database
        fetchAllNotesFromDatabase();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new NoteAdapter(this, NoteHomeActivity.this,notesList);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper helper =new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        ItemTouchHelper itemTouchHelper =new ItemTouchHelper(edit);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    void fetchAllNotesFromDatabase(){//now will get notes from database
        Cursor cursor = db.readAllData();

        if(cursor.getCount()==0)//it means there is no data
        {
            Toast.makeText(NoteHomeActivity.this,"No data to show",Toast.LENGTH_LONG).show();
        }
        else
        {
            while ((cursor.moveToNext()))
            {
                notesList.add(new Notes(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu,menu);


        MenuItem search = menu.findItem(R.id.searchbar);
        SearchView searchView = (SearchView) search.getActionView();

        SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() { // معني كلمةQuery هو النص المكتوب في الشريط
            @Override
            public boolean onQueryTextSubmit(String query) {//دا لو انا عايز يعمل بحث لما المستخدم يكتب الجملة كاملة ويدوس submit
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {//دا لو هيعمل بحث مع كل حرف بيتكتب ويتغير
                adapter.getFilter().filter(newText);
                return true;
            }
        };

        searchView.setOnQueryTextListener(listener);// دا listener بيعمل مؤشر على نص الكتابة (الاستعلام) وممكن كنت اعمله فوق واخط جواه فانكشن new SearchView.OnQueryTextListener()

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {



        if(item.getItemId()==R.id.delete_all_notes)
        {
            deleteAllNotes();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllNotes(){
        NoteDatabase databaseClass =new NoteDatabase(NoteHomeActivity.this);
        databaseClass.deleteAllNotes();
        recreate();
    }


    ItemTouchHelper.SimpleCallback callback= new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            Notes item =adapter.getList().get(position);
            adapter.removeItem(position);

            Snackbar snackbar = Snackbar.make(coordinatorLayout,R.string.note_snackBar,Snackbar.LENGTH_LONG)
                    .setAction(R.string.note_undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {//this situation up when Snackbar is visible(means undo remove the item)
                            adapter.restoreItem(item,position);
                            recyclerView.scrollToPosition(position);
                        }
                    }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {// is non-visible(means remove the item)
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            super.onDismissed(transientBottomBar, event);

                            if(!(event==DISMISS_EVENT_ACTION))
                            {
                                NoteDatabase db = new NoteDatabase(NoteHomeActivity.this);
                                db.deleteSingleItem(item.getId());
                            }


                        }
                    });
            snackbar.setActionTextColor(Color.GREEN);
            snackbar.show();
        }
        public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){
            new RecyclerViewSwipeDecorator.Builder(NoteHomeActivity.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                    .addSwipeRightBackgroundColor(ContextCompat.getColor(NoteHomeActivity.this, android.R.color.holo_red_dark))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_24)
                    .setActionIconTint(ContextCompat.getColor(recyclerView.getContext(), android.R.color.white))
                    .addSwipeRightLabel("حذف")
                    .setSwipeRightLabelColor(Color.parseColor("#FFFFFF"))
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        }
    };
    ItemTouchHelper.SimpleCallback edit= new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            adapter.editItem(position);

        }

        public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){
            new RecyclerViewSwipeDecorator.Builder(NoteHomeActivity.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(NoteHomeActivity.this, R.color.purple_500))
                    .addSwipeLeftActionIcon(R.drawable.ic_edit)
                    .setActionIconTint(ContextCompat.getColor(recyclerView.getContext(), android.R.color.white))
                    .addSwipeLeftLabel("تعديل")
                    .setSwipeLeftLabelColor(Color.parseColor("#FFFFFF"))
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        }
    };

    @Override
    public void onBackPressed(){

        startActivity(new Intent(getBaseContext(), MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
package com.bfcai.islamy.Screens.MainScreen.Quran;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bfcai.islamy.MainActivity;
import com.bfcai.islamy.R;

import java.util.HashMap;

public class Qraan extends AppCompatActivity {
    ListView pdflistview;
    Toolbar toolbar;

    EditText theFilter;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qraan);


        pdflistview=findViewById(R.id.surah_name);
        theFilter =  findViewById(R.id.searchFilter);

        toolbar = findViewById(R.id.quranToolbar);
        setSupportActionBar(toolbar);
        String[]pdfFilenames={getString(R.string.qraan1),getString(R.string.qraan2),getString(R.string.qraan3),getString(R.string.qraan4),getString(R.string.qraan5),getString(R.string.qraan6),getString(R.string.qraan7),
                getString(R.string.qraan8),getString(R.string.qraan9),getString(R.string.qraan10),getString(R.string.qraan11),getString(R.string.qraan12),getString(R.string.qraan13),getString(R.string.qraan14),getString(R.string.qraan15),getString(R.string.qraan16),getString(R.string.qraan17),
                getString(R.string.qraan18),getString(R.string.qraan19),getString(R.string.qraan20)
               ,getString(R.string.qraan21),getString(R.string.qraan22),getString(R.string.qraan23),getString(R.string.qraan24),getString(R.string.qraan25),getString(R.string.qraan26),getString(R.string.qraan27),getString(R.string.qraan28),
                getString(R.string.qraan29),getString(R.string.qraan30),getString(R.string.qraan31),getString(R.string.qraan32),getString(R.string.qraan33),getString(R.string.qraan34),getString(R.string.qraan35),getString(R.string.qraan36),getString(R.string.qraan37)
                ,getString(R.string.qraan38),getString(R.string.qraan39),getString(R.string.qraan40),getString(R.string.qraan41),getString(R.string.qraan42),getString(R.string.qraan43),getString(R.string.qraan44),getString(R.string.qraan45),
                getString(R.string.qraan46),getString(R.string.qraan47),getString(R.string.qraan48),getString(R.string.qraan49)
                ,getString(R.string.qraan50),getString(R.string.qraan51),getString(R.string.qraan52),getString(R.string.qraan53),getString(R.string.qraan54),getString(R.string.qraan55),getString(R.string.qraan56),
                getString(R.string.qraan57),getString(R.string.qraan58),getString(R.string.qraan59),getString(R.string.qraan60),getString(R.string.qraan61),getString(R.string.qraan62),getString(R.string.qraan63),
                getString(R.string.qraan64),getString(R.string.qraan65),getString(R.string.qraan66),getString(R.string.qraan67),getString(R.string.qraan68),getString(R.string.qraan69),getString(R.string.qraan70),getString(R.string.qraan71)
                ,getString(R.string.qraan72),getString(R.string.qraan73),getString(R.string.qraan74),getString(R.string.qraan75),getString(R.string.qraan76),getString(R.string.qraan77),getString(R.string.qraan78),
                getString(R.string.qraan79),getString(R.string.qraan80),getString(R.string.qraan81),getString(R.string.qraan82),getString(R.string.qraan83),getString(R.string.qraan84),getString(R.string.qraan85)
                ,getString(R.string.qraan86),getString(R.string.qraan87),getString(R.string.qraan88),getString(R.string.qraan89),getString(R.string.qraan90),getString(R.string.qraan91),getString(R.string.qraan92),getString(R.string.qraan93)
                ,getString(R.string.qraan94),getString(R.string.qraan95),getString(R.string.qraan96),getString(R.string.qraan97),getString(R.string.qraan98),getString(R.string.qraan99),getString(R.string.qraan100),getString(R.string.qraan101)
                ,getString(R.string.qraan102),getString(R.string.qraan103),getString(R.string.qraan104),getString(R.string.qraan105),getString(R.string.qraan106),getString(R.string.qraan107),getString(R.string.qraan108)
                ,getString(R.string.qraan109),getString(R.string.qraan110),getString(R.string.qraan111),getString(R.string.qraan112),getString(R.string.qraan113),getString(R.string.qraan114)};

        // ده ميثود بنستخدمها عشان نشوف لو فيه تغيير في التيكست
        theFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (Qraan.this).adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        HashMap<String, String> addressMap = new HashMap<String, String>();
        adapter = new ArrayAdapter(this, R.layout.quran_listitem_layout, pdfFilenames);
        pdflistview.setAdapter(adapter);
        Animation translate_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_anim);
        pdflistview.setAnimation(translate_anim);
        pdflistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item=pdflistview.getItemAtPosition(i).toString();
                Intent intent=new Intent(getApplicationContext(),PdfOpner.class);
                intent.putExtra("item",item);
                intent.putExtra("num",i);

                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


            }
        });


    }
    @Override
    public void onBackPressed(){
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);


    }

}
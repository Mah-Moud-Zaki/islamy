package com.bfcai.islamy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bfcai.islamy.Screens.DrawerScreen.MalomatActivity;
import com.bfcai.islamy.Screens.DrawerScreen.TasabyhActivity;
import com.bfcai.islamy.Screens.DrawerScreen.adayaActivity;
import com.bfcai.islamy.Screens.MainScreen.SebhaActivity;
import com.bfcai.islamy.Screens.MainScreen.azkarActivity;
import com.bfcai.islamy.Screens.DrawerScreen.azkarElmasaa;
import com.bfcai.islamy.Screens.DrawerScreen.azkarElsabah;
import com.bfcai.islamy.Screens.MainScreen.Notes.NoteHomeActivity;
import com.bfcai.islamy.Screens.MainScreen.QiblahActivity;
import com.bfcai.islamy.Screens.MainScreen.Quran.Qraan;
import com.bfcai.islamy.Screens.MainScreen.Stories.StoriesActivity;

import com.bfcai.islamy.Service.FirebasePushNotificationClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FirebaseAuth auth;
    LinearLayout islamicStories,islamicNote,azkarPage,qraan,qiblahScreen,sebhaScreen;
    NavigationView navigationView ;
    ImageView imageView;

    //اسلامي

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // خاصين بمعرفة حالة اللغه و الوضع الليلي الذي اخترهم المستخدم
        loadLocale();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (loadMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            setTheme(R.style.darkTheme);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            setTheme(R.style.AppTheme);
        }

        Intent intentBackgroundService = new Intent(this, FirebasePushNotificationClass.class);
        startService(intentBackgroundService);
        // فانكشن خاصه بكود زر الوضع الليلي
        // تم عملها بهذا الشكل لان عند وضعها داخل onSelectedMenuItem يكون هناك Bug يمكن التجربه للتاكد
        darkModeButton();

        // تعريف العناصر

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        islamicStories = findViewById(R.id.islamicStories);
        islamicNote = findViewById(R.id.islamicNote);
        azkarPage = findViewById(R.id.azkarPage);
        auth = FirebaseAuth.getInstance();
        qraan=findViewById(R.id.Qraan);
        qiblahScreen = findViewById(R.id.qiblahScreen);
        sebhaScreen = findViewById(R.id.sebhaScreen);
        imageView=findViewById(R.id.imageView);

        // اكواد تعريف القائمه الجانبيه و العناصر بداخلها
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor( getResources().getColor(R.color.black));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

          // تسحيل دخول للمستخدم عند تثبيت التطبيق لاول مره فقط
        if(auth.getCurrentUser() == null){
            auth.signInAnonymously().addOnCompleteListener(task -> Toast.makeText(getBaseContext(),"مرحبا بك بتطبيق اسلامي",Toast.LENGTH_SHORT).show());
        }

        // الانتقال للصفحات بالصفحه الرئيسية
        qraan.setOnClickListener(v->{
            Intent intent=new Intent(this, Qraan.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        });
        islamicStories.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), StoriesActivity.class));
            finish();
        });
        islamicNote.setOnClickListener(v ->{
            startActivity(new Intent(getApplicationContext(), NoteHomeActivity.class));
            finish();
        });
        azkarPage.setOnClickListener(v ->{
            startActivity(new Intent(getApplicationContext(), azkarActivity.class));
            finish();
        });
        qiblahScreen.setOnClickListener(v ->{
            startActivity(new Intent(getApplicationContext(), QiblahActivity.class));
            finish();
        });
        sebhaScreen.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SebhaActivity.class));
            finish();
        });

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.adayaPage:
                startActivity(new Intent(getBaseContext(), adayaActivity.class));
                break;
            case R.id.tasabyhPage:
                startActivity(new Intent(getBaseContext(), TasabyhActivity.class));
                break;
            case R.id.azkarElsabahPage:
                startActivity(new Intent(getBaseContext(), azkarElsabah.class));
                break;
            case R.id.azkarElmasaaPage:
                startActivity(new Intent(getBaseContext(), azkarElmasaa.class));
                break;
            case R.id.malomatPage:
                startActivity(new Intent(getBaseContext(), MalomatActivity.class));
                break;
            case R.id.aboutApp:
                showDialogMenu();
                break;
            case R.id.nav_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "وَأَمَّا بِنِعْمَةِ رَبِّكَ فَحَدِّث , حمل الان تطبيق اسلامي و ستجد كل ما تحتاجه لاي مسلم  :  https://play.google.com/store/apps/details?id=com.bfcai.islamy ";
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                break;
            case R.id.nav_rate:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.bfcai.islamy")));
                break;


            case R.id.translate:
                showchangelanguagedialog();
                break;


        }
        return true;
    }


        // الديالوج الخاص ب زر عن التطبيق
    private void showDialogMenu() {
        Button btn;
        ViewGroup viewGroup = findViewById(android.R.id.content);
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_layout,viewGroup,false);
        btn = view.findViewById(R.id.dialog_btn);
        dialog.setCancelable(false);
        dialog.setView(view);

       final AlertDialog alert = dialog.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.show();
        btn.setOnClickListener(v-> alert.dismiss());
    }

    // الديالوج الذي يظهر عند الضغط علي زر الرجوع ( onBackPress = فانكشن اساسية تبع الاندرويد استوديو)
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            TextView title =  new TextView(getBaseContext());
            title.setText(R.string.closeProject);
            title.setGravity(Gravity.CENTER);
            title.setTextSize(25);
            title.setTextColor(getResources().getColor(R.color.text_color));

            new AlertDialog.Builder(this,R.style.AlertDialogCustom)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCustomTitle(title)
                    .setMessage(R.string.closeDialogDes)
                    .setPositiveButton(R.string.yes, (dialog, which) ->{
                            finishAffinity();
                    System.exit(0);
                    }).setNegativeButton(R.string.No,null).show();


        }
    }
    int selectedItems;
    String currentLang ;

    // الديالوج الخاص ب تغيير اللغه
    private void showchangelanguagedialog() {
        if(currentLang.equals("en")){
            selectedItems = 1;
        } else{
            selectedItems = 0;
        }
        final String[][] listitems = {{getString(R.string.arabic), getString(R.string.english)}};
        final AlertDialog.Builder mbuilder=new AlertDialog.Builder(this,R.style.AlertDialogCustom);
        TextView title =  new TextView(getBaseContext());
        title.setText(R.string.changeLanguage);
        title.setTextSize(25);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(getResources().getColor(R.color.text_color));
        mbuilder.setCustomTitle(title);
        mbuilder.setSingleChoiceItems(listitems[0], selectedItems, (dialog, i) -> {
            if (i == 0) {
                setLocale("ar");
                recreate();

            } else {
                setLocale("en");
                recreate();

            }
            dialog.dismiss();
        });
        AlertDialog malertDialog=mbuilder.create();
        malertDialog.show();
    }

    // كود تغيير اللغه والحفظ ب shared prefrences
    private void setLocale(String lang) {
        Locale locale=new Locale(lang);
        Locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor=getSharedPreferences("Setting",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();

    }

    // كود جلب اللغه من shared Prefrences
    private void loadLocale() {
        SharedPreferences preferences = getSharedPreferences("Setting", Activity.MODE_PRIVATE);
        String language = preferences.getString("My_Lang", "");
        setLocale(language);
        if(language.equals("ar")){
            currentLang = "ar";
        }else {
            currentLang = "en";
        }
    }

    // كود زر تغير اللغه
    private void darkModeButton() {
        navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.darkMode);
        menuItem.setActionView(R.layout.theme_switch);
        final SwitchCompat  themeSwitch = (SwitchCompat) menuItem.getActionView().findViewById(R.id.action_switch);
        if (loadMode()) {
            themeSwitch.setChecked(true);
        }
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                    saveMode(true);
                    recreate();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                    saveMode(false);
                }
            }
        });
    }

    // كود حفظ الوضغ الذي اختاره المستخدم وتخزينه
    private void saveMode(Boolean state){
        SharedPreferences sharedPreferences = getSharedPreferences("Mode", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("NightMode", state);
        editor.apply();
    }

    // كود جلب الوضع الذي اختاره المستخدم والبدء به بالتطبيق
    private Boolean loadMode(){
        SharedPreferences sharedPreferences = getSharedPreferences("Mode", MODE_PRIVATE);
        Boolean state = sharedPreferences.getBoolean("NightMode", false);
        return state;
    }


}
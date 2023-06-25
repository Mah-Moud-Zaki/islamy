package com.bfcai.islamy.Screens.WelcomeScreen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bfcai.islamy.Adapter.SliderAdapter;
import com.bfcai.islamy.MainActivity;
import com.bfcai.islamy.R;

public class OnboardingActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dotsLayout;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    Button start;
    Animation animation;
    int currentPos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPager = findViewById(R.id.view_page);
        dotsLayout = findViewById(R.id.dots);
        start =findViewById(R.id.get_started_btn);

        sliderAdapter = new SliderAdapter(this);

        viewPager.setAdapter(sliderAdapter);

        addDots(0);
        viewPager.addOnPageChangeListener(pageChangeListener);
        start.setOnClickListener(v -> {
            startActivity(new Intent(getBaseContext(),MainActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
    public void skip(View view){
        startActivity(new Intent( this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();

    }
    public void next(View view){
        viewPager.setCurrentItem(currentPos + 1);

    }

    private void addDots(int position){
        dots = new TextView[3]; // 3 slides
        dotsLayout.removeAllViews();
        //create this views
        for (int i=0;i<dots.length;i++)
        {
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);

            dotsLayout.addView(dots[i]);
        }

        if(dots.length>0)
        {
            dots[position].setTextColor(getResources().getColor(R.color.yellow));
        }

    }

    ViewPager.OnPageChangeListener pageChangeListener =new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPos=position;


            switch (position)
            {
                case 0:
                    start.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    start.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    animation = AnimationUtils.loadAnimation(OnboardingActivity.this,R.anim.bottom_anim);
                    start.setAnimation(animation);
                    start.setVisibility(View.VISIBLE);
            }
        }


        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
package com.bfcai.islamy.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bfcai.islamy.R;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context=context;
    }

    public int[] slideImages = {
            R.drawable.onboarding1,
            R.drawable.onboarding2,
            R.drawable.onboarding3,
    };

    public String[] slideHeadings ={
            "Quran",
            "Prayers",
            "Other Features"
    };

    public String[] slideDescriptions ={
            "You can access all Verses of the Qur'an as PDF formula",
            "Find all prayers you need (Morning prayers,Evening prayers,...etc)",
            "Also you can find another features such Al-Qiblah,Notes,Tasbeeh,Islamic stories,Religious Information",
    };


    @Override
    public int getCount() {
        return slideHeadings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (ConstraintLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slides_layout, container, false);

        ImageView slideImageView =  view.findViewById(R.id.slider_iv);
        TextView slideHeading =  view.findViewById(R.id.slider_tv_heading);
        TextView slideDescription =  view.findViewById(R.id.slider_tv_description);

        slideImageView.setImageResource(slideImages[position]);
        slideHeading.setText(slideHeadings[position]);
        slideDescription.setText(slideDescriptions[position]);

        container.addView(view);

        return view;

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);  //todo: RelativeLayout??
    }
}
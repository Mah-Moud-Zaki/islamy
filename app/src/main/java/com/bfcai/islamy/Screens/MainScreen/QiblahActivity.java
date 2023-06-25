package com.bfcai.islamy.Screens.MainScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.bfcai.islamy.MainActivity;
import com.bfcai.islamy.R;


public class QiblahActivity extends AppCompatActivity implements SensorEventListener {
    ImageView compass ;
    private static SensorManager sensorManager ;
    private static Sensor sensor ;
    private float currentDegree ;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiblah);
        toolbar = findViewById(R.id.qiblahToolbar);
        setSupportActionBar(toolbar);
        compass = findViewById(R.id.compass);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        if (sensor != null )
        {
            sensorManager.registerListener(this , sensor , SensorManager.SENSOR_DELAY_FASTEST);
        }
        else {

            Toast.makeText(getApplicationContext() , "not support" , Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int degree = Math.round(event.values[0]);
        RotateAnimation animation = new RotateAnimation(currentDegree , -degree , Animation.RELATIVE_TO_SELF
                ,0.5f , Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        compass.setAnimation(animation);
        currentDegree = -degree ;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }
}

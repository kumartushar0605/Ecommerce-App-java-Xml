package com.example.red.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.red.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView T_animation = findViewById(R.id.textView);
        Animation slide_up_Amin = AnimationUtils.loadAnimation(this,R.anim.slide_up);
        T_animation.startAnimation(slide_up_Amin);

      slide_up_Amin.setAnimationListener(new Animation.AnimationListener() {
          @Override
          public void onAnimationStart(Animation animation) {

          }

          @Override
          public void onAnimationEnd(Animation animation) {
              T_animation.animate().alpha(1).setDuration(3000).start();
          }

          @Override
          public void onAnimationRepeat(Animation animation) {

          }
      });
      new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
              Intent mainIntent = new Intent(Splash.this, MainActivity.class);
              startActivity(mainIntent);
              finish(); // Close the splash activity
          }
      },7000);



    }
}
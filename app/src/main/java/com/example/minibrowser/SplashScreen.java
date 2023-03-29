package com.example.minibrowser;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity
{
    ImageView icon = null;

    TextView title = null;


    @Override
    public void onCreate(Bundle sp)
    {
        super.onCreate(sp);
        setContentView(R.layout.splash_screen);


        //hiding actionbar
        getSupportActionBar().hide();


        //defining references after acquiring User Interface
        icon  = findViewById(R.id.icon);
        title = findViewById(R.id.app_name);

        //starting animation
        Animation rotate = AnimationUtils.loadAnimation(this,R.anim.icon_animation);
        icon.startAnimation(rotate);

        //controlling animation events
        rotate.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationEnd(Animation animation) {
                //navigate to the browser activity
                Intent switchAct = new Intent();
                switchAct.setClass(SplashScreen.this,MainActivity.class);
                startActivity(switchAct);
            }

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}

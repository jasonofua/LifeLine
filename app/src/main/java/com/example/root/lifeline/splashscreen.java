package com.example.root.lifeline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.root.lifeline.Intro.Welcome;

public class splashscreen extends AppCompatActivity {

    private ImageView splashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        splashImage = (ImageView)findViewById(R.id.splashscreen);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        splashImage.startAnimation(animation);

        final Intent intent = new Intent(this,Welcome.class);

        Thread timer = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(3000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }

}



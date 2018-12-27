package com.example.laptophome.news;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class home extends AppCompatActivity {
    ImageView img1,img2,img3,img4;

        public void load_Activity()
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent start = new Intent(home.this, MainActivity.class);
                    startActivity(start);
                    finish();
                }
            }, 3000);
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        img1=(ImageView)findViewById(R.id.image1);
        img2=(ImageView)findViewById(R.id.image2);
        img3=(ImageView)findViewById(R.id.image3);
        img4=(ImageView)findViewById(R.id.image4);
        load_Activity();

    }

    @Override
    protected void onRestart() {
            load_Activity();
        super.onRestart();
    }

}

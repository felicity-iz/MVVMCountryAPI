package com.test.countrylist;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageView introImage;
    private TextView introText;
    private static int TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_intro);

        introImage = findViewById(R.id.introImage);
        introText = findViewById(R.id.introText);

        introText.startAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_in));
        introImage.startAnimation(AnimationUtils.loadAnimation(this,R.anim.rotate));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, CountryListActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, TIME_OUT);

    }
}

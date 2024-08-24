package com.example.goodsweight;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashScreen extends AppCompatActivity {

    private ProgressBar _progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        _progressBar = findViewById(R.id.progressBarId);

        Thread thread = new Thread(new Runnable(){

            @Override
            public void run() {
                doWork();
                startApp();
            }
        });

        thread.start();
    }

    public void doWork() {

        for(int progress = 10; progress <= 100; progress += 10) {
            try {
                Thread.sleep(100);
                _progressBar.setProgress(progress);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }
    public void startApp(){
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);
        finish(); //Must
    }
}
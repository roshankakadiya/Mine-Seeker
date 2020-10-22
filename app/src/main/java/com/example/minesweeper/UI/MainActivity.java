package com.example.minesweeper.UI;

import android.content.Intent;
import android.os.Bundle;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.minesweeper.R;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animation();
        Button skipButton = (Button) findViewById(R.id.skipButton);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenu = Main_Menu.makeLaunchIntent(MainActivity.this);
                startActivity(mainMenu);

            }
        });

    }
    private void animation() {

        ImageView title = (ImageView) findViewById(R.id.logomain);
        YoYo.with(Techniques.FadeIn)
                .duration(5000)
                .repeat(0)
                .playOn(title);
       ImageView bombImage = (ImageView) findViewById(R.id.waterminemain);
        YoYo.with(Techniques.FadeIn)
                .duration(5000)
                .repeat(0)
                .playOn(bombImage);
        new Handler().postDelayed(new Runnable() {
           @Override
            public void run() {
                Intent MainMenu = Main_Menu.makeLaunchIntent(MainActivity.this);
                startActivity(MainMenu);
            }
        },5000);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
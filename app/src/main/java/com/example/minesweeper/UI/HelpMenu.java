package com.example.minesweeper.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.minesweeper.R;

public class HelpMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helpmenu);

        Button returnbutton = (Button)findViewById(R.id.returnMainMenu);
        returnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static Intent makeLaunchIntent(Context con){
        Intent intent = new Intent(con, HelpMenu.class);
        return intent;
    }
}
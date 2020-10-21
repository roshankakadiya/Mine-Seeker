package com.example.minesweeper.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.minesweeper.R;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class Main_Menu extends AppCompatActivity {

    static private int played = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);


        Button playGame = (Button) findViewById(R.id.playbutton);
        playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                played++;
                Intent launchGame = Play.makeLaunchIntent(Main_Menu.this, Option_tab.getNumRows(Main_Menu.this), Option_tab.getNumCols(Main_Menu.this), Option_tab.getNumMine(Main_Menu.this));
                startActivity(launchGame);
            }
        });

        Button options = (Button) findViewById(R.id.optionbutton);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent optionsScreen = Option_tab.makeLaunchIntent(Main_Menu.this);
                startActivity(optionsScreen);
            }
        });

        Button help = (Button) findViewById(R.id.hrlpbutton);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent helpScreen = HelpMenu.makeLaunchIntent(Main_Menu.this);
                startActivity(helpScreen);
            }
        });

    }


    static public int returnGamesPlayed(){
        return played;
    }
    public static Intent makeLaunchIntent(Context c){
        Intent intent = new Intent(c, Main_Menu.class);
        return intent;
    }
}
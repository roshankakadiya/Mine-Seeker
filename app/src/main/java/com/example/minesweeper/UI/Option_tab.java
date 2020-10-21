package com.example.minesweeper.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.minesweeper.R;
import com.example.minesweeper.model.GamePlay;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Option_tab extends AppCompatActivity {
    public static final String BOARDROW = "Rows";
    public static final String BOARDCOL = "Cols";
    public static final String NUMMINE = "numMine";
    public static final String BOARDPREFS = "BoardPrefs";
    public static final String MINEPREFS = "MinePrefs";
    private GamePlay gameGamePlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);

        Button returnButton = (Button) findViewById(R.id.backbutton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        gameGamePlay = GamePlay.getInstance();
        createBoardSize();
        createMineSize();
        SetupResetButton();
        int savedRowValue = getNumRows(this);
        int savedColValue = getNumCols(this);
        int savedMinesValue = getNumMine(this);
        Toast.makeText(this, "Default Board Size : " + savedRowValue + " X " + savedColValue, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Default Number of Zombies: "+ savedMinesValue, Toast.LENGTH_SHORT).show();
    }

    private void createBoardSize() {
        RadioGroup BoardSpecs = findViewById(R.id.boardsize);
        int[] RowSpecs = getResources().getIntArray(R.array.num_rows);
        int[] ColSpecs = getResources().getIntArray(R.array.num_cols);

        int SavedRows = getNumRows(this);
        int SavedCols = getNumCols(this);

        for(int i =0; i < RowSpecs.length; i++) {
            final int numRow = RowSpecs[i];
            final int numCol = ColSpecs[i];

            RadioButton button = new RadioButton(this);
            button.setText(getString(R.string.board,numRow,numCol));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Option_tab.this,"You selected "+ numRow+" rows by "+ numCol+" columns",Toast.LENGTH_SHORT).show();
                    SaveBoardSpecs(numRow,numCol);
                }
            });

            BoardSpecs.addView(button);

            if (numRow == SavedRows && numCol == SavedCols) {
                button.setChecked(true);
            }
        }
    }

    private void SaveBoardSpecs(int BoardRow, int BoardCol) {
        SharedPreferences prefs = this.getSharedPreferences(BOARDPREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(BOARDROW, BoardRow);
        editor.putInt(BOARDCOL, BoardCol);
        editor.apply();
    }

    private void createMineSize() {
        RadioGroup group = (RadioGroup) findViewById(R.id.watermine_size);

        int[] numMines = getResources().getIntArray(R.array.num_mines);

        // Create Buttons
        for (int i =0; i< numMines.length;i++)
        {
            final int numMine =numMines[i];
            RadioButton button = new RadioButton(this);
            button.setText(getString(R.string.numMine,numMine));
            // Set onclick callback
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Option_tab.this,"You selected "+ numMine,Toast.LENGTH_SHORT)
                            .show();
                    SaveMineInstalled(numMine);

                }
            });
            // Add radiogrp
            group.addView(button);

            //select default button
            if(numMine == getNumMine(this)) {
                button.setChecked(true);
            }

        }

    }

    private void SaveMineInstalled(int numMine) {
        SharedPreferences prefs = this.getSharedPreferences(MINEPREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(NUMMINE, numMine);
        editor.apply();
    }
    // Getter Functions for Rows, Columns and Mines
    public static int getNumRows(Context context) {
        int defaultRows = context.getResources().getInteger(R.integer.default_rows);
        SharedPreferences prefs = context.getSharedPreferences(BOARDPREFS, MODE_PRIVATE);
        int BoardRow = prefs.getInt(BOARDROW,defaultRows);
        Log.i("BoardRow",""+BoardRow);
        return BoardRow;
    }

    static public int getNumCols(Context context){
        int defaultCols = context.getResources().getInteger(R.integer.default_cols);
        SharedPreferences prefs = context.getSharedPreferences(BOARDPREFS, MODE_PRIVATE);
        int BoardCol = prefs.getInt(BOARDCOL,defaultCols);
        Log.i("BoardCol",""+BoardCol);
        return BoardCol;
    }

    public static int getNumMine(Context context){
        int defaultNumMine = context.getResources().getInteger(R.integer.default_mines);
        SharedPreferences prefs = context.getSharedPreferences(MINEPREFS, MODE_PRIVATE);
        int BoardZombies = prefs.getInt(NUMMINE,defaultNumMine);
        Log.i("BoardZombie",""+ BoardZombies);
        return BoardZombies;
    }

    private void SetupResetButton() {

        Button reset = findViewById(R.id.resetbutton);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int defaultMines = getResources().getInteger(R.integer.default_mines);
                int defaultRows = getResources().getInteger(R.integer.default_rows);
                int defaultColumns = getResources().getInteger(R.integer.default_cols);
                /*gameBoard.setMineNum(defaultMines);
                gameBoard.setBoardRow(defaultRows);
                gameBoard.setBoardCol(defaultColumns);
                */
                SaveBoardSpecs(defaultRows,defaultColumns);
                SaveMineInstalled(defaultMines);
                finish();
            }
        });
    }

    public static Intent makeLaunchIntent(Context c){
        Intent intent = new Intent(c, Option_tab.class);
        return intent;
    }
}
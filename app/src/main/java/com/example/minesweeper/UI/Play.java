package com.example.minesweeper.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.minesweeper.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Play extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPref";
    public static final int DEFAULT_VALUE = 0;
    public static final String Text = "text";


    private int Num_row;
    private int Num_col;
    private int Num_watermines;
    private int count_of_scans = 0;
    private int watermine_found = 0;
    Button[][] buttons = new Button[Num_row][Num_col];
    private List<Button> array = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameplay);


        extractDataFromIntent();


        mineText();
        saveData();
        setScansText();

        setPlayed();

    }

    public void saveData() {
        SharedPreferences sharedPref = this.getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int played = Main_Menu.returnGamesPlayed();
        editor.putInt( Text , played);
        editor.apply();
    }

    private void setPlayed(){
        int gamesPlayed = Main_Menu.returnGamesPlayed();
        SharedPreferences sharedPreference = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        int gg = sharedPreference.getInt(Text,DEFAULT_VALUE);
        TextView noOfTimesGamePlayed = (TextView)findViewById(R.id.gameplayed);
        noOfTimesGamePlayed.setText("Times Played: " + gg);

    }

    private void buttonClicked(int row,int col){
        count_of_scans++;
        setScansText();
    }

    private void setScansText() {
        TextView scansUsed = (TextView)findViewById(R.id.noOfScans);
        scansUsed.setText("Number Of Scans used: " + count_of_scans);
    }


    private void setWaterMines(Button[][] table){
        Random rand = new Random();
        int randomRow;
        int randomCol;
        for (int i = 0; i < Num_watermines; i++){
            randomRow = rand.nextInt(Num_row);
            randomCol = rand.nextInt(Num_col);
            if (table[randomRow][randomCol].getText() != "Set"){
                table[randomRow][randomCol].setText("Set");
                table[randomRow][randomCol].setTextColor(0xff0000);
                //array.add(table[randomRow][randomCol]);
            }
            else {
                i--;
            }
        }
    }
    //
    private void mines(final Button[][] butt){
        final MediaPlayer sound = MediaPlayer.create(this,R.raw.voice );
        for(int i = 0 ; i < Num_row;i++){
            for(int j = 0; j < Num_col;j++){
                if(butt[i][j].getText() == "Set"){
                    final Button b = butt[i][j];
                    //array.add(b);
                    lockButtonSizes();
                    int newWidth = 45;
                    int newHeight = 45;
                    Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.watermine);
                    final Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
                    final Resources resource = getResources();
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sound.start();
                            setScansText();
                            b.setBackground(new BitmapDrawable(resource, scaledBitmap));
                            if(watermine_found < Num_watermines){

                                watermine_found++;
                                count_of_scans++;
                                mineText();

                                if(watermine_found == Num_watermines){

                                    Toast.makeText(Play.this, "All Water Mines have been Found.", Toast.LENGTH_SHORT).show();
                                    for(int m = 0; m < Num_row;m++){
                                        for(int n = 0 ; n < Num_col;n++){
                                            butt[m][n].setText("0");

                                        }
                                    }
                                    FragmentManager manager = getSupportFragmentManager();
                                    MessageFragment dialog = new MessageFragment();
                                    dialog.show(manager,"Message Dialog");
                                    Log.i("TAG", "Just showed the dialog");
                                }
                            }
                        }
                    });

                }
                else
                    {
                        final Button[] bbb = {butt[i][j]};
                        final int fi = i;
                        final int fj = j;
                        //just a normal int had to put it in an array to execute the code
                        final int[] cc = {0};
                        bbb[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(count_of_scans < Num_row*Num_col){
                                    count_of_scans++;
                                    setScansText();

                                    for(int k = 0; k < Num_col;k++){
                                        if(butt[fi][k].getText() == "Set"){
                                            cc[0]++;}
                                    }
                                    for(int l = 0 ; l < Num_row;l++){
                                        if(butt[l][fj].getText() == "Set"){
                                            cc[0]++;}
                                    }


                                    if (bbb[0] == null || bbb[0].getText().toString().isEmpty()) {
                                        bbb[0].setText("" + cc[0]);
                                    }
                                }
                            }
                        });
                    }

            }
        }
    }

    private void mineText() {
        TextView noOfMines = (TextView)findViewById(R.id.foundText);
        noOfMines.setText("Found " + watermine_found + " of " + Num_watermines + " Water Mines");
    }



    private void populateMines() {
        TableLayout mineLand = (TableLayout)findViewById(R.id.watermineboard);
        for (int row = 0 ; row < Num_row ; row++){
            TableRow tablerow = new TableRow(this);
            tablerow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            mineLand.addView(tablerow);

            for (int col = 0; col < Num_col ; col++){
                final int finalRow = row;
                final int finalCol = col;
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                button.setPadding(0,0,0,0);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //buttonClicked(finalRow,finalCol);
                    }
                });
                tablerow.addView(button);
                buttons[row][col] = button;

            }
        }
    }


    private void lockButtonSizes(){
        for (int row = 0 ; row < Num_row;row ++){
            for (int col = 0; col < Num_col; col++){
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }
    private void congratulate() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Congratulations");

        alertDialogBuilder.setMessage("R.string.congratulations_text")
                .setCancelable(false)
                .setPositiveButton("Yay!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Play.this, Main_Menu.class);
                        startActivity(intent);
                    }
                });
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        Num_row = intent.getIntExtra("Hello",4);
        Num_col = intent.getIntExtra("World",6);
        Num_watermines = intent.getIntExtra("Sir",6);
        buttons = new Button[Num_row][Num_col];
        populateMines();
        setWaterMines(buttons);
        mines(buttons);
    }

    public static Intent makeLaunchIntent(Context c,int rows,int cols,int watermine){
        Intent intent = new Intent(c, Play.class);
        intent.putExtra("Hello",rows);
        intent.putExtra("World",cols);
        intent.putExtra("Sir",watermine);
        return intent;
    }
}
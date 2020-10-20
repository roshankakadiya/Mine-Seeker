package com.example.minesweeper.model;

public class Board {
    private int clickCount;
    private static Board instance;
    private int rows;
    private int columns;
    private int noOfMines;
    private int usedScans;
    private int minesFound;
    public Board(){
        minesFound=0;
        usedScans=0;

    }
    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }
    public int getNumRows(){
        return rows;
    }

    public int getNumCols(){
        return columns;
    }

    public int getNumMine() {
        return noOfMines;
    }

    public int getScanUsed() {
        return usedScans;
    }

    public int getFoundMine() {
        return minesFound;
    }
    public void setBoardRow(int row){
        this.rows = row;
    }

    public void setBoardCol(int col){
        this.columns = col;
    }

    public void setMineNum(int Mines){
        this.noOfMines = Mines;
    }


    public int getClickCount(){
        return clickCount;
    }

    public void registerClick(){
        clickCount++;
    }

}

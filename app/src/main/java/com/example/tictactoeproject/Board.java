package com.example.tictactoeproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.SweepGradient;
import android.view.View;

public class Board extends View {
    final int size = 3;
    Square[][] board;
    Context context;
    public Board(Context context){
        super(context);
        this.context = context;
        board = new Square[size][size];
    }

    public Square[][] getBoard() {
        return this.board;
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        drawBoard(canvas);
    }
    public void drawBoard(Canvas canvas){
        int h = canvas.getWidth()/size; //101
        int w = canvas.getWidth()/size; //101
        Square Vertical_line1 = new Square(w - 10,canvas.getWidth()/6,20, h * 3, 0, 0);
        Vertical_line1.drawBlack(canvas);
        Square Vertical_line2 = new Square(w * 2 - 10,canvas.getWidth()/6,20, h * 3, 0, 0);
        Vertical_line2.drawBlack(canvas);
        Square Horizontal_line1 = new Square(0,canvas.getWidth()/6 + h,w * 3, 10, 0, 0);
        Horizontal_line1.drawBlack(canvas);
        Square Horizontal_line2 = new Square(0,canvas.getWidth()/6 + h * 2,w * 3, 10, 0, 0);
        Horizontal_line2.drawBlack(canvas);
        for (int i = 0; i < board.length; i ++){
            for (int j = 0; j < board.length; j++){

                board[i][j] = new Square(j*w, canvas.getWidth()/6 + i*h, w, h,i,j); //jw, 150 + ih, w, h,i,j
            }
        }
    }
}
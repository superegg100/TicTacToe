package com.example.tictactoeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView errorer, result;
    private Board board;
    FrameLayout frame;
    TicTacToe game;
    boolean draw = false;
    int X = 0, Y = 0;
    LinearLayout linear;
    RelativeLayout myRelativeLayout;
    RelativeLayout.LayoutParams layoutParams;
    ImageView iv, arnold1, arnold2;
    ImageView[] toes;
    int currentturnamount;
    Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arnold1 = findViewById(R.id.arnold1);
        arnold2 = findViewById(R.id.arnold2);
        arnold1.setVisibility(View.INVISIBLE);
        arnold2.setVisibility(View.INVISIBLE);
        restartButton = findViewById(R.id.restart);
        restartButton.setVisibility(View.INVISIBLE);
        restartButton.setClickable(false);
        result = findViewById(R.id.result);
        game = new TicTacToe();
        layoutParams = NewLayout();
        myRelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        iv = new ImageView(MainActivity.this);
        toes = new ImageView[9];
        frame = findViewById(R.id.frm);
        board = new Board(this);
        for(int i = 0; i < toes.length; i++){
            toes[i] = new ImageView(MainActivity.this);
        }
        currentturnamount = 0;
        frame.addView(board);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 3; i++){
                    for (int j = 0; j < 3; j++){
                        game.board[i][j] = Player.None;
                    }
                }
                for (int i = 0; i < currentturnamount; i++){
                    myRelativeLayout.removeView(toes[i]);
                }
                currentturnamount = 0;
                draw = false;
                arnold1.setVisibility(View.INVISIBLE);
                arnold2.setVisibility(View.INVISIBLE);
                result.setText("");
            }
        });
        Toast.makeText(this,"READY TO LOSE ?", Toast.LENGTH_LONG).show();
    }

    public RelativeLayout.LayoutParams NewLayout(){
        return new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int x=0,y=0;
        Move moveX, moveO;
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            if (event.getX() <= width && event.getX() > 0 && event.getY() > width/6 + width/5.4 && event.getY() <= width/6 + width + width/5.4){
                x =  ((int)event.getX() - (int)event.getX() % (width/3))/360;
                y = (((int)event.getY() - (int)event.getY() % (width/3)) - ((width/3)))/360;
                //y = (height - (int)event.getY()) - (int)event.getY() % 101- 150;
                moveX = new Move(x,y);
                if (!game.isOver() && !draw) {
                    if (game.isEmpty(x, y)) {
                        game.makeMove(moveX);
                        layoutParams = NewLayout();
                        layoutParams.width = width/3 + 20;
                        layoutParams.height = width/3 - 50;
                        toes[currentturnamount].setImageResource(R.drawable.x);
                        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                        layoutParams.topMargin = ((width/3) * y + width/6 + 10);
                        layoutParams.rightMargin = (width/3)*(2-x);
                        myRelativeLayout.addView(toes[currentturnamount], layoutParams);
                        currentturnamount++;
                        if (!game.getAvailableMoves().isEmpty())
                        {
                            moveO = findTheBestMove(game, game.currentPlayer);
                            game.makeMove(moveO);

                            layoutParams = NewLayout();
                            layoutParams.width = width/3 + 20;
                            layoutParams.height = width/3 - 50;
                            toes[currentturnamount].setImageResource(R.drawable.o);
                            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                            layoutParams.topMargin = ((width/3) * moveO.getCol() + width/6 + 10);
                            layoutParams.rightMargin = (width/3)*(2- moveO.getRow());
                            myRelativeLayout.addView(toes[currentturnamount], layoutParams);
                            currentturnamount++;

                        }
                        if (checkDraw(game)){
                            result.setText("DRAW");
                            draw = true;
                            restartButton.setClickable(true);
                            restartButton.setVisibility(View.VISIBLE);
                        }
                        if (game.isOver() && !draw) {
                            result.setText("YOU'VE BEEN TERMINATED GOOD LUCK NEXT TIME");
                            restartButton.setClickable(true);
                            restartButton.setVisibility(View.VISIBLE);
                            arnold1.setVisibility(View.VISIBLE);
                            arnold2.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        Toast.makeText(this, "space taken already......", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
        return true;
    }

    public static Move findTheBestMove(TicTacToe game, Player player) {
        List<Move> availableMoves = game.getAvailableMoves();
        int bestScore = Integer.MIN_VALUE;
        Move bestMove = null;

        for (Move move : availableMoves) {
            TicTacToe newState = game.getNewState(move);
            int score = miniMax(newState, 0, false, player);

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    public boolean checkDraw(TicTacToe game){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (game.board[i][j] == Player.None){
                    return false;
                }
            }
        }
        return true;
    }

    public static int miniMax(TicTacToe game, int depth, boolean isMaximizing, Player player) {
        Player opponent = (player == Player.X) ? Player.O : Player.X;
        Player currentPlayer = isMaximizing ? player : opponent;

        if (game.isOver()) {
            return game.score(player);
        }

        List<Move> availableMoves = game.getAvailableMoves();

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;

            for (Move move : availableMoves) {
                TicTacToe newState = game.getNewState(move);
                int score = miniMax(newState, depth + 1, false, player);
                bestScore = Math.max(score, bestScore);
            }

            return bestScore;
        }
        else {
            int bestScore = Integer.MAX_VALUE;
            for (Move move : availableMoves) {
                TicTacToe newState = game.getNewState(move);
                int score = miniMax(newState, depth + 1, true, player);
                bestScore = Math.min(score, bestScore);
            }

            return bestScore;
        }
    }
}
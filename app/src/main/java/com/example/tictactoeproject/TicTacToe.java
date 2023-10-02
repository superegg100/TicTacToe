package com.example.tictactoeproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

enum Player {
    None,
    X,
    O,
}

public class TicTacToe {
    public Player[][] board;
    public Player currentPlayer;

    public TicTacToe() {
        board = new Player[3][3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                board[i][j] = Player.None;
            }
        }
        currentPlayer = Player.X;
    }

    public boolean isEmpty(int i, int j){
        if (this.board[i][j] == Player.None){
            return true;
        }
        return false;
    }

    public boolean isOver() {
        if (checkWin(Player.X)) {
            return true;
        } else if (checkWin(Player.O)) {
            return true;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == Player.None) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Move> getAvailableMoves() {
        List<Move> moves = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == Player.None) {
                    moves.add(new Move(i, j));
                }
            }
        }

        return moves;
    }

    public TicTacToe getNewState(Move move) {
        if (board[move.getRow()][move.getCol()] != Player.None) {
            throw new IllegalStateException("Invalid move. Cell is already occupied.");
        }
        TicTacToe newState = new TicTacToe();
        newState.board = cloneBoard(board);
        newState.board[move.getRow()][move.getCol()] = currentPlayer;
        newState.currentPlayer = (currentPlayer == Player.X) ? Player.O : Player.X;

        return newState;
    }

    public int score(Player player) {
        if (checkWin(player)) {
            return 1;
        } else if (checkWin((player == Player.X) ? Player.O : Player.X)) {
            return -1;
        } else {
            return 0;
        }
    }

    private boolean checkWin(Player player) {
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                    (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }

        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                (board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
            return true;
        }

        return false;
    }

    public void makeMove(Move move) {
        if (board[move.getRow()][move.getCol()] != Player.None) {
            //throw new IllegalStateException("Invalid move. Cell is already occupied.");
            return;
        }
        board[move.getRow()][move.getCol()] = currentPlayer;
        currentPlayer = (currentPlayer == Player.X) ? Player.O : Player.X;
    }

    private Player[][] cloneBoard(Player[][] original) {
        Player[][] clone = new Player[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                clone[i][j] = original[i][j];
            }
        }
        return clone;
    }
}
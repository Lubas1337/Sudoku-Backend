package com.test.testing.sudoku.service;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SudokuGenerator {
    private static final int SIZE = 9;
    private static final int SUBGRID_SIZE = 3;
    private int[][] board;

    public SudokuGenerator() {
        board = new int[SIZE][SIZE];
    }

    public int[][] generate() {
        fillBoard();
        removeCells();
        return board;
    }

    public int[][] generateSolved() {
        int[][] solvedBoard = new int[SIZE][SIZE];
        fillBoard();
        copyBoard(board, solvedBoard);
        solveBoard(0, 0);
        return solvedBoard;
    }

    private void copyBoard(int[][] source, int[][] destination) {
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(source[i], 0, destination[i], 0, SIZE);
        }
    }


    private void fillBoard() {
        fillDiagonalSubgrids();
        solveBoard(0, 0);
    }

    private boolean solveBoard(int row, int col) {
        if (row == SIZE) {
            return true;
        }
        if (col == SIZE) {
            return solveBoard(row + 1, 0);
        }
        if (board[row][col] != 0) {
            return solveBoard(row, col + 1);
        }

        for (int num = 1; num <= SIZE; num++) {
            if (isValidMove(row, col, num)) {
                board[row][col] = num;
                if (solveBoard(row, col + 1)) {
                    return true;
                }
                board[row][col] = 0;
            }
        }
        return false;
    }

    private boolean isValidMove(int row, int col, int num) {
        return !usedInRow(row, num) && !usedInCol(col, num) && !usedInSubgrid(row - row % SUBGRID_SIZE, col - col % SUBGRID_SIZE, num);
    }

    private boolean usedInRow(int row, int num) {
        for (int col = 0; col < SIZE; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean usedInCol(int col, int num) {
        for (int row = 0; row < SIZE; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean usedInSubgrid(int startRow, int startCol, int num) {
        for (int row = 0; row < SUBGRID_SIZE; row++) {
            for (int col = 0; col < SUBGRID_SIZE; col++) {
                if (board[row + startRow][col + startCol] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    private void fillDiagonalSubgrids() {
        for (int row = 0; row < SIZE; row += SUBGRID_SIZE) {
            fillSubgrid(row, row);
        }
    }

    private void fillSubgrid(int row, int col) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        shuffleArray(nums);
        int index = 0;
        for (int i = 0; i < SUBGRID_SIZE; i++) {
            for (int j = 0; j < SUBGRID_SIZE; j++) {
                board[row + i][col + j] = nums[index++];
            }
        }
    }

    private void removeCells() {
        int numToRemove = SIZE * SIZE / 2; // You can adjust the difficulty by changing this number
        while (numToRemove > 0) {
            int row = new Random().nextInt(SIZE);
            int col = new Random().nextInt(SIZE);
            if (board[row][col] != 0) {
                board[row][col] = 0;
                numToRemove--;
            }
        }
    }

    private void shuffleArray(int[] arr) {
        Random random = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
}

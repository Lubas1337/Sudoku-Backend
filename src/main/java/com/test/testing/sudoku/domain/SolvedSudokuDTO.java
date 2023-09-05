package com.test.testing.sudoku.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolvedSudokuDTO {
    private int[][] solvedValue;
}

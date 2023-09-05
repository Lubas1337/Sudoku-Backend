package com.test.testing.sudoku.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnsolvedSudokuDTO {
    private int[][] value;
}

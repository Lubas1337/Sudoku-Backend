package com.test.testing.sudoku.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SudokuCellId implements Serializable {
    @Column(name = "row")
    private int row;

    @Column(name = "col")
    private int col;
}
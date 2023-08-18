package com.test.testing.gameLogic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.testing.gameLogic.converter.ArrayToStringConverter;
import com.test.testing.gameLogic.util.SudokuUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sudoku {

    @Id
    @GeneratedValue
    private long id;

    @Column
    @Convert(converter = ArrayToStringConverter.class)
    private int[] solution;

    @Column
    @Convert(converter = ArrayToStringConverter.class)
    private int[] puzzle;

    @Getter
    private DifficultyLevel difficulty;

    @JsonIgnore
    private Long userId;

    private Long timer;



    public Sudoku(int[] solution, int[] puzzle) {
        this.solution = new int[solution.length];
        this.puzzle = new int[puzzle.length];
        System.arraycopy(solution, 0, this.solution, 0, solution.length);
        System.arraycopy(puzzle, 0, this.puzzle, 0, puzzle.length);
        this.difficulty = SudokuUtil.computeDifficulty(puzzle);
    }

    public int[] getSolution() {
        int[] solution = new int[this.solution.length];
        System.arraycopy(this.solution, 0, solution, 0, solution.length);
        return solution;
    }

    public int[] getPuzzle() {
        int[] puzzle = new int[this.puzzle.length];
        System.arraycopy(this.puzzle, 0, puzzle, 0, puzzle.length);
        return puzzle;
    }

    public void print() {
        System.out.println("difficulty:");
        System.out.println(difficulty);
        System.out.println("puzzle:");
        System.out.println(SudokuUtil.format(puzzle));
        System.out.println("solution:");
        System.out.println(SudokuUtil.format(solution));
    }
}

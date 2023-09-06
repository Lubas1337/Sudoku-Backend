package com.test.testing.sudoku.domain;

import com.test.testing.domain.Entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sudoku_board")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SudokuBoard {

    @EmbeddedId
    private SudokuCellId id;

    @Column(name = "value")
    private int value;

    @Column(name = "solvedValue")
    private int solvedValue;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

}

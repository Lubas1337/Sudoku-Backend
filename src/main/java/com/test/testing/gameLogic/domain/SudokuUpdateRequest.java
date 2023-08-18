package com.test.testing.gameLogic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SudokuUpdateRequest {
    private int[] solution;
    private int[] puzzle;
    private Long timer;
}

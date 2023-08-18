package com.test.testing.gameLogic;

import com.test.testing.Entity.ImageModel;
import com.test.testing.gameLogic.domain.DifficultyLevel;
import com.test.testing.gameLogic.domain.Sudoku;
import com.test.testing.gameLogic.domain.SudokuUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/game")
public class SudokuController {
    private final SudokuService sudokuService;
    @Autowired
    public SudokuController(SudokuService sudokuService) {
        this.sudokuService = sudokuService;
    }
    @GetMapping("/generate")
    public ResponseEntity<Sudoku> generateSudoku(
            @RequestParam("difficulty") DifficultyLevel difficulty,
            Principal principal) {
        Sudoku userSudoku = sudokuService.generateSudoku(principal, difficulty);
        return new ResponseEntity<>(userSudoku, HttpStatus.OK);
    }
    @PutMapping("/{sudokuId}")
    public ResponseEntity<Sudoku> updateSudoku(
            @PathVariable long sudokuId,
            @RequestBody SudokuUpdateRequest updateRequest) {
        int[] newSolution = updateRequest.getSolution();
        int[] newPuzzle = updateRequest.getPuzzle();
        Long newTimer = updateRequest.getTimer();
        Sudoku sudoku = sudokuService.updateSudoku(sudokuId, newSolution, newPuzzle, newTimer);
        return new ResponseEntity<>(sudoku, HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<List<Sudoku>> getAllSudokuByUser(Principal principal) {
        List<Sudoku> sudoku = sudokuService.getAllSudokusByUserId(principal);
        return ResponseEntity.ok(sudoku);
    }
}

package com.test.testing.sudoku.service;

import com.test.testing.domain.Entity.UserEntity;
import com.test.testing.domain.repository.UserRepository;
import com.test.testing.sudoku.domain.SolvedSudokuDTO;
import com.test.testing.sudoku.domain.SudokuBoard;
import com.test.testing.sudoku.domain.UnsolvedSudokuDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sudoku")
public class SudokuController {
    private final SudokuService sudokuService;
    private final UserRepository userRepository;

    @Autowired
    public SudokuController(SudokuService sudokuService, UserRepository userRepository) {
        this.sudokuService = sudokuService;
        this.userRepository = userRepository;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateAndSaveSudoku() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserEntity currentUser) {
            sudokuService.generateAndSaveSudoku(currentUser);
            return ResponseEntity.ok("Sudoku generated and saved for user: " + currentUser.getUsername());
        } else {
            return ResponseEntity.badRequest().body("User not authenticated or not of type UserEntity");
        }
    }

    @GetMapping("/{userId}")
    public int[][] getSudokuByUserId(@PathVariable Long userId) {
        return sudokuService.getSudokuBoardByUserId(userId);
    }

    @GetMapping("/solved/{userId}")
    public ResponseEntity<SolvedSudokuDTO> getSolvedSudokuBoard(@PathVariable Long userId) {
        int[][] solvedBoard = sudokuService.getSolvedSudokuBoardByUserId(userId);
        SolvedSudokuDTO solvedSudokuDTO = new SolvedSudokuDTO(solvedBoard);
        return ResponseEntity.ok(solvedSudokuDTO);
    }

    @GetMapping("/unsolved/{userId}")
    public ResponseEntity<UnsolvedSudokuDTO> getUnsolvedSudokuBoard(@PathVariable Long userId) {
        int[][] unsolvedBoard = sudokuService.getSudokuBoardByUserId(userId);
        if (unsolvedBoard != null) {
            UnsolvedSudokuDTO unsolvedSudokuDTO = new UnsolvedSudokuDTO(unsolvedBoard);
            return ResponseEntity.ok(unsolvedSudokuDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

package com.test.testing.sudoku.service;

import com.test.testing.domain.Entity.UserEntity;
import com.test.testing.sudoku.domain.SudokuBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/sudoku")
public class SudokuController {
    private final SudokuService sudokuService;

    @Autowired
    public SudokuController(SudokuService sudokuService) {
        this.sudokuService = sudokuService;
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

    @GetMapping("/board/{gameId}")
    public int[][] getSudokuBoardByGameId(@PathVariable Integer gameId) {
        return sudokuService.getSudokuBoardByGameId(gameId);
    }

    @GetMapping("/solved/{gameId}")
    public int[][] getSolvedValueByGameId(@PathVariable Integer gameId) {
        return sudokuService.getSolvedValueByGameId(gameId);
    }


    @GetMapping("/game-ids")
    public ResponseEntity<List<Long>> getGameIdsByCurrentUser(Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            List<Long> gameIds = sudokuService.findGameIdsByUsername(username);
            return ResponseEntity.ok(gameIds);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateSudokuBoard(
            @RequestParam Integer gameId,
            @RequestParam Integer row,
            @RequestParam Integer col,
            @RequestParam Integer newValue) {

        boolean updated = sudokuService.updateSudokuBoard(gameId, row, col, newValue);

        if (updated) {
            return ResponseEntity.ok("Sudoku board updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Sudoku board update failed. Check convergence.");
        }
    }
}

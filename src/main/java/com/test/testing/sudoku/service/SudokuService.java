package com.test.testing.sudoku.service;

// SudokuService.java
import com.test.testing.domain.Entity.UserEntity;
import com.test.testing.domain.repository.UserRepository;
import com.test.testing.sudoku.domain.SudokuBoard;
import com.test.testing.sudoku.domain.SudokuBoardRepository;
import com.test.testing.sudoku.domain.SudokuCellId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SudokuService {

    private final SudokuBoardRepository boardRepository;
    private final SudokuGenerator sudokuGenerator;
    private final UserRepository userRepository; // Add a UserRepository to retrieve the current user

    @Autowired
    public SudokuService(SudokuBoardRepository boardRepository, SudokuGenerator sudokuGenerator, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.sudokuGenerator = sudokuGenerator;
        this.userRepository = userRepository;
    }

    public void generateAndSaveSudoku(UserEntity currentUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserEntity) {
            int[][] generatedSolved = sudokuGenerator.generateSolved();
            int[][] sudokuMatrix = sudokuGenerator.generate();

            for (int row = 0; row < sudokuMatrix.length; row++) {
                for (int col = 0; col < sudokuMatrix[row].length; col++) {
                    SudokuCellId cellId = new SudokuCellId();
                    cellId.setRow(row);
                    cellId.setCol(col);

                    SudokuBoard sudokuBoard = new SudokuBoard();
                    sudokuBoard.setId(cellId);
                    sudokuBoard.setSolvedValue(generatedSolved[row][col]);
                    sudokuBoard.setValue(sudokuMatrix[row][col]);
                    sudokuBoard.setUser(currentUser); // Associate the board with the current user
                    boardRepository.save(sudokuBoard);
                }
            }
        }
    }

    public int[][] getSolvedSudokuBoardByUserId(Long userId) {
        return boardRepository.findSolvedValueByUserId(userId);
    }


    public int[][] getSudokuBoardByUserId(Long userId) {
        return boardRepository.findSudokuBoardByUserId(userId);
    }

}


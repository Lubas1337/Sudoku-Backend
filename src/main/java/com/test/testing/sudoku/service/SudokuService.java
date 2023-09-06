package com.test.testing.sudoku.service;

// SudokuService.java
import com.test.testing.domain.Entity.UserEntity;
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

    @Autowired
    public SudokuService(SudokuBoardRepository boardRepository, SudokuGenerator sudokuGenerator) {
        this.boardRepository = boardRepository;
        this.sudokuGenerator = sudokuGenerator;
    }



    public void generateAndSaveSudoku(UserEntity currentUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserEntity) {
            int[][] generatedSolved = sudokuGenerator.generateSolved();
            int[][] sudokuMatrix = sudokuGenerator.generate();

            List<SudokuBoard> existingBoards = boardRepository.findByUser(currentUser);

            int gameId = 1;

            if (!existingBoards.isEmpty()) {
                gameId = existingBoards.stream()
                        .mapToInt(board -> board.getId().getGameId())
                        .max()
                        .orElse(0) + 1;
            }

            for (int row = 0; row < sudokuMatrix.length; row++) {
                for (int col = 0; col < sudokuMatrix[row].length; col++) {
                    SudokuCellId cellId = new SudokuCellId();
                    cellId.setRow(row);
                    cellId.setCol(col);
                    cellId.setGameId(gameId);

                    SudokuBoard sudokuBoard = new SudokuBoard();
                    sudokuBoard.setId(cellId);
                    sudokuBoard.setSolvedValue(generatedSolved[row][col]);
                    sudokuBoard.setValue(sudokuMatrix[row][col]);
                    sudokuBoard.setUser(currentUser);

                    existingBoards.add(sudokuBoard);
                }
            }

            boardRepository.saveAll(existingBoards);
        }
    }


    public List<Long> findGameIdsByUsername(String username) {
        return boardRepository.findGameIdsByUsername(username);
    }


    public int[][] getSudokuBoardByGameId(Integer gameId) {
        return boardRepository.findSudokuBoardByGameId(gameId);
    }

    public int[][] getSolvedValueByGameId(Integer gameId) {
        return boardRepository.findSolvedValueByUserId(gameId);
    }


    public boolean updateSudokuBoard(Integer gameId, Integer row, Integer col, Integer newValue) {
        SudokuBoard sudokuBoard = boardRepository.findById(new SudokuCellId(gameId, row, col)).orElse(null);

        if (sudokuBoard == null) {
            return false;
        }

        int solvedValue = sudokuBoard.getSolvedValue();
        if (newValue == solvedValue) {
            sudokuBoard.setValue(newValue);
            boardRepository.save(sudokuBoard);
            return true;
        } else {
            return false;
        }
    }
}


package com.test.testing.sudoku.domain;

import com.test.testing.domain.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SudokuBoardRepository extends JpaRepository<SudokuBoard, SudokuCellId> {
    @Query(value = "SELECT value FROM sudoku_board WHERE user_id = :userId ORDER BY row, col", nativeQuery = true)
    int[][] findSudokuBoardByUserId(Long userId);

    @Query("SELECT sb.solvedValue FROM SudokuBoard sb WHERE sb.user.id = :userId")
    int[][] findSolvedValueByUserId(Long userId);
}

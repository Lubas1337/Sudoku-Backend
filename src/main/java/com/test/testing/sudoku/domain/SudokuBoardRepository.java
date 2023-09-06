package com.test.testing.sudoku.domain;

import com.test.testing.domain.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SudokuBoardRepository extends JpaRepository<SudokuBoard, SudokuCellId> {
    @Query(value = "SELECT value FROM sudoku_board WHERE game_id = :gameId ORDER BY row, col", nativeQuery = true)
    int[][] findSudokuBoardByGameId(@Param("gameId") Integer gameId);

    @Query(value = "SELECT solved_value FROM sudoku_board WHERE game_id = :gameId ORDER BY row, col", nativeQuery = true)
    int[][] findSolvedValueByUserId(@Param("gameId") Integer gameId);

    @Query("SELECT DISTINCT sb.id.gameId FROM SudokuBoard sb WHERE sb.user.username = :username")
    List<Long> findGameIdsByUsername(@Param("username") String username);

    List<SudokuBoard> findByUser(UserEntity user);


}

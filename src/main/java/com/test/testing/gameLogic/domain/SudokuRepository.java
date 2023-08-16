package com.test.testing.gameLogic.domain;

import com.test.testing.Entity.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SudokuRepository extends JpaRepository<Sudoku, Long> {
    List<Sudoku> findByUserId(Long userId);


}

package com.test.testing.gameLogic;

import com.test.testing.Entity.ImageModel;
import com.test.testing.Entity.UserEntity;
import com.test.testing.gameLogic.domain.Sudoku;
import com.test.testing.gameLogic.domain.SudokuRepository;
import com.test.testing.gameLogic.util.SudokuUtil;
import com.test.testing.repository.UserRepository;
import com.test.testing.services.ImageUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class SudokuService {

    public static final Logger LOG = LoggerFactory.getLogger(ImageUploadService.class);


    private final SudokuRepository sudokuRepository;

    private final UserRepository userRepository;

    @Autowired
    public SudokuService(SudokuRepository sudokuRepository, UserRepository userRepository) {
        this.sudokuRepository = sudokuRepository;
        this.userRepository = userRepository;
    }

    public Sudoku generateSudoku(Principal principal) {
        Sudoku sudoku = SudokuUtil.createSudoku();
        Sudoku entity = new Sudoku(sudoku.getSolution(), sudoku.getPuzzle());

        UserEntity user = getUserByPrincipal(principal);
        entity.setUserId(user.getId());

        sudokuRepository.save(entity);
        sudoku.setId(entity.getId());
        sudoku.setTimer(System.currentTimeMillis()); // Start the timer
        return sudoku;
    }

    public Sudoku updateSudoku(long sudokuId, int[] newSolution, int[] newPuzzle, long elapsedTime) {
        Optional<Sudoku> optionalSudoku = sudokuRepository.findById(sudokuId);

        if (optionalSudoku.isPresent()) {
            Sudoku sudoku = optionalSudoku.get();
            sudoku.setSolution(newSolution);
            sudoku.setPuzzle(newPuzzle);
            sudoku.setTimer(elapsedTime); // Set the elapsed time
            sudokuRepository.save(sudoku);
            return sudoku;
        } else {
            throw new IllegalArgumentException("Sudoku with ID " + sudokuId + " not found.");
        }
    }


    private UserEntity getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));

    }

    public List<Sudoku> getAllSudokusByUserId(Long userId) {
        return sudokuRepository.findByUserId(userId);
    }
}


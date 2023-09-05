package com.test.testing.web;

import com.test.testing.domain.Entity.UserEntity;
import com.test.testing.domain.dto.UserDTO;
import com.test.testing.services.UserService;
import com.test.testing.utils.validations.ResponseErrorValidation;
import com.test.testing.web.mappers.UserMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final UserMapper userFacade;
    private final ResponseErrorValidation responseErrorValidation;

    public UserController(UserService userService, UserMapper userFacade, ResponseErrorValidation responseErrorValidation) {
        this.userService = userService;
        this.userFacade = userFacade;
        this.responseErrorValidation = responseErrorValidation;
    }

    @GetMapping("/")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        UserEntity userEntity = userService.getCurrentUser(principal);
        UserDTO userDTO = userFacade.userToUserDTO(userEntity);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable("userId") String userId) {
        UserEntity userEntity = userService.getUserById(Long.parseLong(userId));
        UserDTO userDTO = userFacade.userToUserDTO(userEntity);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        UserEntity userEntity = userService.updateUser(userDTO, principal);

        UserDTO userUpdated = userFacade.userToUserDTO(userEntity);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

    @GetMapping("/currentUserId")
    public ResponseEntity<Long> getCurrentUserId(Principal principal) {
        Long userId = userService.getCurrentUserId();
        return ResponseEntity.ok(userId);
    }


}

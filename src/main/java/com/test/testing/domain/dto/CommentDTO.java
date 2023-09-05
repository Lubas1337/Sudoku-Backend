package com.test.testing.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;



@Data
public class CommentDTO {

    private Long id;
    @NotEmpty
    private String message;
    private String username;

}

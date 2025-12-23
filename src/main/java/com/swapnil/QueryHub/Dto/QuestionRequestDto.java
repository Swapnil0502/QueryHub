package com.swapnil.QueryHub.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequestDto {

    @NotBlank(message = "title is required")
    @Size(min = 10, max = 100, message = "title must be between 10 to 100 characters")
    private String title;

    @NotBlank(message = "content is required")
    @Size(min = 10, max = 100, message = "content must be between 10 to 100 characters")
    private String content;

}

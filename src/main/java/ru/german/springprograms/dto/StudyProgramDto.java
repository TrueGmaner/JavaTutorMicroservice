package ru.german.springprograms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyProgramDto {
    private Integer id;
    private String name;
    private Integer price;
}

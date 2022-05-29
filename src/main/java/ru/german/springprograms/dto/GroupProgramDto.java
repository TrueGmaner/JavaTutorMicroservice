package ru.german.springprograms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupProgramDto {
    private Integer id;
    private Integer idProgram;
    private Integer idGroup;
}

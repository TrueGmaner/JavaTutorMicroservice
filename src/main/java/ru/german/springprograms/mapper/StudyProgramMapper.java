package ru.german.springprograms.mapper;

import org.springframework.stereotype.Component;
import ru.german.springprograms.dto.StudyProgramDto;
import ru.german.springprograms.entities.StudyProgram;

@Component
public class StudyProgramMapper {
    public StudyProgramDto mapToDto(StudyProgram studyProgram){
        return StudyProgramDto.builder()
                .id(studyProgram.getId())
                .name(studyProgram.getName())
                .price(studyProgram.getPrice())
                .build();
    }
}

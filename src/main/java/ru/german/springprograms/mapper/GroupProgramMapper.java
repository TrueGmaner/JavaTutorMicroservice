package ru.german.springprograms.mapper;

import org.springframework.stereotype.Component;
import ru.german.springprograms.dto.GroupProgramDto;
import ru.german.springprograms.entities.GroupProgram;

@Component
public class GroupProgramMapper {
    public GroupProgramDto mapToDto(GroupProgram groupProgram){
        return GroupProgramDto.builder()
                .id(groupProgram.getId())
                .idProgram(groupProgram.getStudyProgram().getId())
                .idGroup(groupProgram.getIdGroup())
                .build();
    }
}

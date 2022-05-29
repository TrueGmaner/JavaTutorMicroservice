package ru.german.springprograms.mapper;

import org.springframework.stereotype.Component;
import ru.german.springprograms.dto.InstituteAccessDto;
import ru.german.springprograms.entities.InstituteAccess;

@Component
public class InstituteAccessMapper {
    public InstituteAccessDto mapToDto(InstituteAccess instituteAccess){
        return InstituteAccessDto.builder()
                .id(instituteAccess.getId())
                .programName(instituteAccess.getProgramName())
                .build();
    }
}

package ru.german.springprograms.mapper;

import org.springframework.stereotype.Component;
import ru.german.springprograms.dto.PersonalLicenseDto;
import ru.german.springprograms.entities.PersonalLicense;

@Component
public class PersonalLicenseMapper {
    public PersonalLicenseDto mapToDto(PersonalLicense personalLicense){
        return PersonalLicenseDto.builder()
                .id(personalLicense.getId())
                .programName(personalLicense.getProgramName())
                .price(personalLicense.getPrice())
                .dateStart(personalLicense.getDateStart())
                .dateEnd(personalLicense.getDateEnd())
                .userId(personalLicense.getUserId())
                .condition(personalLicense.getCondition())
                .build();
    }
}

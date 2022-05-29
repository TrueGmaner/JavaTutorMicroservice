package ru.german.springprograms.mapper;

import org.springframework.stereotype.Component;
import ru.german.springprograms.dto.BusinessLicenseDto;
import ru.german.springprograms.entities.BusinessLicense;

@Component
public class BusinessLicenseMapper {
    public BusinessLicenseDto mapToDto(BusinessLicense businessLicense){
        return BusinessLicenseDto.builder()
                .id(businessLicense.getId())
                .programName(businessLicense.getProgramName())
                .usersLimit(businessLicense.getUsersLimit())
                .price(businessLicense.getPrice())
                .dateStart(businessLicense.getDateStart())
                .dateEnd(businessLicense.getDateEnd())
                .organizationNumber(businessLicense.getOrganizationNumber())
                .condition(businessLicense.getCondition())
                .build();
    }
}

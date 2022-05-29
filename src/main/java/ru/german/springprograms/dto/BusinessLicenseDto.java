package ru.german.springprograms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessLicenseDto {
    private Integer id;

    private String programName;

    private Integer usersLimit;

    private Integer price;

    private String dateStart;

    private String dateEnd;

    private Integer organizationNumber;

    private String condition;
}

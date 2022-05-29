package ru.german.springprograms.entities;

import ru.german.springprograms.dto.BusinessLicenseDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BusinessLicense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column
    private String programName;

    @Column
    private Integer usersLimit;

    @Column
    private Integer price;

    @Column
    private String dateStart;

    @Column
    private String dateEnd;

    @Column
    private Integer organizationNumber;

    @Column
    private String condition;

    @ManyToMany (mappedBy = "businessLicenses")
    private Set<StudyProgram> studyPrograms = new HashSet<>();

    public BusinessLicense(){

    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Integer getUsersLimit() {
        return usersLimit;
    }

    public void setUsersLimit(Integer usersLimit) {
        this.usersLimit = usersLimit;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getOrganizationNumber() {
        return organizationNumber;
    }

    public void setOrganizationNumber(Integer organizationNumber) {
        this.organizationNumber = organizationNumber;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void updateFromDto(BusinessLicenseDto businessLicenseDto){
        this.programName = businessLicenseDto.getProgramName();
        this.usersLimit = businessLicenseDto.getUsersLimit();
        this.price = businessLicenseDto.getPrice();
        this.dateStart = businessLicenseDto.getDateStart();
        this.dateEnd = businessLicenseDto.getDateEnd();
        this.organizationNumber = businessLicenseDto.getOrganizationNumber();
    }
}

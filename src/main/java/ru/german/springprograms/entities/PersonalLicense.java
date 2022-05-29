package ru.german.springprograms.entities;

import ru.german.springprograms.dto.PersonalLicenseDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PersonalLicense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column
    private String programName;

    @Column
    private Integer price;

    @Column
    private String dateStart;

    @Column
    private String dateEnd;

    @Column
    private Integer userId;

    @Column
    private String condition;

    @ManyToMany (mappedBy = "personalLicenses")
    private Set<StudyProgram> studyPrograms = new HashSet<>();

    public PersonalLicense(){

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void updateFromDto(PersonalLicenseDto personalLicenseDto){
        this.programName = personalLicenseDto.getProgramName();
        this.userId = personalLicenseDto.getUserId();
        this.price = personalLicenseDto.getPrice();
        this.dateStart = personalLicenseDto.getDateStart();
        this.dateEnd = personalLicenseDto.getDateEnd();
    }
}

package ru.german.springprograms.entities;

import ru.german.springprograms.dto.StudyProgramDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class StudyProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column(unique = true)
    private String name;

    @Column
    private Integer price;

    @OneToOne(mappedBy = "studyProgram")
    private InstituteAccess instituteAccess;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Program_BusinessLicense",
            joinColumns = {@JoinColumn (name = "program_id")},
            inverseJoinColumns = {@JoinColumn (name = "businessLicense_id")}
    )
    Set<BusinessLicense> businessLicenses = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Program_PersonalLicense",
            joinColumns = {@JoinColumn (name = "program_id")},
            inverseJoinColumns = {@JoinColumn (name = "personalLicanse_id")}
    )
    Set<PersonalLicense> personalLicenses = new HashSet<>();

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name="Program_GroupProgram",
            joinColumns = {@JoinColumn (name = "program_id")},
            inverseJoinColumns = {@JoinColumn (name = "groupProgram_id")}
    )
    Set<GroupProgram> groupPrograms = new HashSet<>();

    public StudyProgram() {
    }

    public StudyProgram(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void updateFromDto(StudyProgramDto studyProgramDto){
        this.name = studyProgramDto.getName();
    }

}

package ru.german.springprograms.entities;



import ru.german.springprograms.dto.InstituteAccessDto;

import javax.persistence.*;

@Entity
public class InstituteAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "programName")
    private String programName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "studyProgramId", referencedColumnName = "id")
    private StudyProgram studyProgram;

    public InstituteAccess(){

    }

    public InstituteAccess(String programName){
        this.programName = programName;
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

    public void updateFromDto(InstituteAccessDto instituteAccessDto){
        this.programName = instituteAccessDto.getProgramName();
    }
}

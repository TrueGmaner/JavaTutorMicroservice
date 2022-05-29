package ru.german.springprograms.entities;

import ru.german.springprograms.dto.GroupProgramDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class GroupProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    Integer id;

//    @Column
//    Integer idProgram;

    @Column
    Integer idGroup;

    @ManyToOne
    private StudyProgram studyProgram = null;

    public GroupProgram (){

    }

    public GroupProgram(StudyProgram studyProgram, Integer idGroup){
        this.studyProgram=studyProgram;
        this.idGroup=idGroup;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StudyProgram getStudyProgram() {
        return studyProgram;
    }

    public void setStudyProgram(StudyProgram studyProgram) {
        this.studyProgram = studyProgram;
    }

    //    public Integer getIdProgram() {
//        return idProgram;
//    }
//
//    public void setIdProgram(Integer idProgram) {
//        this.idProgram = idProgram;
//    }

    public Integer getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Integer idGroup) {
        this.idGroup = idGroup;
    }

//    public void updateFromDto(GroupProgramDto groupProgramDto){
//        this.idProgram=groupProgramDto.getIdProgram();
//        this.idGroup=groupProgramDto.getIdGroup();
//    }
}

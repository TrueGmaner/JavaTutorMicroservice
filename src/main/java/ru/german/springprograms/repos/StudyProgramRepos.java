package ru.german.springprograms.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.german.springprograms.entities.StudyProgram;

import java.util.Optional;

@Repository
public interface StudyProgramRepos extends JpaRepository<StudyProgram, Integer> {
    Optional<StudyProgram> findById(Integer id);

    Optional<StudyProgram> findByName(String name);
}

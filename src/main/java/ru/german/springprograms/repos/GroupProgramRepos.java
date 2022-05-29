package ru.german.springprograms.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.german.springprograms.entities.GroupProgram;

@Repository
public interface GroupProgramRepos extends JpaRepository<GroupProgram, Integer> {
}

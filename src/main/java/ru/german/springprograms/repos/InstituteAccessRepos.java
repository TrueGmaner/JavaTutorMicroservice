package ru.german.springprograms.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.german.springprograms.entities.InstituteAccess;

import java.util.Optional;

@Repository
public interface InstituteAccessRepos extends JpaRepository<InstituteAccess, Integer> {
    Optional<InstituteAccess> findByProgramName(String programName);
}

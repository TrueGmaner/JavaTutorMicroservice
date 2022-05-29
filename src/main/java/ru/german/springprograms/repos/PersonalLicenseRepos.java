package ru.german.springprograms.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.german.springprograms.entities.PersonalLicense;

@Repository
public interface PersonalLicenseRepos extends JpaRepository<PersonalLicense, Integer> {
}

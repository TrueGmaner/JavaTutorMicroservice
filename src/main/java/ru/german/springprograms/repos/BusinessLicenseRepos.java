package ru.german.springprograms.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.german.springprograms.entities.BusinessLicense;

@Repository
public interface BusinessLicenseRepos extends JpaRepository<BusinessLicense, Integer> {
}

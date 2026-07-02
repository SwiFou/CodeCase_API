package fr.swif.codecase_api.repository;

import fr.swif.codecase_api.model.Langage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Repository est un stéréotype Spring qui marque une classe
// comme couche d'accès aux données (DAO)
@Repository
public interface LangageRepository extends JpaRepository<Langage, Integer> {

}

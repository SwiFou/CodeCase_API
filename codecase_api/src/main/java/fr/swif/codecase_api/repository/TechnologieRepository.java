package fr.swif.codecase_api.repository;

import fr.swif.codecase_api.model.Technologie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * TechnologieRepository
 *<i>de fr.swif.codecase_api.repository</i>
 *<hr>
 *<p>Interface CRUD pour Technologie</p>
 *
 *@author Calderoli Alexandre
 *@version 0.0.1
 *@since 02/07/2026
 */

// @Repository est un stéréotype Spring qui marque une classe
// comme couche d'accès aux données (DAO)
@Repository
public interface TechnologieRepository extends JpaRepository<Technologie, Integer> {

}

package fr.swif.codecase_api.repository;

import fr.swif.codecase_api.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * UserRepository
 *<i>de fr.swif.codecase_api.repository</i>
 *<hr>
 *<p>Interface CRUD pour User</p>
 *
 *@author Calderoli Alexandre
 *@version 0.0.1
 *@since 12/06/2026
 */

// @Repository est un stéréotype Spring qui marque une classe comme couche d'accès aux données (DAO)
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}

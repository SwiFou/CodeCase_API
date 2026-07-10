package fr.swif.codecase_api.repository;

import fr.swif.codecase_api.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
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

// @Repository est un stéréotype Spring qui marque une classe
// comme couche d'accès aux données (DAO)
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  /**
   * Méthode findByUserEmail
   *
   *<i>de UserRepository</i>
   *<h1></h1>
   *<hr>
   *<p>Méthode personnalisée permettant de chercher un utilisateur par rapport
   * à son adresse mail</p>
   * @param userEmail L'email de l'utilisateur à rechercher
   * @return Un Optional (C'est un conteneur qui peut contenir soit une valeur,
   * soit rien).
   */
  Optional<User> findByUserEmail(String userEmail);
}
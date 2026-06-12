package fr.swif.codecase_api.service;

import fr.swif.codecase_api.model.User;
import fr.swif.codecase_api.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * UserService
 * <i>de fr.swif.codecase_api.service</i>
 * <hr>
 * <p>Service pour appliquer les traitements métier de User</p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 12/06/2026
 */

// @RequiredArgsConstructor génère automatiquement un constructeur prenant en
// paramètre tous les champs final et @NotNull de la classe
@RequiredArgsConstructor
// @Service sert à indiquer que la classe détient la logique métier du CRUD
@Service
public class UserService {

  private final UserRepository userRepository;

  /**
   * Méthode getUsers
   *
   *<i>de UserService</i>
   *<h1></h1>
   *<hr>
   *<p>Va chercher tous les Users dans la table grâce à findAll()</p>
   * @return Un Iterable composé de Users → c'est une interface qui
   * représente "quelque chose qu'on peut parcourir élément par élément"
   * Comme une arraylist mais peut parcourir n'importe quelle collection.
   */
  public Iterable<User> getUsers() {
    return userRepository.findAll();
  }

  /**
   * Méthode getUser
   *
   *<i>de UserService</i>
   *<h1></h1>
   *<hr>
   *<p>Prend un id et renvoie le User en question s'il existe grâce à findById()</p>
   * @param id l'id du User cherché
   * @return Un Optional de User qui sert à gérer explicitement null au lieu
   * d'avoir une erreur NullPointerException → "cette méthode peut ne rien retourner, gère-le".
   */
  public Optional<User> getUser(int id) {
    return userRepository.findById(id);
  }

  /**
   * Méthode saveUser
   *
   *<i>de UserService</i>
   *<h1></h1>
   *<hr>
   *<p>Prends un objet User et le sauvegarde ou le mets à jour dans la BDD grâce à save()</p>
   * @param user le User à créer ou modifier
   * @return Le User créé ou modifié
   */
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  /**
   * Méthode deleteUser
   *
   *<i>de UserService</i>
   *<h1></h1>
   *<hr>
   *<p>Prend l'id d'un User et le supprime grâce à deleteById()</p>
   * @param id L'id du User à supprimer
   */
  public void deleteUser(int id) {
    userRepository.deleteById(id);
  }


}

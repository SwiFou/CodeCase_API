package fr.swif.codecase_api.controller;

import fr.swif.codecase_api.exception.ExceptionManager;
import fr.swif.codecase_api.model.User;
import fr.swif.codecase_api.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserRestController
 * <i>de fr.swif.codecase_api.controller</i>
 * <hr>
 * <p></p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 15/06/2026
 */

// @RestController est une combinaison de :
// - @Controller qui marque la classe comme composant Spring MVC gérant les requêtes HTTP
// - @ResponseBody qui indique que la valeur retournée par chaque méthode
// est sérialisée directement dans le corps de la réponse HTPP
// (JSON par défaut avec Jackson), au lieu d'être interprétée comme un nom de vue Thymeleaf/JSP
@RestController
// @RequiredArgsConstructor génère automatiquement un constructeur prenant en paramètre tous les champs final et @NotNull de la classe
@RequiredArgsConstructor

public class UserRestController {

  private final UserService userService;

  /**
   * Méthode pour la création d'un User.
   *
   *<i>de UserRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Création d'un User</p>
   * @param user
   * @return
   */
  @PostMapping("/user")
  public ResponseEntity<User> createUser(@RequestBody User user) {
    try {
      return ResponseEntity.ok(userService.saveUser(user));
    } catch (Exception e) {
      return ExceptionManager.handleException(e);
    }
  }

  /**
   * Méthode pour lister tous les Users.
   *
   *<i>de UserRestController</i>
   *<h1>Liste tous les Users</h1>
   *<hr>
   *<p></p>
   * @return
   */
  @GetMapping("/users")
  public ResponseEntity<Iterable<User>> getUsers() {
    try {
      return ResponseEntity.ok(userService.getUsers());
    } catch (Exception e) {
      e.printStackTrace();
      return ExceptionManager.handleException(e);
    }
  }

  /**
   * Méthode pour lister un User par rapport à son id.
   *
   *<i>de UserRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Liste un User par rapport à son id</p>
   * @param id
   * @return
   */
  @GetMapping("/user/{id}")
  public ResponseEntity<User> getUser(@PathVariable("id") int id) {
    try {
      return ResponseEntity.ok(userService.getUser(id));
    } catch (Exception e) {
      return ExceptionManager.handleException(e);
    }
  }

  /**
   * Méthode pour mettre à jour les données d'un User
   *
   *<i>de UserRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Met à jour les données d'un User</p>
   * @param id
   * @param user
   * @return
   */
  @PutMapping("/user/{id}")
  public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User user) {
    try {
      return ResponseEntity.ok(userService.updateUser(id, user));
    } catch (Exception e) {
      return ExceptionManager.handleException(e);
    }
  }

  /**
   * Méthode pour anonymiser un User
   *
   *<i>de UserRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Anonymise un User</p>
   * @param id
   * @return
   */
  @PutMapping("/user_anonym/{id}")
  public ResponseEntity<String> anonymisationUser(@PathVariable("id") int id) {
    try {
      userService.anonymisationUser(id);
      return ResponseEntity.ok("Utilisateur anonymisé avec succès");
    } catch (Exception e) {
      return ExceptionManager.handleException(e);
    }
  }

  /**
   * Méthode pour supprimer un User
   *
   *<i>de UserRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Supprime un User</p>
   * @param id
   * @return
   */
  @DeleteMapping("/user/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
    try {
      userService.deleteUser(id);
      return ResponseEntity.ok("User supprimé avec succès");
    } catch (Exception e) {
      return ExceptionManager.handleException(e);
    }
  }
}

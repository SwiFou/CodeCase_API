package fr.swif.codecase_api.controller;

import fr.swif.codecase_api.exception.CodeCaseApiException;
import fr.swif.codecase_api.model.User;
import fr.swif.codecase_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
// - @Controller qui marque la classe comme composant Spring MVC
// gérant les requêtes HTTP
// - @ResponseBody qui indique que la valeur retournée par chaque méthode
// est sérialisée (conversion de l'objet en JSON) directement dans le corps
// de la réponse HTTP (JSON par défaut avec Jackson), au lieu d'être interprétée
// comme un nom de vue Thymeleaf/JSP
@RestController
// @RequiredArgsConstructor génère automatiquement un constructeur prenant
// en paramètre tous les champs final et @NotNull de la classe
@RequiredArgsConstructor

public class UserRestController {

  private final UserService userService;

  /**
   * Méthode pour la création d'un User.
   *
   *<i>de UserRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Création d'un User
   * Cette méthode ne lève que des unchecked exceptions</p>
   * @param user
   * @return
   */
  @PostMapping("/user")
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  // @Valid permet de déclencher la Bean Validation sur l'objet qu'il annote
  public ResponseEntity<User> createUser(@Valid @RequestBody User user)
      throws CodeCaseApiException {
      return ResponseEntity.ok(userService.saveUser(user));
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
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  public ResponseEntity<Iterable<User>> getUsers() throws CodeCaseApiException{
      return ResponseEntity.ok(userService.getUsers());
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
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  public ResponseEntity<User> getUser(@PathVariable("id") int id)
      throws CodeCaseApiException{
      return ResponseEntity.ok(userService.getUser(id));
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
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  // @Valid permet de déclencher la Bean Validation sur l'objet qu'il annote
  public ResponseEntity<User> updateUser(@PathVariable("id") int id,
      @Valid @RequestBody User user) throws CodeCaseApiException{
      return ResponseEntity.ok(userService.updateUser(id, user));
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
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  public ResponseEntity<String> anonymisationUser(@PathVariable("id") int id)
      throws CodeCaseApiException {
      userService.anonymisationUser(id);
      return ResponseEntity.ok("Utilisateur anonymisé avec succès");
  }

  /**
   * Méthode pour supprimer un User
   *
   *<i>de UserRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Supprime un User
   * Cette méthode ne lève que des unchecked exceptions</p>
   * @param id
   * @return
   */
  @DeleteMapping("/user/{id}")
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  public ResponseEntity<String> deleteUser(@PathVariable("id") int id)
      throws CodeCaseApiException{
      userService.deleteUser(id);
      return ResponseEntity.ok("User supprimé avec succès");
  }
}

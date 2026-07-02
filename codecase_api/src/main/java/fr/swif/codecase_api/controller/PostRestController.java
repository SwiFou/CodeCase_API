package fr.swif.codecase_api.controller;

import fr.swif.codecase_api.exception.CodeCaseApiException;
import fr.swif.codecase_api.exception.ExceptionManager;
import fr.swif.codecase_api.model.Post;
import fr.swif.codecase_api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserRestController
 * <i>de fr.swif.codecase_api.controller</i>
 * <hr>
 * <p>Controller REST pour les endpoints Post</p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 11/06/2026
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
// en paramètre tous les champs final et @NonNull de la classe
@RequiredArgsConstructor
public class PostRestController {

  private final PostService postService;

  /**
   * Méthode pour la création d'un post
   *
   *<i>de UserRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Création d'un post
   * Cette méthode ne lève que des unchecked exceptions</p>
   * @param post
   * @return
   */
  @PostMapping("/post")
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  // @Valid permet de déclencher la Bean Validation sur l'objet qu'il annote
  public ResponseEntity<Post> createPost(@Valid @RequestBody Post post) {
      return ResponseEntity.ok(postService.savePost(post));
  }

  /**
   * Méthode pour lister tous les posts
   *
   *<i>de UserRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Liste tous les posts</p>
   * @return
   */
  @GetMapping("/posts")
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  public ResponseEntity<Iterable<Post>> getPosts() throws CodeCaseApiException {
      return ResponseEntity.ok(postService.getPosts());
  }

  /**
   * Méthode pour lister un post par rapport à son id
   *
   *<i>de UserRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Liste un post par rapport à son id</p>
   * @param id
   * @return
   */
  @GetMapping("/post/{id}")
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  public ResponseEntity<Post> getPost(@PathVariable("id") int id)
      throws CodeCaseApiException {
      return ResponseEntity.ok(postService.getPost(id));
  }

  /**
   * Méthode pour supprimer un post par rapport à son id
   *
   *<i>de UserRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Supprime un post par rapport à son id
   * Cette méthode ne lève que des unchecked exceptions</p>
   * @param id
   * @return
   */
  @DeleteMapping("/post/{id}")
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  public ResponseEntity<String> deletePost(@PathVariable("id") int id)
      throws CodeCaseApiException{
      postService.deletePost(id);
      return ResponseEntity.ok("Post supprimé");
  }
}

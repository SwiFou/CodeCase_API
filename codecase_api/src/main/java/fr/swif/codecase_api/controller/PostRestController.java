package fr.swif.codecase_api.controller;

import fr.swif.codecase_api.exception.ExceptionManager;
import fr.swif.codecase_api.model.Post;
import fr.swif.codecase_api.service.PostService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
// - @Controller qui marque la classe comme composant Spring MVC gérant les requêtes HTTP
// - @ResponseBody qui indique que la valeur retournée par chaque méthode
// est sérialisée directement dans le corps de la réponse HTTP
// (JSON par défaut avec Jackson), au lieu d'être interprétée comme un nom de vue Thymeleaf/JSP
@RestController
// @RequiredArgsConstructor génère automatiquement un constructeur prenant en paramètre tous les champs final et @NonNull de la classe
@RequiredArgsConstructor
public class PostRestController {

  private final PostService postService;

  /**
   * Méthode pour la création d'un post
   *
   *<i>de UserRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Création d'un post</p>
   * @param post
   * @return
   */
  @PostMapping("/post")
  public ResponseEntity<Post> createPost(@RequestBody Post post) {
    try {
      return ResponseEntity.ok(postService.savePost(post));
    } catch (Exception e) {
      return ExceptionManager.handleException(e);
    }
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
  public ResponseEntity<Iterable<Post>> getPosts() {
    try {
      return ResponseEntity.ok(postService.getPosts());
    } catch (Exception e) {
      e.printStackTrace();
      return ExceptionManager.handleException(e);
    }
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
  public ResponseEntity<Post> getPost(@PathVariable("id") int id) {
    try {
      Optional<Post> post = postService.getPost(id);
      if(post.isPresent()) {
        return ResponseEntity.ok(post.get());
      } else {
        return new ResponseEntity("Post non présent", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return ExceptionManager.handleException(e);
    }
  }

  /**
   * Méthode pour supprimer un post par rapport à son id
   *
   *<i>de UserRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Supprime un post par rapport à son id</p>
   * @param id
   * @return
   */
  @DeleteMapping("/post/{id}")
  public ResponseEntity<String> deletePost(@PathVariable("id") int id) {
    try {
      postService.deletePost(id);
      return ResponseEntity.ok("Post supprimé");
    } catch (Exception e) {
      return ExceptionManager.handleException(e);
    }
  }
}

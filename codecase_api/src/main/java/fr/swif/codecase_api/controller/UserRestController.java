package fr.swif.codecase_api.controller;

import fr.swif.codecase_api.model.Langage;
import fr.swif.codecase_api.model.Post;
import fr.swif.codecase_api.model.User;
import fr.swif.codecase_api.service.PostService;
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
 * @since 11/06/2026
 */

// @RestController est une combinaison de :
// @Controller qui marque la classe comme composant Spring MVC gérant les requêtes HTTP
// et de @ResponseBody qui indique que la valeur retournée par chaque méthode
// est sérialisée directement dans le corps de la réponse HTTP
// (JSON par défaut avec Jackson), au lieu d'être interprétée comme un nom de vue Thymeleaf/JSP
@RestController
// @RequiredArgsConstructor génère automatiquement un constructeur prenant en paramètre tous les champs final et @NonNull de la classe
@RequiredArgsConstructor
public class UserRestController {

  private final PostService postService;

  @PostMapping("/post")
  public ResponseEntity<Post> createPost(@RequestBody Post post) {
    try {
      return ResponseEntity.ok(postService.savePost(post));
    } catch (Exception e) {
      return ExceptionManager.handleException(e);
    }
  }

  @GetMapping("/posts")
  public ResponseEntity<Iterable<Post>> getPosts() {
    try {
      return ResponseEntity.ok(postService.getPosts());
    } catch (Exception e) {
      e.printStackTrace();
      return ExceptionManager.handleException(e);
    }
  }

  @GetMapping("/post/{id}")
  public ResponseEntity<Post> getPost(@PathVariable("id") int id) {
    try {
      Optional<Post> post = postService.getPost(id);
      if(post.isPresent()) {
        return ResponseEntity.ok(post.get());
      } else {
        return new ResponseEntity<>("Post non présent", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return ExceptionManager.handleException(e);
    }
  }

  @PutMapping("/post/{id}")
  public ResponseEntity<Post> updatePost(@PathVariable("id") int id,
    @RequestBody Post post) {

    try {
      Optional<Post> temp = postService.getPost(id);

      if(temp.isPresent()) {
        Post current = temp.get();

        String titrePost = post.getPostTitre();
        String descriptionPost = post.getPostDescription();
        User user = post.getUserId();
        String contenuPost = post.getPostContenu();
        Langage langagePost = post.getLangageId();
      }
    }
  }

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

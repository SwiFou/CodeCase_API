package fr.swif.codecase_api.service;

import fr.swif.codecase_api.model.Post;
import fr.swif.codecase_api.repository.PostRepository;
import java.util.Optional;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * PostService
 * <i>de fr.swif.codecase_api.service</i>
 * <hr>
 * <p></p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 11/06/2026
 */

// @Data est l'équivalent de @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@Data
// @Service sert à indiquer que la classe détient la logique métier du CRUD
@Service
public class PostService {

  private final PostRepository postRepository;

  /**
   * Méthode getPost.
   *
   *<i>de PostService</i>
   *<hr>
   *<p>Va chercher tous les Posts dans la table grâce à findAll()</p>
   * @return Un Iterable composé de Posts → c'est une interface qui
   * représente "quelque chose qu'on peut parcourir élément par élément"
   * Comme une Arraylist mais peut parcourir n'importe quelle collection.
   */
  public Iterable<Post> getPosts() {
    return postRepository.findAll();
  }

  /**
   * Méthode getPost.
   *
   *<i>de PostService</i>
   *<hr>
   *<p>Prends un id et renvoie le Post en question s'il existe grâce à findById()</p>
   * @param id l'id du post cherché
   * @return Un Optional de Posts qui sert à gérer explicitement null au lieu
   * d'avoir une erreur NullPointerException (cette méthode peut ne rien retourner, gère-le).
   */
  public Optional<Post> getPost(int id) {
    return postRepository.findById(id);
  }

  /**
   * Méthode savePost.
   *
   *<i>de PostService</i>
   *<hr>
   *<p>Prends un Objet Post et le sauvegarde ou le mets à jour dans la BDD grâce à save()</p>
   * @param post le Post à créer ou modifier
   * @return Le Post créé ou modifié
   */
  public Post savePost(Post post) {
    return postRepository.save(post);
  }

  /**
   * Méthode deletePost.
   *
   *<i>de PostService</i>
   *<hr>
   *<p>Prends l'id d'un Post et le supprime grâce à deleteById()</p>
   * @param id L'id du Post à supprimer
   */
  public void deletePost(int id) {
    postRepository.deleteById(id);
  }
}

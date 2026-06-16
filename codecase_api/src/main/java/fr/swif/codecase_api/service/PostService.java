package fr.swif.codecase_api.service;

import fr.swif.codecase_api.exception.CodeCaseException;
import fr.swif.codecase_api.model.Post;
import fr.swif.codecase_api.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * PostService
 * <i>de fr.swif.codecase_api.service</i>
 * <hr>
 * <p>Service pour appliquer les traitements métier de Post</p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 11/06/2026
 */

// @RequiredArgsConstructor génère automatiquement un constructeur prenant en
// paramètre tous les champs final et @NotNull de la classe
@RequiredArgsConstructor
// @Service sert à indiquer que la classe détient la logique métier du CRUD
@Service
// @Transactional permet de garantir une transaction :
// - Si tout se passe bien → COMMIT automatique à la fin
// - Si une exception est levée → ROLLBACK automatique (tout est annulé)
// Ici mis en readOnly par défaut pour les méthodes findAll() et findById()
// qui indique que celles-ci ne sont pas destinées à être modifées, donc réduit
// les traitements internes et accorde des optimisations de performances
@Transactional(readOnly = true)
public class PostService {

  private final PostRepository postRepository;

  /**
   * Méthode getPosts.
   *
   *<i>de PostService</i>
   *<h1></h1>
   *<hr>
   *<p>Va chercher tous les Posts dans la table grâce à findAll()</p>
   * @return Un Iterable composé de Posts → c'est une interface qui
   * représente "quelque chose qu'on peut parcourir élément par élément"
   * Comme une Arraylist mais peut parcourir n'importe quelle collection.
   * @throws CodeCaseException
   */
  // Iterable → Interface qui peut être parcourue
  public Iterable<Post> getPosts() throws CodeCaseException {
    Iterable<Post> posts = postRepository.findAll();
    if(!posts.iterator().hasNext()) {
      // iterator() -> curseur positionné au début de la collection
      // hasNext() -> retourne true s'il y a au moins 1 élément à partir
      // de la position actuelle
      throw new CodeCaseException("Aucuns posts trouvés", HttpStatus.NOT_FOUND);
    }
    return posts;
  }

  /**
   * Méthode getPost.
   *
   *<i>de PostService</i>
   *<h1></h1>
   *<hr>
   *<p>Prend un id et renvoie le Post en question s'il existe grâce à findById()</p>
   * @param id l'id du Post cherché
   * @return Un Optional de Post qui sert à gérer explicitement null au lieu
   * d'avoir une erreur NullPointerException → "cette méthode peut ne rien retourner, gère-le".
   * @throws CodeCaseException
   */
  public Post getPost(int id) throws CodeCaseException{
    return postRepository.findById(id)
        .orElseThrow(() -> new CodeCaseException("Post introuvable : " + id, HttpStatus.NOT_FOUND));
  }

  /**
   * Méthode savePost.
   *
   *<i>de PostService</i>
   *<hr>
   *<p>Prend un Objet Post et le sauvegarde ou le mets à jour dans la BDD grâce à save()</p>
   * @param post le Post à créer ou modifier
   * @return Le Post créé ou modifié
   */
  // @Transactional surcharge le readOnly de la classe
  @Transactional
  public Post savePost(Post post) {
    return postRepository.save(post);
  }

  /**
   * Méthode deletePost.
   *
   *<i>de PostService</i>
   *<hr>
   *<p>Prend l'id d'un Post et le supprime grâce à deleteById()</p>
   * @param id L'id du Post à supprimer
   */
  // @Transactional surcharge le readOnly de la classe
  @Transactional
  public void deletePost(int id) {
    postRepository.deleteById(id);
  }
}

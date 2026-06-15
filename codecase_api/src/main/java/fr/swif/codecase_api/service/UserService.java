package fr.swif.codecase_api.service;

import fr.swif.codecase_api.model.User;
import fr.swif.codecase_api.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
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
// @Transactional permet de garantir une transaction :
// - Si tout se passe bien → COMMIT automatique à la fin
// - Si une exception est levée → ROLLBACK automatique (tout est annulé)
// Ici mis en readOnly par défaut pour les méthodes findAll() et findById()
// qui indique que celles-ci ne sont pas destinées à être modifées, donc réduit
// les traitements internes et accorde des optimisations de performances
@Transactional(readOnly = true)
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
  // Iterable → Interface qui peut être parcourue
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
  // Optional → Conteneur qui contient soit une valeur, soit rien
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
  // @Transactional surcharge le readOnly de la classe
  @Transactional
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  /**
   * Méthode updateUser
   *
   *<i>de UserService</i>
   *<h1></h1>
   *<hr>
   *<p>Prend l'id d'un User et met à jour les informations suivantes :
   * Email, Mot de passe, Avatar</p>
   * @param id
   * @return
   */
  @Transactional
  public User updateUser(int id) {

    Optional<User> user = getUser(id);

    if(user.isPresent()) {

      User userActuel = user.get();

      String userEmail = userActuel.getUserEmail();
      String userMdp = userActuel.getUserMdp();
      String userAvatar = userActuel.getUserAvatar();

      if(userEmail != null) {
        userActuel.setUserEmail(userEmail);
      }

      if(userMdp != null) {
        userActuel.setUserMdp(userMdp);
      }

      if(userAvatar != null) {
        userActuel.setUserAvatar(userAvatar);
      }
    }
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
  // @Transactional surcharge le readOnly de la classe
  @Transactional
  public void deleteUser(int id) {
    userRepository.deleteById(id);
  }

  /**
   * Méthode anonymisationUser
   *
   *<i>de UserService</i>
   *<h1></h1>
   *<hr>
   *<p>Prend l'id d'un User et anonymise les informations suivantes :
   * Pseudo, Mot de passe, Email</p>
   * @param id L'id du User à anonymiser
   */
  // @Transactional surcharge le readOnly de la classe
  @Transactional
  public void anonymisationUser(int id) {

    Optional<User> user = getUser(id);

    if(user.isPresent()) {

      User userExistant = user.get();

      String userPseudo = userExistant.getUserPseudo();
      String userMdp = userExistant.getUserMdp();
      String userEmail = userExistant.getUserEmail();

      if(userPseudo != null) {
        userExistant.setUserPseudo("Utilisateur supprimé-" + id);
      }

      // Permet de remplacer le hash du mot de passe valide en
      // un hash invalide et inutilisable
      if(userMdp != null) {
        userExistant.setUserMdp("{erased}");
      }

      if(userEmail != null) {
        userExistant.setUserEmail("deleted-" + id + "@anonymized.invalid");
      }
    }
  }

}

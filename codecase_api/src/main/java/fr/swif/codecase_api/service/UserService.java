package fr.swif.codecase_api.service;

import fr.swif.codecase_api.exception.CodeCaseApiException;
import fr.swif.codecase_api.exception.MessagesErreur;
import fr.swif.codecase_api.model.User;
import fr.swif.codecase_api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
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
// @Service sert à indiquer que la classe détient la logique métier
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
   * Méthode getUsers.
   *
   *<i>de UserService</i>
   *<h1></h1>
   *<hr>
   *<p>Va chercher tous les Users dans la table grâce à findAll()</p>
   * @return Un Iterable composé de Users → c'est une interface qui
   * représente "quelque chose qu'on peut parcourir élément par élément"
   * Comme une arraylist mais peut parcourir n'importe quelle collection.
   * @throws CodeCaseApiException
   */
  // Iterable → Interface qui peut être parcourue
  public Iterable<User> getUsers() throws CodeCaseApiException {
    Iterable<User> users = userRepository.findAll();
    if(!users.iterator().hasNext()) {
      // iterator() -> curseur positionné au début de la collection
      // hasNext() -> retourne true s'il y a au moins 1 élément à partir
      // de la position actuelle
      throw new CodeCaseApiException(MessagesErreur.ALL_USERS_NOT_FOUND);
    }
    return users;
  }

  /**
   * Méthode getUser.
   *
   *<i>de UserService</i>
   *<h1></h1>
   *<hr>
   *<p>Prend un id et renvoie le User en question
   * s'il existe grâce à findById()</p>
   * @param id l'id du User cherché
   * @return
   * @throws CodeCaseApiException
   */
  public User getUser(int id) throws CodeCaseApiException {
    return userRepository.findById(id)
        .orElseThrow(() -> new CodeCaseApiException(
            MessagesErreur.USER_NOT_FOUND));
  }

  /**
   * Méthode saveUser.
   *
   *<i>de UserService</i>
   *<h1></h1>
   *<hr>
   *<p>Prends un objet User et le sauvegarde
   * ou le mets à jour dans la BDD grâce à save()</p>
   * @param user le User à créer ou modifier
   * @return Le User créé ou modifié
   */
  // @Transactional surcharge le readOnly de la classe
  @Transactional
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  /**
   * Méthode updateUser.
   *
   *<i>de UserService</i>
   *<h1></h1>
   *<hr>
   *<p>Prend l'id d'un User et met à jour les informations suivantes :
   * Email, Mot de passe, Avatar</p>
   * @param id L'id du User qui met à jour
   * @param user L'objet User qui est mis à jour
   * @return Le User modifié
   * @throws CodeCaseApiException
   */
  @Transactional
  public User updateUser(int id, User user) throws CodeCaseApiException {

    User userActuel = getUser(id);

      if(user.getUserEmail() != null) {
        userActuel.setUserEmail(user.getUserEmail());
      }

      if(user.getUserMdp() != null) {
        userActuel.setUserMdp(user.getUserMdp());
      }

      if(user.getUserAvatar() != null) {
        userActuel.setUserAvatar(user.getUserAvatar());
      }
    return userRepository.save(userActuel);
  }

  /**
   * Méthode deleteUser.
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
   * Méthode anonymisationUser.
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
  public void anonymisationUser(int id) throws CodeCaseApiException {

    User userExistant = getUser(id);

    if(userExistant.getUserPseudo() != null) {
      userExistant.setUserPseudo("Utilisateur supprimé-");
    }

    // Permet de remplacer le hash du mot de passe valide en
    // un hash invalide et inutilisable
    if(userExistant.getUserMdp() != null) {
      userExistant.setUserMdp("erased");
    }

    // Le .invalid est destiné aux utilisations de constructions en ligne
    // de noms de domaines dont il est sûr qu'ils sont invalides
    // voir http://abcdrfc.free.fr/rfc-vf/rfc2606.html
    if(userExistant.getUserEmail() != null) {
      userExistant.setUserEmail("deleted-@anonymized.invalid");
    }
    userRepository.save(userExistant);
  }
}

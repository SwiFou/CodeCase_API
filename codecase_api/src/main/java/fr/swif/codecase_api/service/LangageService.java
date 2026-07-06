package fr.swif.codecase_api.service;

import fr.swif.codecase_api.exception.CodeCaseApiException;
import fr.swif.codecase_api.exception.MessagesErreur;
import fr.swif.codecase_api.model.Langage;
import fr.swif.codecase_api.repository.LangageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * LangageService
 * <i>de fr.swif.codecase_api.service</i>
 * <hr>
 * <p>Service pour appliquer les traitements métier de Langage</p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 02/07/2026
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
public class LangageService {

  private final LangageRepository langageRepository;

  /**
   * Méthode getLangages.
   *
   *<i>de LangageService</i>
   *<h1></h1>
   *<hr>
   *<p>Va chercher tous les Langages dans la table grâce à findAll()</p>
   * @return Un Iterable composé de Langages → c'est une interface qui
   * représente "quelque chose qu'on peut parcourir élément par élément"
   * Comme une Arraylist mais peut parcourir n'importe quelle collection.
   * @throws CodeCaseApiException
   */
  // Iterable → Interface qui peut être parcourue
  public Iterable<Langage> getLangages() throws CodeCaseApiException {
    Iterable<Langage> langages = langageRepository.findAll();
    if(!langages.iterator().hasNext()) {
      // iterator() -> curseur positionné au début de la collection
      // hasNext() -> retourne true s'il y a au moins 1 élément à partir
      // de la position actuelle
      throw new CodeCaseApiException(MessagesErreur.ALL_LANGAGES_NOT_FOUND);
    }
    return langages;
  }

  /**
   * Méthode getLangage
   *
   *<i>de LangageService</i>
   *<h1></h1>
   *<hr>
   *<p>Prend un id et renvoie le Langage en question
   * s'il existe grâce à findById()</p>
   * @param id l'id du Langage cherché
   * @return Le Langage cherché
   * @throws CodeCaseApiException
   */
  public Langage getLangage(int id) throws CodeCaseApiException {
    return langageRepository.findById(id)
        .orElseThrow(() -> new CodeCaseApiException(
            MessagesErreur.LANGAGE_NOT_FOUND));
  }

  /**
   * Méthode saveLangage
   *
   *<i>de LangageService</i>
   *<h1></h1>
   *<hr>
   *<p>Prend un Objet Langage et le sauvegarde ou
   * le mets à jour dans la BDD grâce à save()
   * Cette méthode ne lève que des unchecked exceptions</p>
   * @param langage Le Langage à créer ou modifier
   * @return Le Langage créé ou modifié
   */
  // @Transactional surcharge le readOnly de la classe
  @Transactional
  public Langage saveLangage(Langage langage) {
    return langageRepository.save(langage);
  }

  /**
   * Méthode deleteLangage
   *
   *<i>de LangageService</i>
   *<h1></h1>
   *<hr>
   *<p>Prend l'id d'un Langage et le supprime grâce à deleteById()</p>
   * @param id L'id du Langage à supprimer
   * @throws CodeCaseApiException
   */
  // @Transactional surcharge le readOnly de la classe
  @Transactional
  public void deleteLangage(int id) throws CodeCaseApiException{
    if (!langageRepository.existsById(id)) {
      throw new CodeCaseApiException(MessagesErreur.LANGAGE_NOT_FOUND);
    }
    langageRepository.deleteById(id);
  }

}

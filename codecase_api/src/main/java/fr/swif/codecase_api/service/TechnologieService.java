package fr.swif.codecase_api.service;

import fr.swif.codecase_api.exception.CodeCaseApiException;
import fr.swif.codecase_api.exception.MessagesErreur;
import fr.swif.codecase_api.model.Technologie;
import fr.swif.codecase_api.repository.TechnologieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TechnologieService
 * <i>de fr.swif.codecase_api.service</i>
 * <hr>
 * <p>Service pour appliquer les traitements métier de Technologie</p>
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
public class TechnologieService {

  private final TechnologieRepository technologieRepository;

  /**
   * Méthode getTechnologie
   *
   *<i>de TechnologieService</i>
   *<h1></h1>
   *<hr>
   *<p>Va chercher tous les Technologies dans la table grâce à findAll()</p>
   * @return Un Iterable composé de Technologies → c'est une interface qui
   * représente "quelque chose qu'on peut parcourir élément par élément"
   * Comme une Arraylist mais peut parcourir n'importe quelle collection.
   * @throws CodeCaseApiException
   */
  // Iterable → Interface qui peut être parcourue
  public Iterable<Technologie> getTechnologies() throws CodeCaseApiException {
    Iterable<Technologie> technologie = technologieRepository.findAll();
    if(!technologie.iterator().hasNext()) {
      // iterator() -> curseur positionné au début de la collection
      // hasNext() -> retourne true s'il y a au moins 1 élément à partir
      // de la position actuelle
      throw new CodeCaseApiException(MessagesErreur.ALL_TECHNOLOGIES_NOT_FOUND);
    }
    return technologie;
  }

  /**
   * Méthode getTechnologie
   *
   *<i>de TechnologieService</i>
   *<h1></h1>
   *<hr>
   *<p>Prend un id et renvoie la Technologie en question
   * si elle existe grâce à findById()</p>
   * @param id l'id du Technologie cherché
   * @return La Technologie cherchée
   * @throws CodeCaseApiException
   */
  public Technologie getTechnologie(int id) throws CodeCaseApiException {
    return technologieRepository.findById(id)
        .orElseThrow(() -> new CodeCaseApiException(
            MessagesErreur.TECHNOLOGIE_NOT_FOUND));
  }

  /**
   * Méthode saveTechnologie
   *
   *<i>de TechnologieService</i>
   *<h1></h1>
   *<hr>
   *<p>Prend un Objet Technologie et le sauvegarde ou
   * le mets à jour dans la BDD grâce à save()
   * Cette méthode ne lève que des unchecked exceptions</p>
   * @param technologie La Technologie à créer ou modifier
   * @return La Technologie créée ou modifié
   */
  // @Transactional surcharge le readOnly de la classe
  @Transactional
  public Technologie saveTechnologie(Technologie technologie) {
    return technologieRepository.save(technologie);
  }

  /**
   * Méthode deleteTechnologie
   *
   *<i>de TechnologieService</i>
   *<h1></h1>
   *<hr>
   *<p>Prend l'id d'une Technologie et le supprime grâce à deleteById()</p>
   * @param id L'id de la Technologie à supprimer
   * @throws CodeCaseApiException
   */
  // @Transactional surcharge le readOnly de la classe
  @Transactional
  public void deleteTechnologie(int id) throws CodeCaseApiException{
    if (!technologieRepository.existsById(id)) {
      throw new CodeCaseApiException(MessagesErreur.TECHNOLOGIE_NOT_FOUND);
    }
    technologieRepository.deleteById(id);
  }

}

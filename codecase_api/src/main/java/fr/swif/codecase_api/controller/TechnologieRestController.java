package fr.swif.codecase_api.controller;

import fr.swif.codecase_api.exception.CodeCaseApiException;
import fr.swif.codecase_api.model.Technologie;
import fr.swif.codecase_api.service.TechnologieService;
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
 * TechnologieRestController
 * <i>de fr.swif.codecase_api.controller</i>
 * <hr>
 * <p>Controller REST pour les endpoints Technologie</p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 02/07/2026
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
public class TechnologieRestController {

  private final TechnologieService technologieService;

  /**
   * Méthode createTechnologie
   *
   *<i>de TechnologieRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Création d'un post
   * Cette méthode ne lève que des unchecked exceptions</p>
   * @param technologie
   * @return
   */
  @PostMapping("/technologie")
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  // @Valid permet de déclencher la Bean Validation sur l'objet qu'il annote
  public ResponseEntity<Technologie> createTechnologie(@Valid @RequestBody Technologie technologie) {
    return ResponseEntity.ok(technologieService.saveTechnologie(technologie));
  }

  /**
   * Méthode getTechnologies
   *
   *<i>de TechnologieRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Liste tous les posts</p>
   * @return
   * @throws CodeCaseApiException
   */
  @GetMapping("/technologies")
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  public ResponseEntity<Iterable<Technologie>> getTechnologies() throws CodeCaseApiException {
    return ResponseEntity.ok(technologieService.getTechnologies());
  }

  /**
   * Méthode getTechnologie
   *
   *<i>de TechnologieRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Liste une Technologie par rapport à son id</p>
   * @param id
   * @return
   * @throws CodeCaseApiException
   */
  @GetMapping("/technologie/{id}")
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  public ResponseEntity<Technologie> getTechnologie(@PathVariable("id") int id)
      throws CodeCaseApiException {
    return ResponseEntity.ok(technologieService.getTechnologie(id));
  }

  /**
   * Méthode deleteTechnologie
   *
   *<i>de TechnologieRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Supprime une Technologie par rapport à son id
   * Cette méthode ne lève que des unchecked exceptions</p>
   * @param id
   * @return
   * @throws CodeCaseApiException
   */
  @DeleteMapping("/technologie/{id}")
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  public ResponseEntity<String> deleteTechnologie(@PathVariable("id") int id)
      throws CodeCaseApiException {
    technologieService.deleteTechnologie(id);
    return ResponseEntity.ok("Technologie supprimée");
  }

}

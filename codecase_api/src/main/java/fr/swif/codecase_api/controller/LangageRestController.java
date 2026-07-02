package fr.swif.codecase_api.controller;

import fr.swif.codecase_api.exception.CodeCaseApiException;
import fr.swif.codecase_api.model.Langage;
import fr.swif.codecase_api.model.Post;
import fr.swif.codecase_api.service.LangageService;
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
 * LangageRestController
 * <i>de fr.swif.codecase_api.controller</i>
 * <hr>
 * <p>Controller REST pour les endpoints Langage</p>
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
public class LangageRestController {

  private final LangageService langageService;

  /**
   * Méthode createLangage
   *
   *<i>de LangageRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Création d'un langage
   * Cette méthode ne lève que des unchecked exceptions</p>
   * @param langage
   * @return
   */
  @PostMapping("/langage")
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  // @Valid permet de déclencher la Bean Validation sur l'objet qu'il annote
  public ResponseEntity<Langage> createLangage(@Valid @RequestBody Langage langage) {
    return ResponseEntity.ok(langageService.saveLangage(langage));
  }

  /**
   * Méthode getLangages
   *
   *<i>de LangageRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Liste tous les langages</p>
   * @return
   * @throws CodeCaseApiException
   */
  @GetMapping("/langages")
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  public ResponseEntity<Iterable<Langage>> getLangages() throws CodeCaseApiException {
    return ResponseEntity.ok(langageService.getLangages());
  }

  /**
   * Méthode getLangage
   *
   *<i>de LangageRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Liste un langage par rapport à son id</p>
   * @param id
   * @return
   * @throws CodeCaseApiException
   */
  @GetMapping("/langage/{id}")
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  public ResponseEntity<Langage> getLangage(@PathVariable("id") int id)
      throws CodeCaseApiException {
    return ResponseEntity.ok(langageService.getLangage(id));
  }

  /**
   * Méthode deleteLangage
   *
   *<i>de LangageRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Supprime un post par rapport à son id
   * Cette méthode ne lève que des unchecked exceptions</p>
   * @param id
   * @return
   * @throws CodeCaseApiException
   */
  @DeleteMapping("/langage/{id}")
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer
  public ResponseEntity<String> deleteLangage(@PathVariable("id") int id)
      throws CodeCaseApiException{
    langageService.deleteLangage(id);
    return ResponseEntity.ok("Langage supprimé");
  }
}

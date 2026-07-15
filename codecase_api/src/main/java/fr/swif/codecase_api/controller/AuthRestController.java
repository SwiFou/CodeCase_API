package fr.swif.codecase_api.controller;

import fr.swif.codecase_api.configuration.JwtUtils;
import fr.swif.codecase_api.exception.CodeCaseApiException;
import fr.swif.codecase_api.exception.MessagesErreur;
import fr.swif.codecase_api.model.User;
import fr.swif.codecase_api.repository.UserRepository;
import fr.swif.codecase_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthRestController
 * <i>de fr.swif.codecase_api.controller</i>
 * <hr>
 * <p>RestController qui prend en compte les méthodes inscription(), connexion()
 * et deconnexion().
 * Quand une connexion est active, le cookie JWT est stocké 1 seule fois par
 * navigateur. La déconnexion va venir supprimer le cookie courant pour le
 * remplacer par un cookie vide.
 * Par contre, si un cookie JWT est volé avant la déconnexion, il est toujours
 * valide jusqu'à sa date d'expiration.</p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 08/07/2026
 */

// @Slf4j permet de générer un champ de log
@Slf4j
// @RestController est une combinaison de :
// - @Controller qui marque la classe comme composant Spring MVC
// gérant les requêtes HTTP
// - @ResponseBody qui indique que la valeur retournée par chaque méthode
// est sérialisée (conversion de l'objet en JSON) directement dans le corps
// de la réponse HTTP (JSON par défaut avec Jackson), au lieu d'être interprétée
// comme un nom de vue Thymeleaf/JSP
@RestController
// @RequestMapping permet de définir le préfixe d'URL commun à toutes
// les méthodes du controller en question
@RequestMapping("/user/authentification")
// @RequiredArgsConstructor génère automatiquement un constructeur prenant en
// paramètre tous les champs final et @NotNull de la classe
@RequiredArgsConstructor
public class AuthRestController {

  private final UserRepository userRepository;

  private final UserService userService;

  private final PasswordEncoder passwordEncoder;

  private final JwtUtils jwtUtils;

  private final AuthenticationManager authenticationManager;

  /**
   * Méthode inscription
   *
   *<i>de AuthRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Permet d'inscrire un utilisateur par rapport à son adresse mail et
   * d'encoder son mot de passe</p>
   * @param user L'utilisateur qui s'inscrit
   * @return La réponse de création du compte
   */
  @PostMapping("/inscription")
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer

  // @Valid permet de déclencher la Bean Validation sur l'objet qu'il annote

  // Ici le type est ?, car la méthode peut renvoyer des corps de réponse de
  // types différents selon les cas
  public ResponseEntity<?> inscription(@Valid @RequestBody User user) {
    if(userRepository.findByUserEmail(user.getUserEmail()).isPresent()) {
      return ResponseEntity.badRequest().body("L'adresse email est déjà utilisée");
    }
    user.setUserMdp(passwordEncoder.encode(user.getUserMdp()));

    return ResponseEntity.ok(userRepository.save(user));
  }

  /**
   * Méthode connexion
   *
   *<i>de AuthRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Cette méthode permet à un utilisateur de se connecter par rapport à son
   * adresse mail et son mot de passe</p>
   * @param user Le User qui souhaite se connecter
   * @return La réponse de connexion au compte
   * @throws AuthenticationException
   * @throws CodeCaseApiException
   */
  @PostMapping("/connexion")
  // ResponseEntity est une classe Spring qui représente toute la réponse HTTP
  // que le controller va renvoyer

  // Pas de @Valid ici sinon ça ferait déclencher les @NotBlank et @NotNull de
  // variables qu'on ne veut pas manipuler pour cette méthode.

  // Ici le type est ?, car la méthode peut renvoyer des corps de réponse de
  // types différents selon les cas
  public ResponseEntity<?> connexion(@RequestBody User user)
      throws AuthenticationException, CodeCaseApiException {

    // Si l'adresse mail ou le mot de passe sont null ou contiennent seulement
    // des espaces, alors cela lève une exception.
    if(user.getUserEmail() == null || user.getUserEmail().isBlank() ||
    user.getUserMdp() == null || user.getUserMdp().isBlank()) {
      throw new CodeCaseApiException(MessagesErreur.IDENTIFIANTS_USER_INVALIDES);
    }

    /* authenticate() est la méthode de Spring Security qui va tenter
    d'authentifier un objet Authentication fourni et renvoie un objet de même
    type entièrement renseigné en cas de succès.
    - Elle va ainsi chercher dans la BDD l'utilisateur dont l'email est renseigné
    - Comparer le mot de passe fourni avec celui stocké en BDD :
      - Si ça correspond, l'exécution continue
      - Si ça ne correspond pas, lève une exception AuthenticationException */
    authenticationManager.authenticate(
        // UsernamePasswordAuthenticationToken est un objet de Spring Security
        // qui représente une tentative d'authentification "non authentifiée"
        new UsernamePasswordAuthenticationToken(
            // Il embarque ici
            user.getUserEmail(), // L'adresse mail du User
            user.getUserMdp() // Le mot de passe du User
        )
    );

    // Génération du cookie contenant le JWT, à poser sur la réponse
    ResponseCookie cookie = jwtUtils.genererCookieJwt(user.getUserEmail());

    // Mise à jour de la dernière connexion
    userService.majDerniereConnexion(user.getUserEmail());

    return ResponseEntity.ok()
        /* header() ajoute le SET_COOKIE à la réponse HTTP. C'est ce header
        que le navigateur interprète pour stocker le cookie côté client */
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body("Connexion réussie");

  }

  /**
   * Méthode deconnexion
   *
   *<i>de AuthRestController</i>
   *<h1></h1>
   *<hr>
   *<p>Cette méthode permet à un utilisateur de se déconnecter en supprimant
   * le cookie contenant le token JWT</p>
   * @return Le réponse de déconnexion
   */
  @PostMapping("/deconnexion")
  public ResponseEntity<?> deconnexion() {

    ResponseCookie cookie = jwtUtils.genererCookieVideJwt();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body("Déconnexion réussie");
  }

}

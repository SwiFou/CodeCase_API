package fr.swif.codecase_api.controller;

import fr.swif.codecase_api.configuration.JwtUtils;
import fr.swif.codecase_api.exception.CodeCaseApiException;
import fr.swif.codecase_api.exception.MessagesErreur;
import fr.swif.codecase_api.model.User;
import fr.swif.codecase_api.repository.UserRepository;
import fr.swif.codecase_api.service.UserService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
 * <p></p>
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

@RequestMapping("/user/auth")
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
  public ResponseEntity<?> inscription(@Valid @RequestBody User user) {
    if(userRepository.findByUserEmail(user.getUserEmail()).isPresent()) {
      return ResponseEntity.badRequest().body("L'adresse email est déjà utilisée");
    }
    user.setUserMdp(passwordEncoder.encode(user.getUserMdp()));

    return ResponseEntity.ok(userRepository.save(user));
  }


  @PostMapping("/connexion")
  public ResponseEntity<?> connexion(@RequestBody User user)
      throws AuthenticationException, CodeCaseApiException {

    if(user.getUserEmail() == null || user.getUserEmail().isBlank() ||
    user.getUserMdp() == null || user.getUserMdp().isBlank()) {
      throw new CodeCaseApiException(MessagesErreur.IDENTIFIANTS_USER_INVALIDES);
    }

    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            user.getUserEmail(), user.getUserMdp()));

    Map<String, Object> authData = new HashMap<>();
    authData.put("token", jwtUtils.genererToken(user.getUserEmail()));
    authData.put("type", "Bearer");

    userService.majDerniereConnexion(user.getUserEmail());

    return ResponseEntity.ok(authData);

  }

}

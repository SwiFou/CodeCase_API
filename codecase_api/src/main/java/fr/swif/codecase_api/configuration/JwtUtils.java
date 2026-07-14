package fr.swif.codecase_api.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * JwtUtils
 * <i>de fr.swif.codecase_api.configuration</i>
 * <hr>
 * <p>Classe qui permet la création et la configuration du token JWT</p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 17/06/2026
 */

// @Component est une annotation Spring qui marque une classe comme bean géré
// par le conteneur IoC (Inversion of Control) de Spring
// Spring scan tous les packages et instancie automatiquement toutes les classes
// annotées @Component
@Component
public class JwtUtils {

  /**
   * Récupération de la valeur de app.secet-key dans application.properties
   */
  @Value("${app.secret-key}")
  private String secretKey;

  /**
   * Récupération de la valeur de app.expiration-time
   * dans application.properties
   */
  @Value("${app.expiration-time}")
  private long expirationTime;

  /**
   * Méthode générerToken
   *
   *<i>de JwtUtils</i>
   *<h1></h1>
   *<hr>
   *<p>Cette méthode permet de générer un token JWT à partir de l'email de
   * l'utilisateur</p>
   * @param userEmail L'email de l'utilisateur, utilisé comme subject du token
   * @return Le token JWT signé sous forme de chaîne compactée
   */
  public String genererToken(String userEmail) {
    // Map<> est une collection d'associations clé-valeur

    // Pour l'instant claims est vide, mais pourra contenir le Role par exemple
    // rajouter des claims.put("clé", "valeur")
    Map<String, Object> claims = new HashMap<>();
    return creerToken(claims, userEmail);
  }

  /**
   * Méthode creerToken
   *
   *<i>de JwtUtils</i>
   *<h1></h1>
   *<hr>
   *<p>Cette méthode permet de construire un token JWT signé avec les claims et
   * le subject fournis</p>
   * @param claims Les claims additionnels à inclure dans le token. Ce sont
   *               des informations (paire clé-valeur) contenue dans le payload
   *               du token JWT
   * @param subject Le subject du token (ici l'email de l'utilisateur)
   * @return Le token JWT signé et compacté
   */
  private String creerToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .claims(claims) // Données additionnelles (vide ici, pour l'instant)
        .subject(subject) // L'identifiant principal (l'émail)
        .issuedAt(new Date(System.currentTimeMillis())) // La date de création
        // La date d'expiration
        .expiration(new Date(System.currentTimeMillis() + expirationTime))
        .signWith(getSignatureKey()) // La signature avec la clé secrète
        .compact(); // Sérialise le tout en une chaîne JWT (header.payload.signature)
  }

  /**
   * Méthode getSignatureKey
   *
   *<i>de JwtUtils</i>
   *<h1></h1>
   *<hr>
   *<p>Cette méthode permet de construire la clé de signature HMAC-SHA à partir
   * de la clé secrète configurée (dans application.properties), encodée en
   * UTF-8.</p>
   * @return La clé secrète utilisée pour signer et vérifier les tokens
   */
  private SecretKey getSignatureKey() {
    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  /**
   * Méthode validerToken
   *
   *<i>de JwtUtils</i>
   *<h1></h1>
   *<hr>
   *<p>Cette méthode permet de valider un token JWT en vérifiant que l'email
   * extrait correspond bien à l'utilisateur fourni et que le token n'est pas
   * expiré.</p>
   * @param token Le token JWT à valider
   * @param userDetails Les détails de l'utilisateur à comparer avec le contenu
   *                    du token
   * @return True si le token est valide et correspond à l'utilisateur, sinon
   * false
   */
  public boolean validerToken(String token, UserDetails userDetails) {
    try{
      String userEmailExtrait = extractEmail(token);
      return (userEmailExtrait.equals(userDetails.getUsername())
          && !expirationToken(token));
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  /**
   * Méthode extractionExpirationDate
   *
   *<i>de JwtUtils</i>
   *<h1></h1>
   *<hr>
   *<p>Cette méthode permet d'extraire la date d'expiration contenue dans le
   * token</p>
   * @param token Le token JWT
   * @return La date d'expiration du token extraite
   */
  private Date extractionExpirationDate(String token) {
    return extractionClaim(token, Claims::getExpiration);
  }

  /**
   * Méthode expirationToken
   *
   *<i>de JwtUtils</i>
   *<h1></h1>
   *<hr>
   *<p>Cette méthode permet de vérifier si le token envoyé est expiré en
   * comparant sa date d'expiration avec la date actuelle</p>
   * @param token Le token JWT
   * @return True si le token est expiré, sinon false
   */
  private boolean expirationToken(String token) {
    return extractionExpirationDate(token).before(new Date());
  }

  /**
   * Méthode extractEmail
   *
   *<i>de JwtUtils</i>
   *<h1></h1>
   *<hr>
   *<p>Cette méthode permet d'extraire l'email (donc le subject) contenu dans
   * le token</p>
   * @param token Le token JWT
   * @return L'email de l'utilisateur associé au token
   */
  public String extractEmail(String token) {
    return extractionClaim(token, Claims::getSubject);
  }

  /**
   * Méthode extractionClaim
   *
   *<i>de JwtUtils</i>
   *<h1></h1>
   *<hr>
   *<p>Cette méthode permet d'extraire un claim spécifique du token, via une
   * fonction de résolution appliquée aux Claims</p>
   * @param token Le token JWT
   * @param claimsResolver La fonction extrayant la valeur souhaitée depuis les
   *                       Claims
   * @param <T> Le type de la valeur extraite
   * @return La valeur du claim extrait
   */
  private <T> T extractionClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Méthode extractAllClaims
   *
   *<i>de JwtUtils</i>
   *<h1></h1>
   *<hr>
   *<p>Cette méthode permet de parser le token JWT et de vérifier sa signature
   * à l'aide de la clé secrète.
   * Retourne l'ensemble des claims (payload) contenus dans le token.
   * Lève une JwtException si le token est invalide, expiré côté format, ou mal
   * signé. Elle est catchée dans la méthode validerToken</p>
   * @param token Le token JWT à parser
   * @return Les claims extraits du token
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parser() // Créer un parseur
        .verifyWith(getSignatureKey()) // Configure la vérification avec la clé
                                      // secrète
        .build()
        .parseSignedClaims(token) // Parse le token en vérifiant la signature
        .getPayload(); // Récupère les claims (payload) du token
  }

  /**
   * Méthode genererCookieJwt
   *
   *<i>de JwtUtils</i>
   *<h1></h1>
   *<hr>
   *<p>Cette méthode permet de générer un cookie httpOnly contenant le token JWT
   * de l'utilisateur, destiné à être posé sur la réponse HTTP lors de la
   * connexion.</p>
   * @param userEmail L'adresse mail du User, utilisé pour générer le token
   * @return Le cookie contenant le token JWT, prêt à être ajouté à la réponse
   */
  public ResponseCookie genererCookieJwt(String userEmail) {

    String token = genererToken(userEmail);

    return ResponseCookie.from("jwt", token)
        // Le cookie est envoyé pour toutes les routes du domaine
        .path("/")
        // httpOnly empêche JavaScript de lire le cookie (protection XSS)
        .httpOnly(true)
        /* maxAge attend une durée en secondes, alors qu'expirationTime est en
        millisecondes */
        .maxAge(expirationTime / 1000)
        .build();
  }

  /**
   * Méthode genererCookieVideJwt
   *
   *<i>de JwtUtils</i>
   *<h1></h1>
   *<hr>
   *<p>Cette méthode permet de générer un cookie "vide" avec une durée de vie
   * nulle, ce qui indique au navigateur de supprimer immédiatement le cookie
   * JWT existant. Utilisée lors de la déconnexion</p>
   * @return Le cookie de nettoyage, prêt à être ajouté à la réponse
   */
  public ResponseCookie genererCookieVideJwt() {
    return ResponseCookie.from("jwt", "")
        /* Le path et le nom sont identiques au cookie posé à la connexion,
        sinon le navigateur considère qu'il s'agit d'un cookie différent. */
        .path("/")
        // httpOnly empêche JavaScript de lire le cookie (protection XSS)
        .httpOnly(true)
        // maxAge à 0 = suppression immédiate du cookie par le navigateur
        .maxAge(0)
        .build();
  }

}

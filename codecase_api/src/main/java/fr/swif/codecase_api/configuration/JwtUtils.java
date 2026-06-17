package fr.swif.codecase_api.configuration;

import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * JwtUtils
 * <i>de fr.swif.codecase_api.configuration</i>
 * <hr>
 * <p></p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 17/06/2026
 */

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
  private int expirationTime;

  public String generationToken(String userEmail) {
    Map<String, Object> claims = new HashMap<>();
    return creationToken(claims, userEmail);
  }

  private String creationToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .claims(claims)
        .subject(subject)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expirationTime))
  }

}

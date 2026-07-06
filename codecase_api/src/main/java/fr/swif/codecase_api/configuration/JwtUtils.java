package fr.swif.codecase_api.configuration;

import fr.swif.codecase_api.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
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

  public String genererToken(String userEmail) {
    Map<String, Object> claims = new HashMap<>();
    return creerToken(claims, userEmail);
  }

  private String creerToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .claims(claims)
        .subject(subject)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expirationTime))
        .signWith(getSignatureKey())
        .compact();
  }

  private SecretKey getSignatureKey() {
    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public boolean validerToken(String token, UserDetailsImpl userDetailsImpl) {
    try{
      String userEmailExtrait = extractEmail(token);
      return (userEmailExtrait.equals(userDetailsImpl.getUsername()) && !expirationToken(token));
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  private Date extractionExpirationDate(String token) {
    return extractionClaim(token, Claims::getExpiration);
  }

  private boolean expirationToken(String token) {
    return extractionExpirationDate(token).before(new Date());
  }

  public String extractEmail(String token) {
    return extractionClaim(token, Claims::getSubject);
  }

  private <T> T extractionClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(getSignatureKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }


}

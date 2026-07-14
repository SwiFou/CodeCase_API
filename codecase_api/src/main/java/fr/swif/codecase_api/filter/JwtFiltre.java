package fr.swif.codecase_api.filter;

import fr.swif.codecase_api.configuration.JwtUtils;
import fr.swif.codecase_api.security.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JwtFiltre
 * <i>de fr.swif.codecase_api.filter</i>
 * <hr>
 * <p>Cette classe sert à tenter d'authentifier l'utilisateur si le token
 * valide est présent. Si ce n'est pas le cas, elle doit laisser la requête
 * continuer sans authentification. Donc pas de throw/throws au niveau du
 * try/catch</p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 18/06/2026
 */

// @Slf4j permet de générer un champ de log
@Slf4j
// @Component est une annotation Spring qui marque une classe comme bean géré
// par le conteneur IoC (Inversion of Control) de Spring
// Spring scan tous les packages et instancie automatiquement toutes les classes
// annotées @Component
@Component
// @RequiredArgsConstructor génère automatiquement un constructeur prenant en
// paramètre tous les champs final et @NotNull de la classe
@RequiredArgsConstructor
public class JwtFiltre extends OncePerRequestFilter {

  private final CustomUserDetailsService customUserDetailsService;

  private final JwtUtils jwtUtils;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    // Récupération de la valeur du Header
    final String authHeader = request.getHeader("Authorization");

    String eMail = null;
    String jwt = extractionJwtDeCookies(request);

    if(authHeader != null && authHeader.startsWith("Bearer ")) {
      // Permet d'extraire les 7 premiers caractères du Header
      // (donc de "Bearer ")
      jwt = authHeader.substring(7);
      try {
        eMail = jwtUtils.extractEmail(jwt);
      } catch (ExpiredJwtException eje) {
        log.debug("Tentative d'authentification avec un token JWT expiré : {}",
            eje.getMessage());
      } catch (JwtException | IllegalArgumentException e) {
        log.warn("Tentative d'authentification avec un token JWT invalide : {}",
            e.getMessage());
      }

    }

    // Sert à vérifier si l'email de l'utilisateur n'est pas null et
    // si l'utilisateur n'est pas encore authentifié
    // Permet aussi de s'assurer que le filtre ne va rien écraser par rapport à
    // d'autres mécanismes d'authentification (ex : authentification par session)
    if(eMail != null && SecurityContextHolder.getContext().getAuthentication()
        == null) {
      // Récupération des informations de l'utilisateur
      UserDetails userDetails = customUserDetailsService.loadUserByUsername(eMail);

      // Si le token est valide
      if(jwtUtils.validerToken(jwt, userDetails)) {

        // Construction de l'objet d'authentification Spring Security à partir
        // de l'utilisateur et de ses rôles
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(
                // L'utilisateur authentifié (son identité)
                userDetails,
                // Les credentials (normalement le mot de passe).
                // Ici null car le mot de passe n'est plus nécessaire :
                // l'utilisateur a déjà prouvé son identité via son token
                // JWT valide, donc pas besoin de re-vérifier un mot de passe.
                null,
                // Les rôles/permissions de l'utilisateur, utilisés plus tard
                // pour les vérifications d'autorisation
                userDetails.getAuthorities());

        // Récupérer les informations de la requête
        // Avec quelle requête l'utilisateur a été authentifié
        // Et permet d'apporter plus de détails pour les logs (identifiants,
        // adresse IP, etc.)
        authenticationToken.setDetails(new WebAuthenticationDetailsSource()
            .buildDetails(request));

        // Enregistre l'utilisateur comme étant authentifié dans le contexte de
        // sécurité de la requête courante (utilisé ensuite pour les
        // vérifications d'autorisation)
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    }

    // Poursuit l'exécution de la chaîne de filtres (obligatoire, sinon la
    // requête reste bloquée ici) : ce filtre ne fait qu'authentifier,
    // il ne bloque jamais
    filterChain.doFilter(request, response);

  }

  /**
   * Méthode extractionJwtDeCookies
   *
   *<i>de JwtFiltre</i>
   *<h1></h1>
   *<hr>
   *<p>Cette méthode permet d'extraire la valeur du token JWT depuis les cookies
   * de la requête entrante</p>
   * @param request La requête HTTP entrante
   * @return Le token JWT s'il est présent dans les cookies, sinon null
   */
  private String extractionJwtDeCookies(HttpServletRequest request) {

    Cookie[] cookies = request.getCookies();

    if(cookies == null) {
      return null;
    }

    for(Cookie cookie : cookies) {
      if("jwt".equals(cookie.getName())) {
        return cookie.getValue();
      }
    }

    return null;
  }
}

package fr.swif.codecase_api.filter;

import fr.swif.codecase_api.configuration.JwtUtils;
import fr.swif.codecase_api.security.CustomUserDetailsService;
import fr.swif.codecase_api.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JwtFiltre
 * <i>de fr.swif.codecase_api.filter</i>
 * <hr>
 * <p></p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 18/06/2026
 */

@Component
@RequiredArgsConstructor
public class JwtFiltre extends OncePerRequestFilter {

  private final CustomUserDetailsService customUserDetailsService;

  private final JwtUtils jwtUtils;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    // Récupération de la valeur du Header
    final String authHeader = request.getHeader("Authorisation");

    String eMail = null;
    String jwt = null;

    if(authHeader != null && authHeader.startsWith("Bearer ")) {
      // Permet d'extraire les 7 premiers caractères du Header
      // (donc de "Bearer ")
      jwt = authHeader.substring(7);
      eMail = jwtUtils.extractEmail(jwt);
    }

    // Sert à vérifier si l'utilisateur n'est encore authentifié
    if(eMail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetailsImpl userDetails = 
    }

    // Laisse passer la requête
    filterChain.doFilter(request, response);

  }
}

package fr.swif.codecase_api.security;

import fr.swif.codecase_api.model.User;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * UserDetailsImpl
 * <i>de fr.swif.codecase_api.security</i>
 * <hr>
 * <p></p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 17/06/2026
 */

// @Getter permet de générer les getters des différentes méthodes
@Getter
// @RequiredArgsConstructor génère automatiquement un constructeur prenant en
// paramètre tous les champs final et @NotNull de la classe
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

  private final User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(user.getUserRole().name()));
  }

  @Override
  public @Nullable String getPassword() {
    return user.getUserMdp();
  }

  @Override
  public String getUsername() {
    return user.getUserEmail();
  }

  @Override
  public boolean isEnabled() {
    return !user.getUserPseudo().startsWith("Utilisateur supprimé-");
  }
}

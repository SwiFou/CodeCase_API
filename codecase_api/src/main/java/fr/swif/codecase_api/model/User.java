package fr.swif.codecase_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * User
 * <i>de fr.swif.codecase_api.model</i>
 * <hr>
 * <p></p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 09/06/2026
 */

// @Data est l'équivalent de @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@Data
// @Entity indique à Hibernate (ou tout autre provider JPA) que la classe Java est mappée à une table en base de données
@Entity
@Table(name = "User_")
public class User {
  /**
   * Variable userId
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "userId")
  private Integer userId;

  /**
   * Variable userPseudo
   */
  @Size(min = 3, max = 20)
  @Column(name = "userPseudo")
  private String userPseudo;

  /**
   * Variable userMdp
   */
  @Column(name = "userMdp")
  private String userMdp;

  /**
   * Variable userEmail
   */
  @Email
  @Column(name = "userEmail")
  private String userEmail;

  /**
   * Variable userRole
   */
  @Size(max = 8)
  @NotEmpty
  @Enumerated(EnumType.STRING) // Permet de stocker
                              // "VISITEUR"/"USER"/"MODO"/"ADMIN" au lieu de 0/1
  @Column(name = "userRole")
  private Role userRole;

  /**
   * Variable userDateCreationCompte
   */
  @NotEmpty
  @Column(name = "userDateCreationCompte")
  private LocalDate userDateCreationCompte;

  /**
   * Variable userDerniereConnexion
   */
  @NotEmpty
  @Column(name = "userDerniereConnexion")
  private LocalDate userDerniereConnexion;

  /**
   * Variable userAvatar
   */
  @NotBlank
  @Size(max = 250)
  private String userAvatar;

  /**
   * Variable userMfaActif
   */
  @Column(name = "userMfaActif")
  private boolean userMfaActif;
}

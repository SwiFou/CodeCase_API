package fr.swif.codecase_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * Sanction
 * <i>de fr.swif.codecase_api.model</i>
 * <hr>
 * <p></p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 10/06/2026
 */

// @Data est l'équivalent de @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@Data
// @Entity indique à Hibernate (ou tout autre provider JPA) que la classe Java est mappée à une table en base de données
@Entity
@Table(name = "Sanction")
public class Sanction {
  /**
   * Variable sanctionId
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "sanctionId")
  private Integer sanctionId;

  /**
   * Variable sanctionType
   */
  @Column(name = "sanctionType")
  @Enumerated(EnumType.STRING) // Permet de stocker "AVERTISSEMENT"/
  // "BANNISSEMENT_TEMPORAIRE"/"BANNISSEMENT_DEFINITIF" au lieu de 0/1
  private SanctionType sanctionType;

  /**
   * Variable sanctionStatut
   */
  @Column(name = "sanctionStatut")
  @Enumerated(EnumType.STRING) // Permet de stocker "EN_COURS"/"LEVEE"/"EXPIREE"
  // au lieu de 0/1
  private SanctionStatut sanctionStatut;

  /**
   * Variable sanctionDate
   */
  @NotEmpty
  private LocalDate sanctionDate;

  /**
   * Variable sanctionDateFin
   */
  @NotEmpty
  private LocalDateTime sanctionDateFin;

  /**
   * Variable sanctionMotif
   */
  @NotBlank
  @Size(max = 100)
  private String sanctionMotif;

  /**
   * FK vers le membre sanctionné (0,n côté Sanction → 1,1)
   */
  @ManyToOne
  @JoinColumn(name = "userId_MEMBRE", nullable = false)
  private User userId_MEMBRE;

  /**
   * FK vers le modo/admin qui a formulé la sanction
   */
  @ManyToOne
  @JoinColumn(name = "userId_MODO_ADMIN", nullable = false)
  private User userId_MODO_ADMIN;
}

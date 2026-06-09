package fr.swif.codecase_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Data;

/**
 * Signalement
 * <i>de fr.swif.codecase_api.model</i>
 * <hr>
 * <p></p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 09/06/2026
 */

@Data
@Entity
@Table(name = "Signalement")
public class Signalement {
  /**
   * Variable signalementId
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "signalementId")
  private Integer signalementId;

  /**
   * Variable signalementDate
   */
  @NotBlank
  @Column(name = "signalementDate")
  private LocalDate signalementDate;

  /**
   * Variable signalementDescription
   */
  @NotBlank
  @Size(max = 100)
  @Column(name = "signalementDescription")
  private String signalementDescription;

  /**
   * Variable technologieId de la classe Technologie
   */
  @ManyToOne
  @JoinColumn(name = "technologieId")
  private Technologie technologieId;

  /**
   * Variable postId de la classe Post
   */
  @ManyToOne
  @JoinColumn(name = "postId")
  private Post postId;

  /**
   * Variable commentaireId de la classe Commentaire
   */
  @ManyToOne
  @JoinColumn(name = "commentaireId")
  private Commentaire commentaireId;

  /**
   * Variable userId de la classe User
   */
  @ManyToOne
  @JoinColumn(name = "userId")
  private User userId;
}

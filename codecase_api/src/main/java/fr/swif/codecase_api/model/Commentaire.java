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
 * Commentaire
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
@Table(name = "Commentaire")
public class Commentaire {
  /**
   * Variable commentaireId
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "commentaireId")
  private Integer commentaireId;

  /**
   * Variable commentaireDate
   */
  @Column(name = "commentaireDate")
  private LocalDate commentaireDate;

  /**
   * Variable commentaireContenu
   */
  @NotBlank
  @Size(max = 600)
  @Column(name = "commentaireContenu")
  private String commentaireContenu;

  /**
   * Variable postId de la classe Post
   */
  @ManyToOne
  @JoinColumn(name = "postId")
  private Post postId;

  /**
   * Variable userId dans la classe User
   */
  @ManyToOne
  @JoinColumn(name = "userId")
  private User userId;
}

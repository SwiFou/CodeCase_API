package fr.swif.codecase_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Langage
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
@Table(name = "Langage")
public class Langage {
  /**
   * Variable langageId
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name  = "langageId")
  private Integer langageId;

  /**
   * Variable langageIntitule
   */
  @Size(min = 3, max = 30)
  @NotBlank
  @Column(name ="langageIntitule")
  private String langageIntitule;


}

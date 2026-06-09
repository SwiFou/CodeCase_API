package fr.swif.codecase_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Vote
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
@Table(name = "Vote")
public class Vote {
  /**
   * Variable voteId
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "voteId")
  private Integer voteId;

  /**
   * Variable voteType de type VoteType
   */
  @Column(name = "voteType")
  VoteType voteType;
}

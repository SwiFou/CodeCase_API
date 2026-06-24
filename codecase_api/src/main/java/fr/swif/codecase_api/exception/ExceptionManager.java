package fr.swif.codecase_api.exception;

import java.sql.SQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * ExceptionManager
 * <i>de fr.swif.codecase_api.exception</i>
 * <hr>
 * <p>Gestionnaire des Exceptions, dédié à rassembler la gestion des exceptions
 * majeures et centralisables</p>
 *
 * @author Calderoli Alexandre
 * @version 0.0.1
 * @since 12/06/2026
 */
public class ExceptionManager {

  /**
   * Constante de sévérité haute
   */
  public static int severityHigh = 6;

  /**
   * Constante de sévérité basse
   */
  public static int severityLow = 1;

  /**
   * Méthode handleException
   *
   *<i>de ExceptionManager</i>
   *<h1></h1>
   *<hr>
   *<p>Méthode "carrefour" du gestionnaire des erreurs, renvoie vers la bonne
   * gestion d'erreur</p>
   * @param ex L'exception à gérer
   * @return Objet de type RetourException
   */
  public static ResponseEntity handleException(Exception ex) {

    if (ex instanceof CodeCaseApiException) {
      CodeCaseApiException cx = (CodeCaseApiException) ex;
      return new ResponseEntity<>(cx.getMessage(), cx.getStatus());
    }
    else if (ex instanceof SQLException) {
      SQLException sx = (SQLException) ex;
      return handleSQL(sx);
    }
    else {
      return handleUnknown(ex);
    }
  }

  /**
   * Méthode handleStatus
   *
   *<i>de ExceptionManager</i>
   *<h1></h1>
   *<hr>
   *<p>Gère les Exceptions inconnues, "Si ça arrive, il faudra la trouver et
   * l'ajouter autre part", évite des crashs inutiles</p>
   * @param ex L'exception à gérer
   * @return Objet de type RetourException
   */
  private static ResponseEntity handleSQL(SQLException ex) {

    ResponseEntity retour;

    switch (ex.getErrorCode()) {
      case 1062:
        retour = new ResponseEntity<>("Un élément de même nom existe déjà",
            HttpStatus.BAD_REQUEST);
        break;
      case 1451:
        retour = new ResponseEntity<>(
            "L'élément est encore lié à des enfants",
            HttpStatus.INTERNAL_SERVER_ERROR);
        break;
      case 1406:
        retour = new ResponseEntity<>("Un champ est trop long",
            HttpStatus.BAD_REQUEST);
        break;
      default:
        retour = new ResponseEntity<>("Exception inconnue",
            HttpStatus.INTERNAL_SERVER_ERROR);
        break;
    }
    return retour;
  }

  /**
   * Méthode handleUnknown
   *
   *<i>de ExceptionManager</i>
   *<h1></h1>
   *<hr>
   *<p>Gère les Exceptions inconnues, "Si ça arrive, il faudra la trouver et
   * l'ajouter autre part", évite des crashs inutiles</p>
   * @param ex
   * @return
   */
  private static ResponseEntity handleUnknown(Exception ex) {

    return new ResponseEntity<>(ex.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

package fr.swif.codecase_api.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

// @RestControllerAdvice est la combinaison de :
// - @ControllerAdvice, qui intercepte les exceptions levées par les controllers
// et permet de retourner n'importe quel type de réponse
// - @ResponseBody, qui permet que la valeur retour soit sérialisée en JSON
// automatiquement
@RestControllerAdvice
public class ExceptionManager {

  // @ExceptionHandler permet de définir la logique pour traiter et répondre aux
  // exceptions traitées en paramètre
  @ExceptionHandler(CodeCaseApiException.class)
  public ResponseEntity<MessageClientApiErreur> handleCodeCaseException(CodeCaseApiException codeCaseApiException) {
    final MessagesErreur messagesErreur = codeCaseApiException.getMessagesErreur();
    final HttpStatus status = messagesErreur.getHttpStatus();
    final MessageClientApiErreur body = creationMessageClientApi(messagesErreur);
    return ResponseEntity.status(status).body(body);
  }

  @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class,})
  public ResponseEntity<MessageClientApiErreur> handleExceptions(MethodArgumentTypeMismatchException exception) {
    final MessagesErreur messagesErreur = MessagesErreur.BAD_REQUEST;
    final HttpStatus status = messagesErreur.getHttpStatus();
    final String paramName = exception.getName();

    String requiredTypeName = "unknown";
    if (exception.getRequiredType() != null) {
      requiredTypeName = exception.getRequiredType().getSimpleName();
    }

    String providedTypeName = "unknown";
    if (exception.getValue() != null) {
      providedTypeName = exception.getValue().getClass().getSimpleName();
    }

    final String message = String.format("Valeur invalide pour le paramètre : "
        + "'%s'. Type attendu : '%s', type reçu : '%s'.", paramName,
        requiredTypeName, providedTypeName);

    final MessageClientApiErreur body = creationMessageClientApi(messagesErreur, message);

    return ResponseEntity.status(status).body(body);
  }

  

}

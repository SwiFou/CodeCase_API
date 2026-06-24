package fr.swif.codecase_api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CodeCaseApiException extends Exception {

  private final MessagesErreur messagesErreur;

  public CodeCaseApiException(MessagesErreur messagesErreur) {
    super(messagesErreur.getDescription());
    this.messagesErreur = messagesErreur;
  }  

}

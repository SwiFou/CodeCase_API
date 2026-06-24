package fr.swif.codecase_api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CodeCaseApiException extends Exception {

  private final HttpStatus status;

  public CodeCaseApiException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

}

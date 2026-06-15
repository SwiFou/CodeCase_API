package fr.swif.codecase_api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CodeCaseException extends Exception {

  private final HttpStatus status;

  public CodeCaseException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

}

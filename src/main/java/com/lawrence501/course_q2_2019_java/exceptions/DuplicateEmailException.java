package com.lawrence501.course_q2_2019_java.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateEmailException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public DuplicateEmailException(String message) {
    super(message);
  }
}
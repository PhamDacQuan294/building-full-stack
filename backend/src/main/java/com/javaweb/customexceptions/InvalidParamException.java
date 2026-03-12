package com.javaweb.customexceptions;

public class InvalidParamException extends RuntimeException {
  public InvalidParamException(String message) {
    super(message);
  }
}

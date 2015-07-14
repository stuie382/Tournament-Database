package com.stuart.tourny.model.utils.exceptions;

/**
 * Custom exception class to throw when a server side class has had a problem. The client side will
 * need to look for this exception.
 */
public class ServerProblem extends Exception {

  public ServerProblem() {
  }

  public ServerProblem(String message) {
    super(message);
  }

  public ServerProblem(Throwable cause) {
    super(cause);
  }

  public ServerProblem(String message, Throwable cause) {
    super(message, cause);
  }
}

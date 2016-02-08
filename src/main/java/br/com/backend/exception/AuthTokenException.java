package br.com.backend.exception;

public class AuthTokenException extends Exception {
	
	private static final long serialVersionUID = 8319843842364363172L;

	public AuthTokenException() {
		super("Invalid Auth Token");
	}
}

package controller;

public class TimeException extends Exception{
	
	public TimeException(String message) {
		super(message);
	}
	
	public TimeException() {
		super();
	}
	
	public TimeException(Throwable t) {
		super(t);
	}
}

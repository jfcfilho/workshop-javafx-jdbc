package db;

public class DBException extends RuntimeException{

	private static final long serialVersionUID = -777264391426448565L;

	public DBException(String message) {
		super(message);
	}
}

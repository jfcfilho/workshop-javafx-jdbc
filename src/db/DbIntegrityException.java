package db;

public class DbIntegrityException extends RuntimeException {

	private static final long serialVersionUID = -1101136413558000168L;

	public DbIntegrityException(String msg) {
		super(msg);
	}
}
package org.lightfish.business.servermonitoring.control.collectors;

public class ValueNotFoundException extends RuntimeException {

    public ValueNotFoundException(String name) {
        super(name);
    }

	public ValueNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}

package org.lightfish.business.servermonitoring.control.collectors;

public class ValueNotFoundException extends RuntimeException {

    ValueNotFoundException(String name) {
        super(name);
    }

}

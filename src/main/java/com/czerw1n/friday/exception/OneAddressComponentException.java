package com.czerw1n.friday.exception;

public class OneAddressComponentException extends AddressParsingException {
    public OneAddressComponentException() {
        super("Only one component found in address line");
    }
}

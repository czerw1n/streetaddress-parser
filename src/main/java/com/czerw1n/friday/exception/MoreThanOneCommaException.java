package com.czerw1n.friday.exception;

public class MoreThanOneCommaException extends AddressParsingException {
    public MoreThanOneCommaException() {
        super("There is more than one comma in address line");
    }
}

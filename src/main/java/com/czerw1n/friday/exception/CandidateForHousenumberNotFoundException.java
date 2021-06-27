package com.czerw1n.friday.exception;

public class CandidateForHousenumberNotFoundException extends AddressParsingException {
    public CandidateForHousenumberNotFoundException() {
        super("Could not find candidate for house number");
    }
}

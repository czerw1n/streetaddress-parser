package com.czerw1n.friday;

import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NonNull;
import lombok.Value;

@Value
public class StreetAddress {
    @NonNull private String street;
    @NonNull private String housenumber;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public StreetAddress(@JsonProperty("street") String street,
                            @JsonProperty("housenumber") String housenumber) {
        this.street = street;
        this.housenumber = housenumber;
    }

    public static StreetAddress createStreetAddress(String[] addressComponents, int housenumberIndex) {
        StringJoiner street = new StringJoiner(" ");
        StringJoiner housenumber = new StringJoiner(" ");
        for(int i = 0; i < addressComponents.length; i++) {
            if(i < housenumberIndex) {
                street.add(addressComponents[i]);
            } else {
                housenumber.add(addressComponents[i]);
            }
        }
        return new StreetAddress(street.toString(), housenumber.toString());
    }

    public static StreetAddress createStreetAddressWhereHousenumberIsFirstComponent(String[] addressComponents) {
        StringJoiner street = new StringJoiner(" ");
        for(int i = 1; i < addressComponents.length; i++) {
            street.add(addressComponents[i]);
        }
        return new StreetAddress(street.toString(), addressComponents[0]);
    }
}

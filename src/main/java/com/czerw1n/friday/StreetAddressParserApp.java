package com.czerw1n.friday;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StreetAddressParserApp {
    public static void main( String[] args ) {
        if(args.length != 1) {
            System.out.println("Wrong arguments " + args.length);
        } else {
            StreetAddressParser streetAddres = new StreetAddressParser();
            ObjectMapper mapper = new ObjectMapper();
            try {
                System.out.println(args[0] + " -> " + mapper.writeValueAsString(streetAddres.parse(args[0])));
            } catch(Exception ex) {
                System.out.println("Cannot parse address input: " + args[0] + " because: " + ex.getMessage());
            }
        }
    }
}

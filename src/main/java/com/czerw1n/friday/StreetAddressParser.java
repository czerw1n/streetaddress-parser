package com.czerw1n.friday;

import java.util.Arrays;
import java.util.stream.IntStream;

import com.czerw1n.friday.exception.CandidateForHousenumberNotFoundException;
import com.czerw1n.friday.exception.MoreThanOneCommaException;
import com.czerw1n.friday.exception.OneAddressComponentException;

import org.apache.commons.lang3.StringUtils;

public class StreetAddressParser {
    
    private static final String[] numberSymbols = new String[] {"nr", "no", "n°"};
    private static final String divideByCommaRegexp = "\\s*(,)\\s*";
    private static final String divideBySpaceRegexp = "\\s+";
    
    public StreetAddress parse(String addressLine) {
        if(addressLine.contains(",")) {
            return handleWithComma(addressLine);
        } else {
            return handleWithoutComma(addressLine);
        }
    }
    
    private StreetAddress handleWithComma(String addressLine) {
        String[] addressComponents = addressLine.split(divideByCommaRegexp);
        if(addressComponents.length != 2) {
            throw new MoreThanOneCommaException();
        }
        return handleTwoComponents(addressComponents);
    }

    private StreetAddress handleWithoutComma(String addressLine) {
        String[] addressComponents = addressLine.split(divideBySpaceRegexp);
        if(addressComponents.length < 2) {
            throw new OneAddressComponentException();
        } else if (addressComponents.length == 2) {
            return handleTwoComponents(addressComponents);
        } else {
            return handleMoreThanTwoComponents(addressComponents);
        }
    }

    private StreetAddress handleTwoComponents(String[] addressComponents) {
        int housenumberIndex = getIndexOfComponentStartingWithNumberSymbolOrDigit(addressComponents);
        return new StreetAddress(addressComponents[Math.abs(housenumberIndex-1)], addressComponents[housenumberIndex]);
    }

    private Integer getIndexOfComponentStartingWithNumberSymbolOrDigit(String[] addressComponents) {
        int[] indexes = IntStream.range(0, addressComponents.length)
                                .filter(i -> startsWithNumberSymbol(addressComponents[i]) || startsWithDigit(addressComponents[i]))
                                .toArray();
        if(indexes.length != 1) {
            throw new CandidateForHousenumberNotFoundException();
        }
        return indexes[0];                
    }

    private boolean startsWithNumberSymbol(String addressComponent) {
        return StringUtils.startsWithAny(addressComponent.toLowerCase(), numberSymbols);
    }

    private StreetAddress handleMoreThanTwoComponents(String[] addressComponents) {
        if(existsNumberSymbol(addressComponents)) {
            int indexOfNumberSymbol = getIndexOfNumberSymbolComponent(addressComponents);
            return StreetAddress.createStreetAddress(addressComponents, indexOfNumberSymbol);
        } else {
            return handleWithoutNumberSymbol(addressComponents);
        }
    }

    private boolean existsNumberSymbol(String[] addressComponents) {
        return Arrays.stream(addressComponents)
                        .anyMatch(component -> Arrays.asList(numberSymbols)
                                                        .contains(component.toLowerCase()));
    }

    private Integer getIndexOfNumberSymbolComponent(String[] addressComponents) {
        int[] indexes = IntStream.range(0, addressComponents.length)
                                .filter(i -> Arrays.asList(numberSymbols).contains(addressComponents[i].toLowerCase()))
                                .toArray();

        if(indexes.length != 1) {
            throw new CandidateForHousenumberNotFoundException();
        }
        return indexes[0];  
    }


    private StreetAddress handleWithoutNumberSymbol(String[] addressComponents) {
        if(firstComponentStartsWithDigit(addressComponents)) {
            return StreetAddress.createStreetAddressWhereHousenumberIsFirstComponent(addressComponents);
        } else if (containsComponentThatStartsWithDigit(addressComponents)) {
            int housenumberIndex = getIndexOfLastComponentStartingWithDigit(addressComponents);
            return StreetAddress.createStreetAddress(addressComponents, housenumberIndex);
        } else {
            throw new CandidateForHousenumberNotFoundException();
        }
    }
    
    private boolean firstComponentStartsWithDigit(String[] addressComponents) {
        return startsWithDigit(addressComponents[0]);
    }

    private int getIndexOfLastComponentStartingWithDigit(String[] addressComponents) {
        int[] indexes = IntStream.range(0, addressComponents.length)
                                .filter(i -> startsWithDigit(addressComponents[i]))
                                .toArray();
        return indexes[indexes.length - 1];  
    }

    private boolean containsComponentThatStartsWithDigit(String[] addressComponents) {
        return Arrays.stream(addressComponents)
                        .anyMatch(component -> startsWithDigit(component));
    }

    private boolean startsWithDigit(String addressComponent) {
        return Character.isDigit(addressComponent.charAt(0));
    }
}

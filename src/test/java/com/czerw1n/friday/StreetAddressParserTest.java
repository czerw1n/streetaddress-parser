package com.czerw1n.friday;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import com.czerw1n.friday.exception.CandidateForHousenumberNotFoundException;
import com.czerw1n.friday.exception.MoreThanOneCommaException;
import com.czerw1n.friday.exception.OneAddressComponentException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

public class StreetAddressParserTest {
    
    ObjectMapper mapper = new ObjectMapper();
    StreetAddressParser streetAddressParser = new StreetAddressParser();

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    public void testParser_validAddresses(String input, String expected) throws JsonProcessingException {
        StreetAddress expectedStreetAddress = mapper.readValue(expected, StreetAddress.class);
        StreetAddress result = streetAddressParser.parse(input);
        System.out.println(input + " -> " + mapper.writeValueAsString(result));
        assertEquals(expectedStreetAddress, result);
    }

    @ParameterizedTest
    @MethodSource("provideArgsForExceptionTests")
    public <T extends Throwable> void testParser_throwsCorrectException(String input, Class<T> clazz) {
        assertThrows(clazz, () -> streetAddressParser.parse(input));
    }

    private static Stream<Arguments> provideArgsForExceptionTests() {
        return Stream.of(
            Arguments.of("Calle, Aduana, 29", MoreThanOneCommaException.class),
            Arguments.of("29", OneAddressComponentException.class),
            Arguments.of("Calle", OneAddressComponentException.class),
            Arguments.of("29 29", CandidateForHousenumberNotFoundException.class),
            Arguments.of("Calle calle", CandidateForHousenumberNotFoundException.class),
            Arguments.of("Calle Aduana No No 29", CandidateForHousenumberNotFoundException.class)
        );
    }
}

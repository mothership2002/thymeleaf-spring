package hello.typeconverter.converter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ConverterTest {


    @Test
    void stringToInteger() {
        StringToIntegerConverter stringToIntegerConverter = new StringToIntegerConverter();
        Integer integer = stringToIntegerConverter.convert( "4123" );

        assertThat( integer ).isEqualTo( 4123 );
    }

    @Test
    void integerToString() {
        IntegerToStringConverter integerToStringConverter = new IntegerToStringConverter();
        String string = integerToStringConverter.convert( 41902 );

        assertThat( string ).isEqualTo( "41902" );
    }

}

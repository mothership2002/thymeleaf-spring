package hello.typeconverter.converter;


import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.assertj.core.api.Assertions.*;

public class ConversionServiceTest {

    @Test
    void conversionService() {
        DefaultConversionService converter = new DefaultConversionService();

        converter.addConverter( new StringToIpPortConverter() );
        converter.addConverter( new IpPortToStringConverter() );

        converter.addConverter( new IntegerToStringConverter() );
        converter.addConverter( new StringToIntegerConverter() );

        assertThat( converter.convert( "18", Integer.class ) ).isEqualTo( 18 );
        assertThat( converter.convert( 18, String.class ) ).isEqualTo( "18" );

        assertThat( converter.convert( "127.0.0.1:8080", IpPort.class ) ).isEqualTo( new IpPort( "127.0.0.1", 8080 ) );
        assertThat( converter.convert( new IpPort( "192.168.0.1", 8080 ), String.class ) ).isEqualTo( "192.168.0.1:8080" );

    }

}

package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class IpPortConverterTest {

    @Test
    void getIpPort() {
        StringToIpPortConverter stringToIpPortConverter = new StringToIpPortConverter();
        IpPort result = stringToIpPortConverter.convert( "123.2.9.1:1023" );

        assertThat( result ).isInstanceOf( IpPort.class );
        assertThat( result.getIp() ).isEqualTo( "123.2.9.1" );
        assertThat( result.getPort() ).isEqualTo( 1023 );
        assertThat( result ).isEqualTo( new IpPort( "123.2.9.1", 1023 ) );

    }

    @Test
    void getStringIpPort() {
        IpPortToStringConverter ipPortToStringConverter = new IpPortToStringConverter();
        String result = ipPortToStringConverter.convert( new IpPort( "192.128.0.1", 10827 ) );
        assertThat( result ).isEqualTo( "192.128.0.1:10827" );
    }

}

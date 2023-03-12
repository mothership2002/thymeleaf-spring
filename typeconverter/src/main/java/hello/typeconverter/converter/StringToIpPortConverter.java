package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StringToIpPortConverter implements Converter<String, IpPort> {

    @Override
    public IpPort convert( String source ) {
        log.info( "Convert source = {} ", source );
        String[] ipAndPort = source.split( ":" );
        return new IpPort( ipAndPort[0], Integer.valueOf( ipAndPort[1] ) );
    }

}

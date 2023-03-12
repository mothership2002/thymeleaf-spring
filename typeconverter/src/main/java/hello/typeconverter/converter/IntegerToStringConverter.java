package hello.typeconverter.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class IntegerToStringConverter implements Converter<Integer, String> {

    @Override
    public String convert( Integer source ) {
        log.info( "convert source = {}", source );
        log.info( "before converted Type = {}", source.getClass() );
        return String.valueOf( source );
    }

}

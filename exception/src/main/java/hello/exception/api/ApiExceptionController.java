package hello.exception.api;

import hello.exception.web.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiExceptionController {

    @GetMapping( "/api/members/{id}" )
    public MemberDto getMember( @PathVariable String id ) {
        if ( id.equals( "ex" ) ) {
            throw new RuntimeException( "ex" );
        }

        if ( id.equals( "bad" ) ) {
            throw new IllegalArgumentException();
        }
        if ( id.equals( "user-ex" ) ) {

            throw new UserException();
        }
        return new MemberDto( id, "hello " + id );
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {

        private String memberId;
        private String name;

    }

}

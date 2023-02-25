package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if(session == null) {
            return "세션이 없습니다.";
        }
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("name = {}, value = {}", name, session.getAttribute(name)));

        log.info("session id = {}", session.getId());
        log.info("session max in active interval = {}", session.getMaxInactiveInterval());
        log.info("create time = {}", new Date(session.getCreationTime()));
        log.info("last access time= {} ", new Date(session.getLastAccessedTime()));
        log.info("is new = {}", session.isNew());
        return null;
    }
}

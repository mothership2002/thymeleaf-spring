package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "mySessionId";
    // 동시성 문제 > concurrentHashMap;
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    public void createSession(Object value, HttpServletResponse resp) {

        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);
        resp.addCookie(new Cookie(SESSION_COOKIE_NAME, sessionId));
    }

    public Object getSession(HttpServletRequest req) {
        Cookie sessionCookie = findCookie(req, SESSION_COOKIE_NAME);
        if (sessionCookie == null) {
            return null;
        }
        return sessionStore.get(sessionCookie.getValue());
    }

    public void expiredSession(HttpServletRequest req) {
        Cookie cookie = findCookie(req, SESSION_COOKIE_NAME);
        if (cookie != null) {
            sessionStore.remove(cookie.getValue());
        }
    }

    public Cookie findCookie(HttpServletRequest req, String cookieName) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            return null;
        }
        // findFirst - 먼저 찾으면 종료 : findAny - 튀어나오면 종료 (병렬)
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny().orElse(null);
    }

}

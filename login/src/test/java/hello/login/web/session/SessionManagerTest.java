package hello.login.web.session;

import hello.login.domain.member.Member;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class SessionManagerTest {

    public SessionManager sessionManager = new SessionManager();

    @Test
    void createSession() {
        MockHttpServletResponse resp = new MockHttpServletResponse();

        // 응답 쿠키세팅
        Member member = new Member();

        sessionManager.createSession(member, resp);

        // 헤더에 쿠키 삽입.
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setCookies(resp.getCookies());

        Object sessionMember = sessionManager.getSession(req);
        assertThat(sessionMember).isEqualTo(member);

        sessionManager.expiredSession(req);

        assertThat(sessionManager.getSession(req)).isNull();
    }

}
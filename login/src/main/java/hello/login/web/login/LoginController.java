package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")//("loginForm")
    public String loginForm(@ModelAttribute LoginForm form) {
        return "login/loginForm";
    }

//    @PostMapping("/login")
//    public String login(@Validated @ModelAttribute LoginForm form, BindingResult br, HttpServletResponse resp) {
//        log.info("{}", br);
//
//        if (br.hasErrors()) {
//            return "login/loginForm";
//        }
//
//        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
//        log.info("login ? {} ", loginMember);
//
//        if (loginMember == null) {
//            br.reject("loginFail", "로그인 실패");
//            return "login/loginForm";
//        }
//        Cookie cookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
//        resp.addCookie(cookie);
//
//        return "redirect:/";
//    }

//    @PostMapping("/login")
//    public String loginV2(@Validated @ModelAttribute LoginForm form, BindingResult br, HttpServletResponse resp) {
//        log.info("{}", br);
//
//        if (br.hasErrors()) {
//            return "login/loginForm";
//        }
//
//        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
//        log.info("login ? {} ", loginMember);
//
//        if (loginMember == null) {
//            br.reject("loginFail", "로그인 실패");
//            return "login/loginForm";
//        }
//
//        sessionManager.createSession(loginMember, resp);
//        return "redirect:/";
//    }

    @PostMapping("/login")
    public String loginV3(@Validated @ModelAttribute LoginForm form,
                          BindingResult br,
                          HttpServletRequest req) {
        log.info("{}", br);

        if (br.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login ? {} ", loginMember);

        if (loginMember == null) {
            br.reject("loginFail", "로그인 실패");
            return "login/loginForm";
        }

        req.getSession().setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
//        getSession(true) : 새로 생성 등등
//        getSession(false) : 기존 세션 반환 / 비생성
        return "redirect:/";
    }

//    @PostMapping("/logout")
//    public String logout(HttpServletResponse resp) {
//        expiredCookies(resp, "memberId");
//        return "redirect:/";
//    }

    public void expiredCookies(HttpServletResponse resp, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }

//    @PostMapping("/logout")
//    public String logoutV2(HttpServletRequest req, HttpServletResponse resp) {
//        sessionManager.expiredSession(req);
//        expiredCookies(resp, sessionManager.getSessionCookieName());
//        return "redirect:/";
//    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}

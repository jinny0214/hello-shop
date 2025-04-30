package jpabook.jpashop.controller;

import jakarta.servlet.http.HttpServletRequest;
import jpabook.jpashop.auth.PrincipalDetails;
import jpabook.jpashop.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class HomeController {

    @GetMapping({"/","/home"})
    public String home(HttpServletRequest request) { // @AuthenticationPrincipal PrincipalDetails userDetails 주입 받아서 권한에 따른 home 노출할 것

        if ("/".equals(request.getServletPath())) {
            return "loginForm";
        } else { // /home
            return "home";
        }
    }

    @GetMapping("/loginForm")
    public String login() {

        return "loginForm";
    }

//    @GetMapping("/test")
//    public String test(@AuthenticationPrincipal PrincipalDetails userDetails) {
//        // USER ROLE에 맞는 view 리턴
//        return "home";
//    }

    @GetMapping("/joinForm")
    public String loginForm(Model model) {

        model.addAttribute("memberForm", new MemberForm());
        return "joinForm";
    }
}

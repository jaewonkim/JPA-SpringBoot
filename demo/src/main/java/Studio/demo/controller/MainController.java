package Studio.demo.controller;

import Studio.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private static final String SESSION_ID = "sessionId";
    private final MemberService memberService;

    @GetMapping(value = "/view")
    public String view(){
        return "view";
    }

    @GetMapping(value = "/info")
    public String MyPage(){
        return "/members/MyPage";
    }


    @RequestMapping("/main")
    public String home(HttpServletRequest request){

        HttpSession session = request.getSession(false);

        if (session != null) {
            return "main";
        }

        log.info("home controller");
        return "home";
    }
}

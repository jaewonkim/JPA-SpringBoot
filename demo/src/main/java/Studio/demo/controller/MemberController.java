package Studio.demo.controller;

import Studio.demo.controller.Form.EditForm;
import Studio.demo.controller.Form.FindForm;
import Studio.demo.controller.Form.LoginForm;
import Studio.demo.controller.Form.MemberForm;
import Studio.demo.domain.Member;
import Studio.demo.domain.Reservation;
import Studio.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private static final String LOGIN_MEMBER = "LOGIN_MEMBER";
    private final MemberService memberService;

    
    //회원가입
    @GetMapping(value="/members/join")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
    return "members/createMemberForm";
    }
    @PostMapping(value = "/members/join")
    public String create(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }
        memberService.join(form.getId(),form.getName(),form.getPhoneNumber(),form.getPassword());
        return "redirect:/";
    }
    
    
    //계정찾기
    @GetMapping(value = "/members/find")
    public String findForm(Model model) {
        model.addAttribute("findForm", new FindForm());
        return "members/findMemberAccount";
    }
    @PostMapping(value = "/members/find")
    public String find(@Valid FindForm form, Model model) {

        Member member = memberService.findId(form.getName(),form.getPhoneNumber());
        model.addAttribute("member",member);
        return "members/findMemberResult";
    }

    
    //로그인
    @GetMapping(value = "/members/login")
    public String loginForm(Model model,HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            return "main";
        }
        model.addAttribute("loginForm", new LoginForm());
        return "members/LoginMember";
    }
    @PostMapping(value = "/members/login")
    public String login(@Valid LoginForm form, Model model, HttpServletRequest request){


        try {
            Member member = memberService.login(form.getId(), form.getPassword());
            model.addAttribute("member",member);

            HttpSession session = request.getSession();

            session.setAttribute(LOGIN_MEMBER, member);
            
        }catch (Exception e){
            return "members/LoginMember";
        }

        return "main";
    }

    
    
    
    //로그아웃
    @GetMapping(value = "/members/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return "home";
    }


    //개인 예약 리스트 조회
    @GetMapping(value = "/members/mylist")
    public String MyList(HttpServletRequest request, Model model) throws Exception {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "home";
        }

        Member member = (Member) session.getAttribute(LOGIN_MEMBER);

        List<Reservation> Reservations = memberService.myReservation(member);

        model.addAttribute("Reservations", Reservations);

        return "members/MyList";
    }



    //계정 정보 편집
    @GetMapping(value = "/members/editaccount")
    public String editAccount(Model model,HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        Member member = (Member) session.getAttribute(LOGIN_MEMBER);
        model.addAttribute("member", member);

        model.addAttribute("editForm", new EditForm());
        return "members/EditInfo";
    }
    @PostMapping(value = "/members/editaccount")
    public String editAccount(@Valid EditForm form, HttpServletRequest request, Model model) throws Exception {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "home";
        }

        Member member = (Member) session.getAttribute(LOGIN_MEMBER);
        model.addAttribute("member", member);

        String newName = member.getName();
        String newPhoneNumber = member.getPhoneNumber();
        String newPassword = member.getPassword();

        if(!form.getName().equals(""))
            newName = form.getName();
        if(!form.getPhoneNumber().equals(""))
            newPhoneNumber = form.getPhoneNumber();
        if(!form.getPassword().equals(""))
            newPassword = form.getPassword();

        memberService.update(member.getId(),newName,newPhoneNumber,newPassword);

        return "members/MyPage";
    }


}

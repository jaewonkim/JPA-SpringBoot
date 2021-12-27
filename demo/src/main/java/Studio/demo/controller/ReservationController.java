package Studio.demo.controller;


import Studio.demo.controller.Form.DateForm;
import Studio.demo.controller.Form.ReservationForm;
import Studio.demo.domain.Member;
import Studio.demo.domain.PartTime;
import Studio.demo.domain.Reservation;
import Studio.demo.service.MemberService;
import Studio.demo.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ReservationController {

    private static final String LOGIN_MEMBER = "LOGIN_MEMBER";
    private final ReservationService reservationService;
    private final MemberService memberService;


    //예약 생성
    @GetMapping(value="/reserv")
    public String createForm(Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        model.addAttribute("reservationForm", new ReservationForm());
        return "reservations/createReservForm";
    }
    @PostMapping(value = "/reserv")
    public String create(@Valid ReservationForm form, BindingResult result,HttpServletRequest request) {
        if (result.hasErrors()) {
            return "reservations/createReservForm";
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        Member member = (Member) session.getAttribute(LOGIN_MEMBER);

        reservationService.reservation(form.getContent(),form.getReservationDate(),form.getPartTime(),member);
        return "redirect:/main";
    }



    //주문 목록 + 날짜별
    @GetMapping("/dateList")
    public String orderList(@ModelAttribute("date") DateForm date, Model model,HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }
        List<Reservation> reservations = reservationService.reservationsDate(date.getReservationDate());
        model.addAttribute("reservations", reservations);

        List<Reservation> all = reservationService.reservations();
        model.addAttribute("all", all);
        return "reservations/dateList";
    }



    //개인 주문 리스트 수정
    @GetMapping("items/{itemId}/edit")
    public String updateReservationForm(@PathVariable("itemId") Long itemId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        model.addAttribute("form", new ReservationForm());

        return "reservations/edit";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateReservation(@ModelAttribute("form") ReservationForm editForm,@PathVariable("itemId") Long itemId,Model model,HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }
        Member member = (Member) session.getAttribute(LOGIN_MEMBER);

        Reservation reservation = reservationService.findOne(itemId);

        String newContent = reservation.getContent();
        Date newDate = reservation.getReservationDate();
        PartTime newTime = reservation.getPartTime();

        if(!editForm.getContent().equals(""))
            newContent = editForm.getContent();
        if(editForm.getReservationDate()!=null)
            newDate = editForm.getReservationDate();
        if(editForm.getPartTime()!=null)
            newTime = editForm.getPartTime();


        reservationService.updateReservation(itemId,newContent,newDate,newTime,member);

        return "members/MyPage";
    }



    //개인 주문 취소
    @GetMapping("items/{itemId}/cancel")
    public String cancel(@PathVariable("itemId") Long itemId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }
        Member member = (Member) session.getAttribute(LOGIN_MEMBER);

        reservationService.cancel(itemId,member);
        return "members/MyPage";
    }
}

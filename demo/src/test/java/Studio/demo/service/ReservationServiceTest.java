package Studio.demo.service;

import Studio.demo.domain.Member;
import Studio.demo.domain.PartTime;
import Studio.demo.domain.Reservation;
import Studio.demo.repository.MemberRepository;
import Studio.demo.repository.ReservationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ReservationServiceTest {
    @Autowired
    ReservationService reservationService;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    MemberService memberService;

    @Autowired
    EntityManager em;



   @Test
   public void reservation() throws Exception {
       //given
       String content = "파티사진을 찍고싶습니다.";
       Date date = new Date(1996/06/05);
       PartTime partTime = PartTime.A;
       Member member = join("wodnjs2","재원","01011111111","123123!!");

       //when
       Long saveId = reservationService.reservation(content,date,partTime,member);
       //then
       assertEquals(saveId,reservationRepository.findOne(saveId).getId());
   }


   @Test(expected = IllegalStateException.class)
   public void validateDateTime() throws Exception {
       //given
       Date date = new Date(1996/06/05);
       PartTime partTime = PartTime.A;
       //when
       reservation();
       //then
       reservationService.validateDateTime(date,partTime);
       
       fail("이게 실패한다면 아마도 예약 자체가 안됐을 확률이 높음");
    }


//
//    @Test
//    public void cancel() throws Exception {
//        //given
//        List<Reservation> reservations= reservationService.reservations();
//        Long id = 1L;
//        //when
//        reservation();
//        reservationService.cancel(id);
//        //then
//        assertEquals(reservations,reservationRepository.findAll());
//    }

    @Test
    public void updateReservation() throws Exception {
        //given
        String content = "빨리빨리~";
        Date date = new Date(1996/06/04);
        PartTime partTime = PartTime.C;
        //when
        reservation();
        //reservationService.updateReservation(1l,content,date,PartTime.A);
        //then
        assertEquals(content, reservationRepository.findOne(1l).getContent());
    }


    public Member join(String id,String name,String phoneNumber, String password) {
        String savedId = memberService.join(id, name, phoneNumber, password);
        return memberService.login(id,password);
    }
}
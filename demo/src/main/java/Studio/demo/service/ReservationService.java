package Studio.demo.service;


import Studio.demo.domain.Member;
import Studio.demo.domain.PartTime;
import Studio.demo.domain.Reservation;
import Studio.demo.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    //예약
    @Transactional
    public Long reservation(String content, Date date, PartTime partTime, Member member){
        Reservation reservation = new Reservation(content, date, partTime,member);

        try{
            validateDateTime(date,partTime);
        }
        catch (IllegalStateException e){
            reservation.cancel(member);
        }
        validateDateTime(date,partTime);

        reservationRepository.save(reservation);

        return reservation.getId();
    }
    public Reservation findOne(Long id){
        return reservationRepository.findOne(id);
    }

    //겹치는 날짜와 시간 확인
    public void validateDateTime(Date date,PartTime partTime){
        Reservation reservation = reservationRepository.findAllTime(partTime,date);

        if(reservation!=null){
            throw new IllegalStateException("이미 예약된 날짜/시간입니다. 다른 날짜/시간을 선택해주세요");
        }
    }

    //취소
    @Transactional
    public void cancel(Long id,Member member){
        Reservation reservation = findOne(id);
        reservation.delete(member);// 내일 마저 보자 
        reservationRepository.DeleteById(id);
    }


    //예약 날짜, 시간, 내용 수정
    @Transactional
    public void updateReservation(Long id,String content,Date date, PartTime partTime,Member member){
        Reservation reservation = reservationRepository.findOne(id);

        Reservation reservation1 = reservationRepository.findAllTime(partTime,date);
        if(reservation1!=null && reservation.getId()!=reservation1.getId()){
            throw new IllegalStateException("이미 예약된 날짜/시간대 입니다.");
        }

        reservation.update(content,date,partTime, member);
    }


    //조회
    public List<Reservation> reservations(){
        return reservationRepository.findAll();
    }

    public List<Reservation> reservationsDate(Date reservationDate){
        return reservationRepository.findAllByDate(reservationDate);
    }
    //date로 조회
    public List<Reservation> sameDateReservations(Date date){
        return reservationRepository.findAllByDate(date);
    }


}

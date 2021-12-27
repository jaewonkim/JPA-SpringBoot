package Studio.demo.repository;


import Studio.demo.domain.Member;
import Studio.demo.domain.PartTime;
import Studio.demo.domain.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final EntityManager em;

    public void save(Reservation reservation){
        em.persist(reservation);
    }

    public Reservation findOne(Long id){
        return em.find(Reservation.class,id);
    }


    public List<Reservation> findAll(){
        return em.createQuery("select m from Reservation m",Reservation.class).getResultList();
    }

    //취소
    public void DeleteById(Long id){

        Reservation reservation = em.find(Reservation.class,id);
        if(reservation==null)
            return;

        em.remove(reservation);
    }

    //예약리스트를 날짜로 검색
    public List<Reservation> findAllByDate(Date reservationDate){
        String sql = "select r from Reservation r where r.reservationDate = : reservationDate";

        return em.createQuery(sql,Reservation.class)
                .setParameter("reservationDate",reservationDate)
                .getResultList();
    }

    //날짜와 파트타임이 겹치는 예약 검색
    public Reservation findAllTime(PartTime partTime, Date reservationDate){
        String sql = "select r from Reservation r where r.reservationDate = : reservationDate and r.partTime = : partTime" ;

        List<Reservation> reservation = em.createQuery(sql,Reservation.class)
                .setParameter("reservationDate",reservationDate)
                .setParameter("partTime",partTime)
                .getResultList();

        if(reservation.isEmpty()) return null;

        return reservation.get(0);
    }


}

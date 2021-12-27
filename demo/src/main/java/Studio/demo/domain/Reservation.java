package Studio.demo.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
public class Reservation {

    @Id @GeneratedValue
    @Column(name="reservation_id")
    private Long id;

    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reservationDate;

    @Enumerated(EnumType.STRING)
    private PartTime partTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected Reservation(){}

    @Builder
    public Reservation(String content, Date date,PartTime partTime, Member member){
        this.content = content;
        this.reservationDate = date;
        this.partTime = partTime;
        this.member = member;
        //연관관계 편의 명령어
        member.getReservationList().add(this);
    }

    public void cancel(Member member){
        member.getReservationList().remove(this);
    }

    public void update(String content, Date date,PartTime partTime,Member member){
        member.getReservationList().remove(this);
        this.content = content;
        this.reservationDate = date;
        this.partTime = partTime;
        member.getReservationList().add(this);
    }

    //연관관계 편의 메서드
    public void delete(Member member){
        member.getReservationList().remove(this);
    }
    //List().remove(Object o) 메소드를 제대로 동작시키기 위한 메소드 재정의
    @Override
    public boolean equals(Object obj){
        Reservation reservation = (Reservation)obj;

        if(this.getId() == ((Reservation) obj).getId())
            return true;

        return false;
    }
}

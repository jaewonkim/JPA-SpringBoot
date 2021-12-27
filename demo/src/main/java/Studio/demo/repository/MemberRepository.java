package Studio.demo.repository;


import Studio.demo.domain.Member;
import Studio.demo.domain.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m",Member.class).getResultList();
    }

    //아이디로 멤버 검색
    public Member findById(String id){
        return em.find(Member.class,id);
    }
    //폰번호로 멤버 검색
    public Member findByPhone(String phoneNumber){
        List<Member> member =  em.createQuery("select m from Member m where m.phoneNumber = :phoneNumber", Member.class)
                .setParameter("phoneNumber", phoneNumber)
                .getResultList();

        if (member.isEmpty()) return null;
        return member.get(0);
    }

    // Privacy를 이용한 멤버 검색
    public Member findByPrivacy(String name,String phoneNumber){
        String sql = "select m from Member m where m.name = : name and m.phoneNumber = : phoneNumber";
        List<Member> member = em.createQuery(sql,Member.class)
                .setParameter("name", name)
                .setParameter("phoneNumber", phoneNumber)
                .getResultList();

        if (member.isEmpty()) return null;
        return member.get(0);
    }

    //login information을 이용한 멤버 검색
    public Member findByLogInfo(String id,String password){
        String sql = "select m from Member m where m.id = : id and m.password = : password";
        List<Member> member = em.createQuery(sql,Member.class)
                .setParameter("id", id)
                .setParameter("password", password)
                .getResultList();

        if (member.isEmpty()) return null;
        return member.get(0);
    }

}

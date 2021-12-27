package Studio.demo.service;

import Studio.demo.domain.Member;
import Studio.demo.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void join() throws Exception {
        //given
        String id ="wodnjs2";
        String name="김재원";
        String phoneNumber="01011111111";
        String password="123123!!";

        //when

        String savedId = memberService.join(id,name,phoneNumber,password);
        //then

        assertEquals(savedId,(memberRepository.findById(id)).getId());
        assertEquals(savedId,(memberRepository.findByPhone(phoneNumber)).getId());
        assertEquals(savedId,(memberRepository.findByLogInfo(id,password)).getId());
        assertEquals(savedId,(memberRepository.findByPrivacy(name,phoneNumber)).getId());
    }


    @Test
    public void login() throws Exception {
        //given
        join();
        String id ="wodnjs2";
        String password="123123!!";
        //when
        Member member1 = memberRepository.findById(id);

        Member member2 = memberService.login(id,password);
        //then
        assertEquals(member2,member1);
    }



    @Test
    public void update() throws Exception {
        //given
        join();
        String name = "클로이 데커";
        String phoneNumber = "01088888888";
        String password = "555!!";
        //when
        Member member = memberRepository.findById("wodnjs2");
        memberService.update(member.getId(),name,phoneNumber,password);
        //then
        assertEquals(member.getName(),name);
    }
}
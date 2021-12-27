package Studio.demo.service;

import Studio.demo.domain.Member;
import Studio.demo.domain.Reservation;
import Studio.demo.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

        private final MemberRepository memberRepository;
    //회원가입
    @Transactional
    public String join(String id, String name, String phoneNumber, String password){
        Member member = new Member(id,name,phoneNumber,password);
        validateDuplicateMember(id,phoneNumber);
        memberRepository.save(member);
        return member.getId();
    }
    //유효 아이디 검증, 유효 번호검증
    private void validateDuplicateMember(String id, String phoneNumber) {
        //EXCEPTION
        Member sameId = memberRepository.findById(id);
        Member samePh = memberRepository.findByPhone(phoneNumber);

        if(sameId!=null){
            throw new IllegalStateException("이미 존재하는 ID 입니다.");
        }
        if(samePh!=null){
            throw new IllegalStateException("이미 존재하는 phoneNumber 입니다.");
        }

    }


    //계정찾기
    public Member findId(String name,String phoneNumber){
        Member member = memberRepository.findByPrivacy(name,phoneNumber);
        return member;
    }

    //본인 예약내역 조회
    public List<Reservation> myReservation(Member member){
        return member.getReservationList();
    }


    //로그인
    public Member login(String id,String password){
        Member member = memberRepository.findByLogInfo(id,password);
        if(member == null){
            throw new IllegalStateException("존재하지 않는 id 혹은 password 입니다.");
        }
        return member;
    }


    //이름, 폰번호, 비밀번호 수정
    @Transactional
    public void update(String id,String name,String phoneNumber,String password){
        Member member = memberRepository.findById(id);
        Member member1 = memberRepository.findByPhone(phoneNumber);

        if(member1!=null && !member1.getId().equals(member.getId())){
            throw new IllegalStateException("이미 존재하는 phoneNumber 입니다.");
        }

        member.update(name,phoneNumber,password);
    }

}

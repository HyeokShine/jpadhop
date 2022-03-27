package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //데이터 변경이 일어나는 경우 기본적으로 Transactional안에 있어야한다., 조회시 성능 최적화 쓰기에는 쓰면 안된다(readOnly = true)
@RequiredArgsConstructor //final이 있는 필드만 가지고 생성자를 만들어준다 롬복의 기능
public class MemberService {

    private final MemberRepository memberRepository; //final 권장, 생성자 값 안 넣어줄 경우 체크 할수있다

    //@Autowired //생성자가 1개인 경우 스프링이 자동으로 @Autowired 해준다.
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원가입
     */
    @Transactional
    public Long join(Member member) {
        ValidateDuplicateMember(member); //중복회원 검증
        memberRepository.save(member);
        return member.getId();

    }

    // 중복 체크 시 맴버의 이름을 db에 유니크 제약조건으로 설정 권장(동시에 같은 이름으로 가입하는 경우 문제 발생 방지)
    private void ValidateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName()); //이름으로 조회
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 한건 조회
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }


}

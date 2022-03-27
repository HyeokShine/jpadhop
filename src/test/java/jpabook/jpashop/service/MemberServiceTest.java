package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // jupiter5부터는 SpringBootTest만 선언해도 문제없다 이미 내장하고있다.
@Transactional //데이터 변경이 있으면 사용한다, 있어야 롤백가능
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
//    @Autowired
//    EntityManager em;

     @Test
//     @Rollback(false) 기본은 true db에 실제로 데이터를 남기지않는다.
      public void 회원가입() throws Exception {
         //given
         Member member = new Member();
         member.setName("kim");

         //when
         Long savedId = memberService.join(member);

         //then
//         em.flush(); insert 쿼리문을 남긴다
         assertEquals(member, memberRepository.findOne(savedId)); //JPA 같은 Transactional 안에서 pk값(id)이 같으면 영속성 컨텍스트안에서 같은 객체로 인식한다.
     }

      @Test //junit4 사용법(expected = IllegalStateException.class)
       public void 중복회원_예외() throws Exception {
          //given
          Member member1 = new Member();
          member1.setName("kim");

          Member member2 = new Member();
          member2.setName("kim");
           
          //when
          memberService.join(member1);

//          try {
//              memberService.join(member2); //예외가 발생해야한다
//          } catch (IllegalStateException e) {
//              return;
//          }

          //then
          assertThrows(IllegalStateException.class, () ->
              memberService.join(member2), "예외가 발생해야 한다."
          );
//          fail("예외가 발생해야 한다."); //테스트 코드가 잘못 실행되고 있을때 발생

      }
}
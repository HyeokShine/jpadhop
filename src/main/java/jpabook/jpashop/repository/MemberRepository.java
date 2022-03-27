package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor //final이 있는 필드만 가지고 생성자를 만들어준다 롬복의 기능
public class MemberRepository {

    private final EntityManager em; //대체 가능 //1

//    @PersistenceContext   //2
//    private EntityManager em;

//    @Autowired //스프링 부트는  @PersistenceContext를 @Autowired로 대체 가능하게 지원해준다.
//    private EntityManager em; //3
//    public MemberRepository(EntityManager em) {
//        this.em = em;
//    }

    public void save(Member member) {
        em.persist(member); //JPA 저장 로직
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id); //JPA 아이디로 조회해서 반환 로직, 단건 조회
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList(); //Jpa 엔티티 객체 대상 쿼리, 전체 조회
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class).setParameter("name", name).getResultList(); //파라미터 바인딩, 이름으로 회원 조회
    }



}

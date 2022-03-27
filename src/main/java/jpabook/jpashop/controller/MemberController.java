package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //화면 열기
    @GetMapping("members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm()); //key, value
        return "members/createMemberForm";
    }

    //데이터 등록 @Valid MemberForm 안에 있는 validation 사용, BindingResult @Valid에 오류가 있을시 BindingResult에 담긴다.
   @PostMapping("members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        if(result.hasErrors()) {
            return "members/createMemberForm"; //메세지를 뷰로 보낸다
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress((address));

        memberService.join(member);
        return "redirect:/";
   }

   @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members); //key, value
       return "members/memberList";
   }
}
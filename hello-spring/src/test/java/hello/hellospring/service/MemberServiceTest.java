package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memberRepository;


    @AfterEach
    public void AfterEach(){
        memberRepository.clearStore();
    }

    @BeforeEach
    public void BeforeEach(){
        memberRepository = new MemoryMemberRepository();
        //memberService객체 입장에서는 repository가 주입되서 binding되는 부분
        // 이걸 dependency injection(DI)라 한다
        memberService = new MemberService(memberRepository);
    }

    @Test
    void join() {
        //give
        Member member = new Member();
        member.setName("hello");
        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findByOne(saveId).get();
        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
    }

    @Test
    public void duplicateException(){
        //given
        Member member = new Member();
        member.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member);
        // 비효울적인 방법
//        try{
//            memberService.join(member2);
//            fail(); //예외가 나와야 하지만 흘렀기 때문에 실패
//        }catch (IllegalStateException e){
//            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findBy() {
    }
}
# Springboot 기록하는 습관

![스크린샷 2021-07-27 오후 11.32.32](/Users/yonghyun/Desktop/60112334/사진/스크린샷 2021-07-27 오후 11.32.32.png)

- 스프링 컨테이너 진입 후 viewResolver를 통해 매핑할 html을 찾음
- Thymeleaf 템플릿 엔진으로 resources/templates/{ViewName}+'.html'을 찾도록 함



### 서버 빌드하기

$:./gradlew build

build/libs 이동

$: java -jar [프로젝트명]+[version snapshot].jar 파일 실행



### 스프링 웹 개발 기초

- 정적 컨텐츠
- MVC와 템플릿 엔진
- API



![스크린샷 2021-07-29 오후 10.52.36](/Users/yonghyun/Desktop/60112334/사진/스크린샷 2021-07-29 오후 10.52.36.png)



hello-static Controller를 먼저 찾고 없으면 resources/static/하위 경로에서 파일



### MVC와 템플릿 엔진



# API

@ResponseBody

- default 는 json 형식으로 http 응답에 넣는다
- ResponseBody는 ViewResolver 대신 httpMessageConverter가 동작
- 단순 문자열 일때StringHttpMessageConverter 동작
-  객체일때, MappingJackson2HttpMessageConverter가 동작





# TDD

- Test Driven development
- 개발을 구현하기 전에 Test를 먼저 만들어놓고 개발을 진행
- @AfterEach 를 통한 각 Test가 끝난 후 후처리 로직 진행

Ex) save, findByName 테스트가 끝날때마다 afterEach 메서드 실행

```java
@AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        memberRepository.save(member);
        Member result = memberRepository.findById(member.getId()).get();
        Assertions.assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member member = new Member();
        member.setName("spring1");
        memberRepository.save(member);

        Member member2 = new Member();
        member2.setName("spring2");
        memberRepository.save(member2);

        Member result = memberRepository.findByName("spring1").get();
        Assertions.assertThat(member).isEqualTo(result);
    }
```

inteliJ 단축기

 cmd + option + v -> 반환형 단축키

cmd +shift + t -> JUnit test create 단축키

Shift +ctrl + r -> test run 단축키



# Service layer Assertions Test

간략한 DI(dependency injection)

MemberService class

```java
public class MemberService{
  private MemberRepository memberRepository;
  public MemberService(MemberRepository memberRepository){
    this.memberRepository = memberRepository;
  }
}	

```

Test.class

```java
public void BeforeEach(){
  memberRepository = new MemoryMemberRepository();
  //memberService객체 입장에서는 repository가 주입되서 binding되는 부분
  // 이걸 dependency injection(DI)라 한다
  memberService = new MemberService(memberRepository);
}
```



# Spring bean과 의존관계

- 생성자에 @Autowired가 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아서 넣어준다. 이렇게 객체 의존관계를 DI라 한다

```java

public class HelloController{
	MemberService memberService;
  @Autowired
  public HelloController(MemberService memberService){
    this.memberService = memberService;
  }
  //여기서 memberService가 스프링 빈으로 등록되어 있지 않아서 에러
}
```



### 스프링 빈을 등록하는 2가지 방법

- 컴포넌트 스캔과 자동 의존관계 설정
- 자바 코드로 직접 스프링 빈 등록하기
- ex) service, controller, repository 어노테이션에 component 어노테이션이 등록되어 있음

> 참고: 스프링은 스프링 컨테이너에 스프링 빈을 등록할 때, 기본으로 싱글톤으로 등록한다 (유일하게 하나만 등록해서 공유한다) 따라서 같은 스프링 빈이면 모두 같은 인스턴스다. 설정으로 싱글톤이 아니게 설정할 수 있지만, 특별한 경우를 제외하면 대부분 싱글톤으로 사용한다.





### 자바 코드로 직접 스프링 빈 등록하기

```java
@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
```



> 참고: DI에는 3가지 방법이 있다. 생성자 주입, 필드 주입, Setter 주입
>
> 의존관계가 실행중에 바뀌는 경우는 거의 없으므로 생성자 주입을 권장
>
> 아래 코드 확인

```java
@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
```

> 참고: 실무에서는 주로 정형화된 컨트롤러, 서비스,리포지토리 같은 코드는 컴포넌트 스캔을 사용한다. 그리고 정형화되지 않거나, 상황에 따라 구현 클래스를 변경해야 하면 설정을 통해 스프링 빈으로 등록한다(ex, Repository구현체를 실제 DB 연결하는 구현체로 변경)
>
> 주의: @Autowired 를 통한 DI는 스프링이 관리하는 객체에서만 동작한다.



# 기능구현



- 홈화면
- 회원가입
- 회원리스트



# 스프링 DB 접근 기술



![스크린샷 2021-08-06 오후 4.45.40](/Users/yonghyun/Desktop/60112334/사진/스크린샷 2021-08-06 오후 4.45.40.png)



- Spring에 장점 인터페이스를 구현하는 구조로 DB의 구조를 바꿀때의 Config파일만 수정으로 변경가능
- OCP(open close principle) 개방 폐쇄의 원리(확장은 열려있고, 수정변경에는 닫혀있다.)

```java
    public MemberRepository memberRepository(){
      // return new MemoryMemberRepository();
        return new JdbcMemberRepository(dataSource);
    }
```



#  @SpringBootTest

- @SpringBootTest : 스프링 컨테이너와 테스트를 함께 실행한다
- @Transactional: 테스트 케이스에 이 어노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후에 항상 롤백한다. 이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.



# SpringJdbcTemplate



# JPA

- JPA는 기존의 반복 코드는 물론이고, 기본적인 SQL도 JPA가 직접 만들어서 실행해준다.
- JPA를 사용하면, SQL과 데이터 중심의 설계에서 객체 중심의 설계로 패러다임을 전환할 수 있다.
- JPA를 사용하면 개발 생산성을 크게 늘릴 수 있다.
- JPA는 인터페이스고 hibernate가 구현체이다
- 


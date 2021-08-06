# Springboot 기록하는 습관



![스크린샷 2021-07-27 오후 11.32.32](/Users/yonghyun/Desktop/스크린샷 2021-07-27 오후 11.32.32.png)



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



![스크린샷 2021-07-29 오후 10.52.36](/Users/yonghyun/Desktop/스크린샷 2021-07-29 오후 10.52.36.png)



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



# Service layer test

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








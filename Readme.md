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
- 


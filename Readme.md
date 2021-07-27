# Springboot 기록하는 습관



![스크린샷 2021-07-27 오후 11.32.32](/Users/yonghyun/Desktop/스크린샷 2021-07-27 오후 11.32.32.png)



- 스프링 컨테이너 진입 후 viewResolver를 통해 매핑할 html을 찾음
- Thymeleaf 템플릿 엔진으로 resources/templates/{ViewName}+'.html'을 찾도록 함



### 서버 빌드하기

$:./gradlew build

build/libs 이동

$: java -jar [프로젝트명]+[version snapshot].jar 파일 실행




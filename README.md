# 크라운드 - 백엔드

### 프로젝트 주요 관심사

- 도메인 모델 패턴에 맞춘 설계와 값 객체, 일급 컬렉션을 활용한 객체 지향적인 설계
- 계층형 아키텍처의 역할을 명확히 분리하여 패키지로 분리하고, 의존성을 최소화하여 유연한 아키텍처로 구축

<br/>

### 포스팅

[#1. 크라운드를 시작하며](https://jay-ya.tistory.com/153)<br/>
[#2. 프록시 컬렉션 문제 이슈](https://jay-ya.tistory.com/157)<br/>
#3. 객체 지향적인 설계와 캡슐화<br/>
#4. 동적 쿼리와 페이지네이션<br/>
#5. fetch Join과 N+1 문제에 대한 고찰 (`@ManyToOne`, `@OneToMany`)<br/>
#6. 변경 감지와 orphanremoval, cascade<br/>
#7. JWT 토큰 방식<br/>
#8. git branch 전략<br/>
#9. [에러][detached entity passed to persist] Cascade Error<br/>

<br/>

### Wiki
[https://github.com/cround-team/cround-server/wiki](https://github.com/cround-team/cround-server/wiki)

<br/>

### 클래스 다이어그램
![cround-domain](https://github.com/cround-team/cround-server/assets/64416833/2537668e-5281-438c-b1fe-e328bb666761)

<br/>

### API

| Category  | Method |                          URI                          |      Desc       |  
|:---------:|:------:|:-----------------------------------------------------:|:---------------:|
|  Member   |  POST  |                     /api/members                      |      회원가입       |
|     -     |  POST  |                      /auth/login                      |     일반 로그인      |
|     -     |  GET   |                /auth/{provider}/login                 |     소셜 로그인      |
|     -     |  GET   |           /api/members/me/shorts/bookmarks            |    북마크한 숏클래스    |
|     -     |  GET   |           /api/members/me/boards/bookmarks            |    북마크한 콘텐츠     |
|     -     |  GET   |          /api/members/me/creators/followings          |   팔로우한 크리에이터    |
|     -     |  POST  |            /api/members/validations/email             |    이메일 중복 확인    |
|     -     |  POST  |           /api/members/validations/nickname           |    닉네임 중복 확인    |
|  Creator  |  POST  |                     /api/creators                     |    크리에이터 참여     |
|     -     |  GET   | /api/creators?cursorId=&keyword=&sort=&filter=&size=& |   크리에이터 목록 조회   |
|     -     |  GET   |               /api/creators/{creatorId}               |   크리에이터 상세페이지   |
|     -     |  GET   |          /api/creators/validations/nickname           | 크리에이터 닉네임 중복 확인 |
|  Follow   |  POST  |                /api/members/following                 |    크리에이터 팔로잉    |
|     -     | DELETE |                /api/members/following                 |  크리에이터 팔로잉 취소   |
| ShortForm |  POST  |                      /api/shorts                      |     숏클래스 등록     |
|     -     |  GET   |  /api/shorts?cursorId=&keyword=&sort=&filter=&size=&  |   숏클래스 목록 조회    |
|     -     |  GET   |                /api/shorts/{shortsId}                 |   숏클래스 상세 페이지   |
|   Board   |  POST  |                      /api/boards                      |     콘텐츠 등록      |
|     -     |  GET   |  /api/boards?cursorId=&keyword=&sort=&filter=&size=&  |    콘텐츠 목록 조회    |
|     -     |  GET   |                 /api/boards/{boardId}                 |   콘텐츠 상세 페이지    |
|  Review   |  POST  |           /api/creators/{creatorId}/reviews           |   크리에이터 리뷰 등록   |
|     -     |  GET   |   /api/creators/{creatorId}/reviews?cursorId=&size=   |   크리에이터 리뷰 조회   |
|   Like    |  POST  |              /api/boards/{boardId}/likes              |     콘텐츠 좋아요     |
|     -     | DELETE |              /api/boards/{boardId}/likes              |   콘텐츠 좋아요 취소    |
|     -     |  POST  |             /api/shorts/{shortsId}/likes              |    숏클래스 좋아요     |
|     -     | DELETE |              /api/shorts/{shortsId}/likes              |   숏클래스 좋아요 취소   |
| Bookmark  |  POST  |              /api/boards/{boardId}/bookmarks              |     콘텐츠 북마크     |
|     -     | DELETE |              /api/boards/{boardId}/bookmarks              |   콘텐츠 북마크 취소    |
|     -     |  POST  |             /api/shorts/{shortsId}/bookmarks              |    숏클래스 북마크     |
|     -     | DELETE |              /api/shorts/{shortsId}/bookmarks              |   숏클래스 북마크 취소   |

<br/>

### TECH

**Backend**

- Java 11
- Spring Boot 2.7.11
- Spring Data JPA
- Spring Security
- QueryDSL-JPA 5.0.0
- H2, MySQL
- Flyway
- JWT
- OAuth2

**Infra**

- AWS EC2
- AWS RDS
- AWS S3

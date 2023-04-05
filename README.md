# hybridApp sample admin입니다.
egov기반으로 작성되었으며, 회원, 티켓 관리, 기타 상품관리등의 기능이 탑재되어있습니다.


## 🖥️ 프로젝트 소개
리조트 프로젝트를 egov로 시작하였을 때, 어떤식으로 접근해야할지 모르는 개발자들을 위해 그에 대한 샘플을 제작하였습니다.
<br>

## 🕰️ 개발 기간
* 23.01.12일 - 23.03.22일

### ⚙️ 개발 환경
- `Java 8`
- `JDK 1.8.0`
- **IDE** : Intellij
- Egov setting
  . **Framework** : Springboot(2.x)
  . **Database** : Oracle DB(11xe)
  . **ORM** : Mybatis

## 📌 주요 기능
#### 로그인
- DB값 검증
- ID찾기, PW찾기
- 로그인 시 쿠키(Cookie) 및 세션(Session) 생성
#### 회원가입
- 본인인증 nice api 연동
- ID 중복 체크
#### 마이 페이지
- 보유티켓 확인
- 회원정보 변경
- 결제내역 확인
- 결제취소
#### 티켓 구매
- 티켓 정보제공
- 티켓 선택 및 구매
- 결제 페이지 nice pay api 연동 
- 결제 완료
#### 숙박 예약
- 날짜 선택
- 시설 선택
- 결제 페이지 nice pay api 연동 
- 예약 완료
#### 메인 페이지
- 티켓 및 숙박 등 기능 제공
#### 1대1문의 및 공지사항 - <a href="" >상세보기 - WIKI 이동</a> 
- 글 작성, 읽기, 수정, 삭제(CRUD)
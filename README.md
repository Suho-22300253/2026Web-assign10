## 1. 프로젝트 소개

### 주제

무비 데이터 관리 시스템

crud 기능 수행

### 주요 기능
데이터 추가, 삭제, 수정, 조회 기능

이때 조회는 감독이름, 감독이름과 특정 평점 초과, 그리고 특정 감독의 영화를 평점 내림차순으로 볼 수 있다.

- **GitHub Repository:** :
- **Render Deployment URL:** 

---

## 2. 주요 구현 내용 및 결과
### Query Method 
![Query Method 감독 이름으로 찾기.png](captures/Query%20Method%20%EA%B0%90%EB%8F%85%20%EC%9D%B4%EB%A6%84%EC%9C%BC%EB%A1%9C%20%EC%B0%BE%EA%B8%B0.png)

![Query Method 감독 이름 평점 이상인 영화.png](captures/Query%20Method%20%EA%B0%90%EB%8F%85%20%EC%9D%B4%EB%A6%84%20%ED%8F%89%EC%A0%90%20%EC%9D%B4%EC%83%81%EC%9D%B8%20%EC%98%81%ED%99%94.png)

![Query Method 감독 이름 평점 순으로.png](captures/Query%20Method%20%EA%B0%90%EB%8F%85%20%EC%9D%B4%EB%A6%84%20%ED%8F%89%EC%A0%90%20%EC%88%9C%EC%9C%BC%EB%A1%9C.png)

### Dirty Checking
![dirty checking.png](captures/dirty%20checking.png)

### Auditing
![dirty checking and Auditing in db.png](captures/dirty%20checking%20and%20Auditing%20in%20db.png)

## 3. Weekly Review
### Key Learning: 새롭게 배운 내용 2~3가지
1. transaction과 dirty checking의 관계
`@transactional`이 붙은 메서드 전체에서 transaction이 반영되어 findById 를 통해 진행된 영속 상태가 메서드가 끝날때까지 유지 된다.

이때 id를 통해 받은 데이터를 새로 입력 받은 데이터로 수정하게 될 경우 영속 상태에서는 dirty checking이 진행되면 jpa가 데이터 수정을 추적하게 된다.

그렇기에 나중에 save를 진행하지 않아도 자동으로 update sql 문이 진행되며 데이터가 수정된다.

2. query method 작성 방법
findBy + 필드명 + 조건 을 통해 query method 작성이 가능하다.

이를 통해 특정 기능을 수행하는 sql 문을 직접 작성하지 않아도 원하는 조건으로 java 메서드 형태로 사용할 수 있다. 

이때 repository에 추가한 method를 작성할 필요가 있다.

3. Auditing
엔티티가 언제 생성 되었는지 수정된 시기를 자동으로 저장하는 기능

@CreatedDate 과 @LastModifiedDate 를 통해 관리

application class 에서 `@EnableJpaAuditing`를 선언해주어야 하낟.

### Problem & Solution: 어려웠던 점과 해결 방법

어려웠던 점 : @transactional 과 dirty checking 의 관계 및 수정이 어떻게 진행되는지 이해가 어려웠다.

해결 방법 : ta세션과 ai 통해 공부

### Reflection: 이번 과제를 통해 느낀 점
이번 과제를 하면서 이론으로 @transactional과 update, dirty checking 이 어떻게 이루어 지는지 배운 것이 실제 적용으로 어떻게 작성이 되는지를 알 수 있었다.

### ④ AI Usage
사용한 생성형 AI 도구 및 주요 질문
- 해당 코드에서 오류가 있는지
- dirty checking 이 어떻게 이루어 지며 @transactional과 어떤 관계인지

AI가 제공한 내용 중 직접 수정하거나 검증한 부분
- dirty checking 의 이해한 내용을 ta 님에게 물어보고 확인

AI 사용 비율에 대한 자기 평가
- 30%
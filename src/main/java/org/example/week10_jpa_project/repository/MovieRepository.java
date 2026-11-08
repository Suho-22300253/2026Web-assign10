package org.example.week10_jpa_project.repository;

import org.example.week10_jpa_project.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
Q3. JpaRepository<Entity, Long>에서 두 타입은 각각 무엇을 의미
어떤 entity를 다룰 것이며 해당 entity의 primary key에 대한 자료형을 의미
 */

//Movie라는데이터가 Long 타입 id로 구분된다.
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByDirector(String director);
    List<Movie> findByDirectorAndRatingGreaterThan(String director, Integer rating);
    List<Movie> findByDirectorOrderByRatingDesc(String director);
}

/*
Q. JpaRepository에서 기본 제공하는 CRUD 메서드를 5개 이상 찾아 메서드명과 역할
save(entity) - id = null -> 데이터 추가 / id != null -> 데이터 수정
findAll() - 모든 데이터를 받아 List로 반환한다.
findById(id) - 해당 id에 해당하는 데이터를 찾아서 반환
deleteById(id) - 해당 id에 해당하는 데이터를 찾아서 삭제
existsById(id) - 해당 id에 해당하는 데이터 존재 반환 - boolean으로 반환
 */

/*
findById()의 반환 타입이 Optional인 이유는 무엇인가?
조건에 해당하는 값이 없더라도 안전하게 반환하기 위함이다.
null Optional.empty()를 반환하며 orElseThrow를 통해 객체 존재시 객체 반환 없을 경우 예외를 발생시킨다.
즉 NullPointerException을 방지한다.
 */

/*
JpaRepository에서 기본 제공하는 메서드와 개발자가 직접 작성하는 Query Method의 차이
기보으로 제공하는 crud 기능 -> 개발자가 repository에 선언하지 않아도 기본적으로 crud기능 사용 가능

Query Method
개발자가 원하는 조회 조건에 맞게 직접 선언하는 메서드
findBy + 필드명 + 조건 으로 진행된다.
List<Movie> findByDirector(String director);
와 같이
List<Movie> - 반환 타입
findBy - 데이터를 조회하는 메서드임을 명시
Director - 필드 이름
조건 키워드 생략 = 기본적 = 이 적용
String director - 비교할 값
이때 실제 필드는 director이지만 메서드 작성시 Director로 작성되는데 jpa 가 알아서 첫문자를 소문자로 바꾸어 준다.
 */
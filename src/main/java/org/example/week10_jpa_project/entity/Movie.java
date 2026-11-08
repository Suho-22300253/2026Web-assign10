package org.example.week10_jpa_project.entity;

import jakarta.persistence.*;
import org.example.week10_jpa_project.dto.MovieRequest;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// Java 객체와 데이터베이스 테이블을 연결하는 역할
/*
Q2. Entity 클래스와 데이터베이스 테이블은 어떻게 연결되는가?

ORM 기술 이용 -> Java의 Entity 클래스와 데이터베이스 테이블을 연결
Mapping 진행

entity class = table
필드 = column
각 데이터 = row로 진행
 */

@Entity
@Table(name = "movies")
@EntityListeners(AuditingEntityListener.class)
public class Movie {

    @Id // 이 필드가 테이블의 기본 키(Primary Key)임을 의미
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // MariaDB의 AUTO_INCREMENT를 사용하여 ID를 자동 생성
    // @GeneratedValue만 사용해도 기본 전략인 AUTO가 적용되지만, MariaDB의 AUTO_INCREMENT와 동일한 생성 전략이 보장되는 것은 아님
    /*
     GeneratedValue에서 자동 생성과 GenerationType.IDENTITY의 autoincrement의 차이

    GeneratedValue - 기본 키를 자동으로 생성 - 여러 방식 존재
    GenerationType.IDENTITY - 어떤 방식으로 생성할건지 - 여러 방식 중에 autoincrement로 진행하겠다.
     */
    private Long id;

    @Column(nullable = false, length = 100) // null을 허용하지 않고 최대 100자까지 저장 가능
    private String title;

    @Column(nullable = false, length = 100)
    private String director;

    @Column(nullable = false, length = 50)
    private String genre;

    @Column(nullable = false)
    private Double rating; // Double을 사용하여 null 판단이 가능해짐. double일경우 없으면 null 이 아니라 0.0

    @Column(nullable = false)
    private Integer releaseYear; // Double과 마찬가지 이유

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // JPA가 Entity 객체를 생성할 때 사용하는 기본 생성자
    // JPA에서는 매개변수가 없는 생성자가 필요함
    public Movie() {
    }

    public Movie(MovieRequest mr) {
        this.title = mr.title();
        this.director = mr.director();
        this.genre = mr.genre();
        this.rating = mr.rating();
        this.releaseYear = mr.releaseYear();
    }

    public void update(MovieRequest mr) {
        this.title = mr.title();
        this.director = mr.director();
        this.genre = mr.genre();
        this.rating = mr.rating();
        this.releaseYear = mr.releaseYear();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }
}

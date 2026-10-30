package org.example.week09_jpa_project.repository;

import org.example.week09_jpa_project.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> { //Movie라는데이터가 Long 타입 id로 구분된다.
}

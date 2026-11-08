package org.example.week09_jpa_project.service;

import jakarta.transaction.Transactional;
import org.example.week09_jpa_project.dto.MovieRequest;
import org.example.week09_jpa_project.dto.MovieResponse;
import org.example.week09_jpa_project.entity.Movie;
import org.example.week09_jpa_project.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    /*
    Q7) save()를 호출하면 새로운 데이터 등록과 기존 데이터 수정이 어떻게 처리되는가
    Entity가 새로운 데이터인지 아닌지를 id 확인으로 진행함
    새로운 데이터인 경우 - persist 진행
    기존 데이터 - merge 진행
     */
    public MovieResponse create(MovieRequest request) {
        // Movie 생성자를 통해 새로운 Movie 객체 생성하여 repo의 save로 보낸고 db에 저장 후 반환
        Movie saved = repository.save(new Movie(request));
        // from 메서드를 통해 Movie 객체를 response 객체로 변환
        return MovieResponse.from(saved);
    }

    public List<MovieResponse> findAll() {
        // MariaDB에서 모든 영화를 조회하고 → 각각 Response DTO로 변환하고 → 리스트로 반환하는 코드
        /*
        1. findAll 을 통해 db로 부터 데이터를 받아온다. List<Movie> 상태로 받아옴
        2. list.stream 형식으로 리스트의 데이터를 하나씩 처리할 수 있도록 Stream으로 변환하는 메서드
        3. .map(MovieResponse::from) 은 각 Movie 객체를 MovieResponse 객체로 변환 = from 메서드를 각 movie 객체에 적용
            ::는 메서드 참조(Method Reference)라는 Java 문법
            .map(movie -> MovieResponse.from(movie)) 과 같은 의미
        4. .toList() 앞에서 변환한 모든 MovieResponse 객체를 하나의 리스트로 모아 List<MovieResponse> 가 된다.
         */
        return repository.findAll().stream().map(MovieResponse::from).toList();
    }

    public MovieResponse findById(Long id) {

        return MovieResponse.from(findEntity(id));
    }

    // Dirty Checking을 이용한 수정 기능 구현
    @Transactional
    public MovieResponse update(Long id, MovieRequest request) {
        Movie movie = findEntity(id); // 해당 정보가 있는지 확인
        movie.update(request); // 메서드 통해 request를 Movie 객체로 변환
        return MovieResponse.from(movie);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "movie not found: " + id);
        }
        repository.deleteById(id);
    }

    private Movie findEntity(Long id) {
        return repository.findById(id) // repository.findById(id)가 Optional<Movie>를 반환
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "movie not found: " + id));
    }

}

@Transactional과 Dirty Checking은 어떤 관계가 있는가?
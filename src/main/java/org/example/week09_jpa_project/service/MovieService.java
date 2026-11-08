package org.example.week09_jpa_project.service;

import jakarta.transaction.Transactional;
import org.example.week09_jpa_project.dto.MovieRequest;
import org.example.week09_jpa_project.dto.MovieResponse;
import org.example.week09_jpa_project.entity.Movie;
import org.example.week09_jpa_project.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
        // Repository를 이용하여 기존 Entity 조회
        Movie movie = findEntity(id);
        // Entity의 필드 값을 변경
        movie.update(request);
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


    public List<MovieResponse> findByDirector(String director){

        List<MovieResponse> lists = new ArrayList<>();
        for(Movie data : repository.findByDirector(director)){
            lists.add(MovieResponse.from(data));
        }
        return lists;
    }

    public List<MovieResponse> findByActorAndRatingGreaterThan(String actor, Integer rating){
        List<MovieResponse> lists = new ArrayList<>();
        for(Movie data : repository.findByActorAndRatingGreaterThan(actor, rating)){
            lists.add(MovieResponse.from(data));
        }
        return lists;
    }

    public List<MovieResponse> findByDirectorOrderByRatingDesc(String director){
        List<MovieResponse> lists = new ArrayList<>();
        for(Movie data : repository.findByDirectorOrderByRatingDesc(director)){
            lists.add(MovieResponse.from(data));
        }
        return lists;
    }

}

/* Q8 @Transactional과 Dirty Checking은 어떤 관계가 있는가?
dirty checking 으로 db가 변경되기 위해서 entity가 영속 상태여야 한다.
여기서 영속 상태란 JPA의 영속성 컨텍스트가 Entity 객체를 관리하고 있는 상태를 의미한다. 즉 JPA가 해당 객체를 기억하고 있어서 변경 사항을 추적할 수 있는 상태를 의미한다.
보통 findById 인 경우 영속 상태가 되지만 findById가 종료되면 영속 상태도 종료된다. 그렇기에 수정 시 save를 통해 수정사항을 직접 넣어줘야 한다.

@Transactional 을 붙일 경우 메서드 단위로 Transactional이 되는데 이때 findById 에서 진행된 영속 상태가 메서드가 끝날 때까지 유지된다.
더 자세히는 트랜잭션의 범위를 메서드 전체로 확장하여 영속성 컨텍스트가 중간에 종료되지 않도록 하기 때문이다.
그렇기에 movie.update(request); 해당 상황에서도 영속 상태이기 때문에 jpa 가 변화를 감지하고 메서드가 끝날때 이를 UPDATE SQL을 실행하여 DB에 반영하여 save 없이 수정된다.
 */
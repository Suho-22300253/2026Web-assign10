package org.example.week10_jpa_project.controller;

import jakarta.validation.Valid;
import org.example.week10_jpa_project.dto.MovieRequest;
import org.example.week10_jpa_project.dto.MovieResponse;
import org.example.week10_jpa_project.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
    private MovieService ms;

    public MovieController(MovieService ms) {
        this.ms = ms;
    }

    @PostMapping //
    /*
    ResponseEntity = 서버가 클라이언트에게 보낼 HTTP 응답을 직접 구성할 수 있는 클래스
    구성 : status code(처리 결과) +
          header(응답에 대한 부가정보 ex - Location: /api/movies/5 Content-Type: application/json) +
          body (실제 반환할 데이터 - json type)
    @ResponseStatus(HttpStatus.CREATED) 도 가능한데 ResponseEntity 를 통해 Location header까지 저장
    @Valid: MovieRequest에 작성한 @NotBlank, @NotNull 등의 검증 조건 실행
     */
    public ResponseEntity<MovieResponse> create(@Valid @RequestBody MovieRequest mr) {
        MovieResponse created = ms.create(mr);
        /*
        created = response 객체 -> creates.id() = 해당 id
        URI.create() = 문자열을 URI 객체로 변환
        만약 id = 1 :"/api/movie" + created.id()  -> /api/movie/1 : 서버가 새로 생성된 영화의 위치를 클라이언트에게 알려주는 주소
        URI.create()는 문자열을 URI(주소) 객체로 변환하는 Java 메서드  but : 자체는 HTTP 요청을 보내거나 영화를 조회하지 않는다
        ResponseEntity.created(...) -> Spring에서 제공하는 정적 메서드로, 다음 두 가지를 설정할 수 있는 응답 빌더를 반환
        HTTP 상태 코드: 201 Created + HTTP Location 헤더: /api/movies/5
        .body(created) : HTTP 응답의 Body에 created(현 코드에서는 response 객체) 객체를 넣는 것
         */
        return ResponseEntity.created(URI.create("/api/movie" + created.id())).body(created);
    }

    @GetMapping
    public List<MovieResponse> findAll() {
        return ms.findAll();
    }

    @GetMapping("/{id}")
    public MovieResponse findById(@PathVariable Long id) {
        return ms.findById(id);
    }

    @PutMapping("/{id}")
    public MovieResponse update(@PathVariable Long id, @Valid @RequestBody MovieRequest request) {
        return ms.update(id, request);
    }

    /*
    <Void> : 응답 Body에 담을 데이터가 없음을 나타내는 타입 (void와는 다름)
    ResponseEntity.noContent() : 204 No Content를 설정하는 응답 빌더를 반환 위에서는 created
    .build() = 앞에서 설정한 상태 코드를 바탕으로 최종 ResponseEntity 객체를 생성 : 별도의 응답 본문 없이 응답 객체 생성
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ms.delete(id);
        return ResponseEntity.noContent().build();
    }
    /*
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        ms.delete(id);
    } 같은 내용
     */
}

package org.example.week09_jpa_project.dto;

import org.example.week09_jpa_project.entity.Movie;

public record MovieResponse(Long id, String title, String director, String genre, Double rating, int releaseYear) {

    // 반복되는 작업을 없애기 위함 - 그 전에는 매번 이렇게 해줬는데 이를 통해 from을 통해 한번에 진행
    public static MovieResponse from(Movie movie) {
        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getGenre(),
                movie.getRating(),
                movie.getReleaseYear()
        );
    }
}

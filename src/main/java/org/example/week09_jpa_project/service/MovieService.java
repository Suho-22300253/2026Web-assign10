package org.example.week09_jpa_project.service;

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

    // 생성자를 이용해 Repository 객체를 주입받음 (DI)
    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

}

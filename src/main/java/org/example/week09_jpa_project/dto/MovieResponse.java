package org.example.week09_jpa_project.dto;

public record MovieResponse(Long id, String title, String director, String genre, Double rating, int releaseYear) {
}

package org.example.week09_jpa_project.dto;

public record MovieRequest(String title, String director, String genre, Double rating, int releaseYear) {
}

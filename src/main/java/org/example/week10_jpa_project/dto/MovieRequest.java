package org.example.week10_jpa_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record MovieRequest(
        // HTTP 요청에서 검증을 실행하려면 Validation 의존성과 Controller의 @Valid가 필요
        // 전 프로젝트에서는check()를 통해 검증했다면 이번엔 spring boot를 통해 검증한다.
        @NotBlank @Size(max = 100) String title,
        @NotBlank @Size(max = 100) String director,
        @NotBlank @Size(max = 50) String genre,
        @NotNull @PositiveOrZero Double rating, // 음수 불가
        @NotNull @PositiveOrZero int releaseYear) {
}

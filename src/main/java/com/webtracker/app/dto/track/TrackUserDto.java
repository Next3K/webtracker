package com.webtracker.app.dto.track;

import com.webtracker.app.model.states.github.CodingLanguage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackUserDto {

    @NotBlank
    private String githubUsername;

    @NotNull
    private Set<CodingLanguage> technologies;

    @NotBlank
    private String email;

    @NotNull
    private TrackType trackType;

}

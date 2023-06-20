package com.webtracker.app.dto;

import com.webtracker.app.model.observers.ObserverType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserDto {

    @NotBlank
    private String githubUsername;

    @NotNull
    private String observerType;

}

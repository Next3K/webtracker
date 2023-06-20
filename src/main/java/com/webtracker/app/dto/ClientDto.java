package com.webtracker.app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    @NotNull
    String name;

    @NotNull
    String email;

}

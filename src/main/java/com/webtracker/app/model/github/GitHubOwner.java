package com.webtracker.app.model.github;

import com.webtracker.app.common.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "github_owner")
@Getter
@Setter
@Entity
public class GitHubOwner extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "github_owner_id")
    private Long id;

    private String name;

    private String surname;

    @NotBlank(message = "Username cannot be blank")
    private String username;

    private String mail;
}

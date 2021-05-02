package com.example.socialmedia.model;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userId;
    @NotBlank(message="user name is required")
    private String username;
    @NotBlank(message="password is required")
    private String Password;
    @Email
    @NotEmpty(message="Email is required")
    private String email;
    private Instant created;
    private boolean enabled;

}

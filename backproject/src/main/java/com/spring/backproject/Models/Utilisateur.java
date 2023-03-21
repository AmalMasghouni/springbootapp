package com.spring.backproject.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    @Column(unique = true)
    @Email(message = "L'adresse e-mail doit être valide.")
    @Pattern(regexp = "^[a-z0-9._%+-]+@actia-engineering\\.com$", message = "L'adresse e-mail doit être sous la forme @actia-engineering.com.")
    private String email;
    private String password;
}

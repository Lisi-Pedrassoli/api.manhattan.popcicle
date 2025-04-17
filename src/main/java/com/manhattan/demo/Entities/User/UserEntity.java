package com.manhattan.demo.Entities.User;

import com.manhattan.demo.Enums.User.UserRoles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // gera o ID automaticamente
    private String id;

    @Column(nullable = false)
    @Length(min = 3, max = 50)
    private String nome;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    @Size(min = 6)
    private String senha;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoles cargo;

    @Column(nullable = false)
    private boolean ativo;

    public UserEntity(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ativo = true;
        this.cargo = UserRoles.ADMIN;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> permissions = switch (this.cargo) {
            case ADMIN -> List.of(
                    "ROLE_ADMIN"
            );
        };

        return permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.ativo;
    }
}
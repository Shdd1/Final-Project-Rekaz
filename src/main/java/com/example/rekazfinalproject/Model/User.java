package com.example.rekazfinalproject.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Check;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class User implements UserDetails  {

    //*** All Done by Danah ****

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Username is required")
    @Column(columnDefinition = "varchar(15) not null unique")
    private String username;

    @NotEmpty(message = "Password must not be empty")
    @Size(min = 8, message = "Password must be more than 8 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$", message = "Password must contain letter and number")

    @Column(columnDefinition = "varchar (100) not null")
    @Check(constraints = "LENGTH(password) >= 8")
    @Check(constraints = "password ~ '^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$'")
    private String password;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")

    @Column(columnDefinition = "varchar(40) not null unique ")
    @Check(constraints = "email LIKE '%@%'")
    private String email;

    @NotEmpty(message = "Role is required")
    @Pattern(regexp = "OWNER|INVESTOR|ADMIN", message = "role must be OWNER|INVESTOR|ADMIN")
    @Column(columnDefinition = "varchar(12)")
    @Check(constraints = "role IN ('OWNER','INVESTOR','ADMIN')")
    private String role;

//    @AssertFalse
    private boolean isActive;


    @OneToOne(cascade = CascadeType.ALL , mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Owner owner;

    @OneToOne(cascade = CascadeType.ALL , mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Investor investor;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private Set<Question> questions;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));

    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }




}


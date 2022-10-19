package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "users")
public class User extends BaseEntity implements Serializable {


    @Column(name = "username" , unique = true)
    private String username;


    @Column(name = "password")
    private String password;


    @Enumerated(EnumType.STRING)
    private Role role;


    @Column(name = "email" , insertable = true , updatable = false)
    private String email;


    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Book> books = new ArrayList<>();



    public User(Long id, String username, String password) {
        super(id , null , null);
        this.username = username;
        this.password = password;

    }
}

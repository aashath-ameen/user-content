package com.example.usercontent.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "'user'")  // Escape the table name
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @OneToMany(mappedBy = "'user'")
    private Set<Review> reviews;

    @OneToMany(mappedBy = "'user'")
    private Set<Photo> photos;

}

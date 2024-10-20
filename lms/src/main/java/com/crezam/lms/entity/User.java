package com.crezam.lms.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "phoneNo")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;
    @Column(name = "username")
    private String userName;
    @Column(name = "email")
    private String email;
    @Column(name = "phoneNo", nullable = false)
    private String contactNumber;
    @Column(nullable = false)
    private String password;

    private boolean accountNonLocked = true;
    private boolean accountNonExpired = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    private LocalDate credentialsExpiryDate;
    private LocalDate accountExpiryDate;
    private String signUpMethod;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @JsonManagedReference
    @ToString.Exclude
    private Role role;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public User(String userName, String email, String password, String contactNumber) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

}


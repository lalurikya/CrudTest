package com.crud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name="user_detail")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="created_by")
    private Long createdBy;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_by")
    private Long updatedBy;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="deleted_by")
    private Long deletedBy;

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;
}

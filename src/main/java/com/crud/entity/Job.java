package com.crud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name="jobs")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name="name")
    private String name;

    @Column(name="start_at")
    private String startAt;

    @Column(name="end_at")
    private String endAt;

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

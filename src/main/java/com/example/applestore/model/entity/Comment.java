package com.example.applestore.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity{

    @ManyToOne
    private User user;

    @Column(nullable = false, name = "commented_at")
    private LocalDateTime commentedAt;

    @Column(columnDefinition = "TEXT")
    private String comment;
}

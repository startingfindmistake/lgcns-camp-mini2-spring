package com.mini.mini_2.user.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.mini.mini_2.favorite.domain.entity.FavoriteEntity;
import com.mini.mini_2.review.domain.entity.ReviewEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false, 
            length = 50)
    private String password;
    @Column(nullable = false, 
            length = 50, 
            unique = true)
    private String userEmail;
    @Column(nullable = false, 
            length = 50,
            unique = true)
    private String userNickname;
    
    @CreationTimestamp
    @Column(name = "created_at", 
            nullable = false, 
            updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(orphanRemoval = true,
               cascade = CascadeType.ALL,
               mappedBy = "user")
    private List<FavoriteEntity> favorites = new ArrayList<>();

    @OneToMany(orphanRemoval = false,
               cascade = CascadeType.ALL,
               mappedBy = "user")
    private List<ReviewEntity> reviews = new ArrayList<>();


}

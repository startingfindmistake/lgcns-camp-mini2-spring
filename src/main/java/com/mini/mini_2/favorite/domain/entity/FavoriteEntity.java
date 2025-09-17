package com.mini.mini_2.favorite.domain.entity;

import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.user.domain.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "favorite")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"restArea"})
public class FavoriteEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer favoriteId;
    
    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY,
               optional = false)
    @JoinColumn(name = "user_id")       // 테이블 컬럼 이름
    private UserEntity user;            // UserEntity 의 mappedBy

    @ManyToOne(fetch = FetchType.LAZY,
               optional = false)
    @JoinColumn(name = "rest_area_id")       // 테이블 컬럼 이름
    private RestAreaEntity restArea;         // UserEntity 의 mappedBy
}

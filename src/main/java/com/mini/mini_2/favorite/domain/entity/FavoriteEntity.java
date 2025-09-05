package com.mini.mini_2.favorite.domain.entity;

import com.mini.mini_2.user.domain.entity.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FavoriteEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int favorite_id;

    @ManyToOne(fetch = FetchType.LAZY,
               optional = false)
    @JoinColumn(name = "user_id")       // 테이블 컬럼 이름
    private UserEntity user;            // UserEntity 의 mappedBy

    // @ManyToOne(fetch = FetchType.LAZY,
    //            optional = false)
    // @JoinColumn(name = "user_id")       // 테이블 컬럼 이름
    // private RestareaEbtuty user;            // UserEntity 의 mappedBy
}

package com.mini.mini_2.favorite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mini.mini_2.favorite.domain.entity.FavoriteEntity;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Integer> {
    List<FavoriteEntity> findAllByUser_UserId(Integer userId);
}

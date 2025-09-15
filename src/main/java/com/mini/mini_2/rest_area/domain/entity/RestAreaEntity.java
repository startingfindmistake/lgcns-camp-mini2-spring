package com.mini.mini_2.rest_area.domain.entity;


import java.util.ArrayList;
import java.util.List;

import com.mini.mini_2.facility.domain.entity.FacilityEntity;
import com.mini.mini_2.favorite.domain.entity.FavoriteEntity;
import com.mini.mini_2.food.domain.entity.FoodEntity;
import com.mini.mini_2.review.domain.entity.ReviewEntity;

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
@Table(name = "rest_area")

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class RestAreaEntity {
    
    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer restAreaId;


    @Column(nullable = false , 
            unique = true,
            length = 50)
    private String name ;
    
    @Column(nullable = false , 
            length = 50)
    private String direction ;

    @Column(nullable = false,
            unique = true,
            length = 100)
    private String code ;

    @Column(length = 50)
    private String tel ;
    
    @Column(length = 100)
    private String address ;

    @Column(length = 100)
    private String routeName ;

    @Column(length = 100)
    private String xValue ;

    @Column(length = 100)
    private String yValue ;



    @OneToMany(mappedBy = "restArea", 
               orphanRemoval = false) 
    private List<ReviewEntity> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "restArea", 
               orphanRemoval = false) 
    private List<FavoriteEntity> favorites = new ArrayList<>();

    @OneToMany(mappedBy = "restArea", 
               orphanRemoval = false) 
    private List<FacilityEntity> facilities = new ArrayList<>();

    @OneToMany(mappedBy = "restArea", 
               orphanRemoval = false) 
    private List<FoodEntity> foods = new ArrayList<>();

    

    

}



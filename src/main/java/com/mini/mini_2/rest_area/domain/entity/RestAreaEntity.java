package com.mini.mini_2.rest_area.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private Integer rest_area_id;


    @Column(nullable = false , length = 50)
    private String name ;
    
    @Column(length = 50)
    private String comment ;

    @Column(length = 50)
    private String direction ;

    // 연동 예정 - 일대다
    /*
    @OneToMany(mappedBy = "rest", orphanRemoval = false) 
    private List<ReviewEntity> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "rest", orphanRemoval = false) 
    private List<FavoriteEntity> favorites = new ArrayList<>();

    @OneToMany(mappedBy = "rest", orphanRemoval = false) 
    private List<FoodEntity> foods = new ArrayList<>();

    @OneToMany(mappedBy = "rest", orphanRemoval = false) 
    private List<FacilityEntity> facilities = new ArrayList<>();
    */

    

    

}

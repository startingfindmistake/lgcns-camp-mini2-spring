package com.mini.mini_2.facility.domain.entity;

import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;

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
@Table(name = "facility")

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class FacilityEntity {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_id")
    private Integer facilityId;

    // FK(rest_area_id)
    @ManyToOne(fetch = FetchType.LAZY,
               optional = false)
    @JoinColumn(name = "rest_area_id")      
    private RestAreaEntity restArea;          

    
    @Column(name = "type", length = 50)
    private String type; 
    
}

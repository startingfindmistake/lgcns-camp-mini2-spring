package com.mini.mini_2.openapi.restarea.dto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class RestareaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int restarea_id;
}

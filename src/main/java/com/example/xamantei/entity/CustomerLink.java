package com.example.xamantei.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customerlink")
@NoArgsConstructor @AllArgsConstructor @Setter @Getter
public class CustomerLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;
    @Column()
    private String platform;
    @Column()
    private int orderValue;
    @Column()
    private String url;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Appuser appUser;
}

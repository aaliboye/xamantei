package com.example.xamantei.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "appUser")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appuser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column()
    private String fullName;
    @Column()
    private String password;
    @Column()
    private String email;
    @Column()
    private String bio;
    
    @Column(unique = true)
    private String telephone;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> roles=new ArrayList<>();

    @OneToMany(mappedBy = "appUser", fetch = FetchType.LAZY)
    // @JsonIgnore
    private List<SocialLink> socialLinks = new ArrayList<>();

    @OneToMany(mappedBy = "appUser", fetch = FetchType.LAZY)
    // @JsonIgnore
    private List<CustomerLink> custoLinks = new ArrayList<>();
}

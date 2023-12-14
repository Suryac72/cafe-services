package com.cafe.java.cafebackend.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user_profile_roles")
@Table(name="user_profile_roles")
@Getter()
@Setter()
@AllArgsConstructor()
@NoArgsConstructor()
@Data
public class Role {
    // @PrePersist
    // protected void onCreate() {
    //     this.createdAt = new Date(System.currentTimeMillis());
    // }

    // @PreUpdate
    // protected void onUpdate() {
    //     this.updatedAt = new Date(System.currentTimeMillis());
    // }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "role_id")
    private UUID roleId;
    @Column(name = "role_name")
    private String name;
    @Column(name = "role_description")
    private String description;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;

    // @ManyToMany(mappedBy = "roles")
    // @Fetch(value = FetchMode.SELECT)
    // @JsonIgnore
    // private Set<UserProfile> users  = new HashSet<>();

    public Role(UUID roleId, String name, String description) {
        this.roleId = roleId;
        this.name = name;
        this.description = description;
    }

    public Role(String name) {
        this.name = name;
    }
}
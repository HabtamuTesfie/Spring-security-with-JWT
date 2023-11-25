package com.test.productmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
    })
public class User implements Serializable {
  private static final long serialVersionUID = -2550185165626007488L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;
  private String email;
  private String fullName;
  private LocalDateTime createdDate;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

}

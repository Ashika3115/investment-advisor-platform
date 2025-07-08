package com.investment.advisor.entity;

import jakarta.persistence.*;
        import lombok.*;

@Entity
@Table(name = "admin_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(nullable = false, unique = true)
    private String adminUserName;

    @Column(nullable = false)
    private String adminEmail;

    @Column(nullable = false)
    private String adminRole; // Must be ADMIN

    private String approvedBy;  // Could be Admin name
}

package com.dtt.organization.model;

import jakarta.persistence.*;

@Entity
@Table(name = "spp_di_password_reset_token")
public class PasswordResetTokenEntity {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        private String token;

    @Column(name = "email")
    private String email;

    @Column(name = "expiry_time")
    private String expiryTime;

    @Column(name = "used")
    private Boolean used = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PasswordResetTokenEntity{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", email='" + email + '\'' +
                ", expiryTime='" + expiryTime + '\'' +
                ", used=" + used +
                '}';
    }
}

package com.dtt.organization.model;

import jakarta.persistence.*;

@Entity
@Table(name = "spp_di_meta_software_names")
public class MetaSoftwareEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "display_name")
        private String displayName ;

    @Column(name = "value")
        private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MetaSoftwareEntity{" +
                "id=" + id +
                ", displayName='" + displayName + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

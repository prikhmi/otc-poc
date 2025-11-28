package com.priya.otc.orchestrator.domain;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "country_risk")
public class CountryRisk {

    @Id
    private String countryCode;

    private boolean sanctioned;
    private boolean highRisk;

    // getters & setters


}

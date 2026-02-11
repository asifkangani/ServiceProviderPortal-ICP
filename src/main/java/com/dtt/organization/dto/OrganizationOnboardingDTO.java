package com.dtt.organization.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public class OrganizationOnboardingDTO {

    @NotNull(message = "Organisation details are mandatory")
    @Valid
    private OrganizationDetailsDTO organization;

    @NotNull(message = "SPOC details are mandatory")
    @Valid
    private SpocDetailsDTO spocDetails;

    @Valid
    private AuditorDetailsDTO auditorDetails;

    public OrganizationDetailsDTO getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDetailsDTO organization) {
        this.organization = organization;
    }

    public SpocDetailsDTO getSpocDetails() {
        return spocDetails;
    }

    public void setSpocDetails(SpocDetailsDTO spocDetails) {
        this.spocDetails = spocDetails;
    }

    public AuditorDetailsDTO getAuditorDetails() {
        return auditorDetails;
    }

    public void setAuditorDetails(AuditorDetailsDTO auditorDetails) {
        this.auditorDetails = auditorDetails;
    }

    @Override
    public String toString() {
        return "OrganizationOnboardingDTO{" +
                "organization=" + organization +
                ", spocDetails=" + spocDetails +
                ", auditorDetails=" + auditorDetails +
                '}';
    }
}

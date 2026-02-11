package com.dtt.organization.dto;



public class DashboardResponseDto {

    private long orgs;
    private long totalApplications;
    private long pendingApplications;
    private long totalOrganizations;

    public long getOrgs() {
        return orgs;
    }

    public void setOrgs(long orgs) {
        this.orgs = orgs;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public long getPendingApplications() {
        return pendingApplications;
    }

    public void setPendingApplications(long pendingApplications) {
        this.pendingApplications = pendingApplications;
    }

    public long getTotalOrganizations() {
        return totalOrganizations;
    }

    public void setTotalOrganizations(long totalOrganizations) {
        this.totalOrganizations = totalOrganizations;
    }

    @Override
    public String toString() {
        return "DashboardResponseDto{" +
                "orgs=" + orgs +
                ", totalApplications=" + totalApplications +
                ", pendingApplications=" + pendingApplications +
                ", totalOrganizations=" + totalOrganizations +
                '}';
    }
}
package com.dtt.organization.dto;

public class GetOrgIDByOuidResponseDto {

    private String orgName;
    private String ouid;
    private int categoryId;
    private String categoryName;
    private String categoryDisplayName;

    public String getCategoryDisplayName() {
        return categoryDisplayName;
    }

    public void setCategoryDisplayName(String categoryDisplayName) {
        this.categoryDisplayName = categoryDisplayName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOuid() {
        return ouid;
    }

    public void setOuid(String ouid) {
        this.ouid = ouid;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "GetOrgIDByOuidResponseDto{" +
                "orgName='" + orgName + '\'' +
                ", ouid='" + ouid + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", categoryDisplayName='" + categoryDisplayName + '\'' +
                '}';
    }
}

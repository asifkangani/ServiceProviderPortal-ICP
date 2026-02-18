package com.dtt.organization.service.iface;


import com.dtt.organization.util.ApiResponse;

public interface OrganisationFormsFieldsConfigureIface {


    ApiResponse getAllCategories();
    ApiResponse updateCategoryLabel(Integer id, String labelName);
}
package com.dtt.organization.service.impl;

import com.dtt.organization.model.OrganisationCategories;
import com.dtt.organization.repository.OrganisationCategoryRepo;
import com.dtt.organization.service.iface.OrganisationFormsFieldsConfigureIface;
import com.dtt.organization.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganisationFormsFieldsConfigureImpl
        implements OrganisationFormsFieldsConfigureIface {
    private static final String CLASS = "OrganisationFormsFieldsConfigureImpl";
    private static final Logger logger = LoggerFactory.getLogger(OrganisationFormsFieldsConfigureImpl.class);


    @Autowired
    private OrganisationCategoryRepo organisationCategoryRepo;

    @Override
    public ApiResponse getAllCategories() {
        logger.info("{} getAllCategories() request received", CLASS);
        try {

            List<OrganisationCategories> categories =
                    organisationCategoryRepo.findAll();
            logger.info("{} getAllCategories() successful | totalCategories={}", CLASS,
                    categories != null ? categories.size() : 0);
            return new ApiResponse(
                    true,
                    "All Categories fetched successfully",
                    categories
            );

        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(false, "Something went wrong", null);
        }
    }
}

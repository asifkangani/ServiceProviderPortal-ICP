package com.dtt.organization.service.impl;

import com.dtt.organization.model.OrganisationCategories;
import com.dtt.organization.repository.OrganisationCategoryRepo;
import com.dtt.organization.service.iface.OrganisationFormsFieldsConfigureIface;
import com.dtt.organization.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
            logger.error("Unexpected exception", e);
            return new ApiResponse(false, "Something went wrong", null);
        }
    }

    @Override
    public ApiResponse updateCategoryLabel(Integer id, String labelName) {
        logger.info("{} updateCategoryLabel() | id={}", CLASS, id);
        try {

            if (id == null || id <= 0) {
                return new ApiResponse(false, "Category id cannot be null or invalid", null);
            }

            if (labelName == null || labelName.trim().isEmpty()) {
                return new ApiResponse(false, "Label name cannot be null or empty", null);
            }

            String updatedOn = LocalDateTime.now().toString();

            int updatedRows = organisationCategoryRepo
                    .updateLabelNameById(id, labelName.trim(), updatedOn);

            if (updatedRows == 0) {
                return new ApiResponse(false, "Category not found", null);
            }

            return new ApiResponse(true, "Label name updated successfully", null);

        }
        catch (org.springframework.dao.DataIntegrityViolationException ex) {
            return new ApiResponse(false, "Label name already exists", null);
        }
        catch (Exception e) {
            logger.error("Error updating category label", e);
            return new ApiResponse(false, "Something went wrong", null);
        }
    }
}

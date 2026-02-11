package com.dtt.organization.service.iface;


import com.dtt.organization.dto.OrganizationOnboardingDTO;
import com.dtt.organization.dto.SpocOrganizationResponseDTO;
import com.dtt.organization.util.ApiResponse;
import org.springframework.core.io.Resource;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface OrganizationService {


    ApiResponse save(OrganizationOnboardingDTO dto, Map<String, List<MultipartFile>> documents);


    ApiResponse approveOrRejectOrg(String status,Long id);

    ApiResponse getAllOrganizations();




    ApiResponse<Page<SpocOrganizationResponseDTO>> getOrganizationsBySpocEmail(String email, int page, int size);
    ApiResponse getOrganizationDetailsById(Long id);

    ApiResponse getDashboardDetails(String spocEmail);


    ApiResponse getAllOrganizationApprovalDetails();

    ApiResponse getOrgCategoryandidByOrgid(String orgId);

    ApiResponse getRecentOrganizationBySpocEmail(String spocEmail);


    ResponseEntity<Resource> downloadDocument(Long orgDetailsId, Long documentId, String documentType);
}

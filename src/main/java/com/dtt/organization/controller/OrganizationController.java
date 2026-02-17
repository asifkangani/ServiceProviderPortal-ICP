package com.dtt.organization.controller;


import com.dtt.organization.dto.OrganizationOnboardingDTO;
import com.dtt.organization.model.MetaDocumentEntity;
import com.dtt.organization.repository.MetaDocumentRepository;
import com.dtt.organization.service.iface.OrganizationService;
import com.dtt.organization.service.iface.SoftwareService;
import com.dtt.organization.service.iface.TrustedUserService;
import com.dtt.organization.util.ApiResponse;

import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrganizationController {

    private static final String CLASS = "OrganizationController";
    private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    @Value("${portal.url}")
    private String portalUrl;

    @Autowired
    private Validator validator;

    @Autowired
    TrustedUserService trustedUserService;

    private final OrganizationService organizationService;
    private final SoftwareService softwareService;
    private final MetaDocumentRepository metaDocumentRepository;
    public OrganizationController(OrganizationService establishmentService, SoftwareService softwareService, MetaDocumentRepository metaDocumentRepository) {
        this.organizationService = establishmentService;
        this.softwareService = softwareService;
        this.metaDocumentRepository =  metaDocumentRepository;
    }

@PostMapping(
        value = "/save",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
)
public ApiResponse save(
        @Valid @RequestPart("data") OrganizationOnboardingDTO dto,
        MultipartHttpServletRequest request
) {
    return organizationService.save(dto, request.getMultiFileMap());
}

    @GetMapping("/meta-documents")
    public ApiResponse getAllMetaDocuments() {
        try {
            List<MetaDocumentEntity> list = metaDocumentRepository.findAll();

            return new ApiResponse(true, "Fetched successfully", list);

        } catch (Exception e) {
            logger.error("Unexpected exception", e);
            return new ApiResponse(false, "Failed to fetch meta documents", e.getMessage());
        }
    }

    @GetMapping("/recent/by-spoc")
    public ApiResponse getRecentOrgBySpoc(
            @RequestParam String email) {
        return organizationService.getRecentOrganizationBySpocEmail(email);
    }

//    @PostMapping("/change-password")
//    public ApiResponse handleChangePassword(@RequestBody ChangePasswordDTO changePasswordDTO,
//                                            Principal principal,
//                                            HttpServletRequest request) {
//
//
//        ApiResponse response = trustedUserService.changePassword(principal.getName(), changePasswordDTO.getCurrentPassword(), changePasswordDTO.getNewPassword());
//
//        if (response.isSuccess()) {
//
//            SecurityContextHolder.clearContext();
//            request.getSession().invalidate();
//
//            return response;
//        }
//
//        return response;
//    }



}
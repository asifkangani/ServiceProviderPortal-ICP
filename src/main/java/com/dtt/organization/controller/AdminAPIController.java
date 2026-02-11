package com.dtt.organization.controller;


import com.dtt.organization.dto.UploadSofwareDTO;
import com.dtt.organization.service.iface.OrganizationService;
import com.dtt.organization.service.iface.SoftwareService;
import com.dtt.organization.service.iface.WalletIface;
import com.dtt.organization.util.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/public")
public class AdminAPIController {

    private static final String CLASS = "AdminController";
    private static final Logger logger = LoggerFactory.getLogger(AdminAPIController.class);

    @Autowired
    SoftwareService softwareService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    WalletIface walletIface;


    @GetMapping("/get/all-organizations")
    public ApiResponse getAll() {
        logger.info("{} getAll Orgs",CLASS);
        return organizationService.getAllOrganizations();
    }


    @GetMapping("/get/organisation/by/id/{id}")
    public ApiResponse getById(@PathVariable Long id) {

        logger.info("{} get Org by id  :: {}",CLASS,id);
        return organizationService.getOrganizationDetailsById(id);
    }


    @PostMapping("/change/org/status/by/id/{status}/{id}")
    public ApiResponse approveEstablishment(@PathVariable String status,@PathVariable Long id) {
        logger.info("{} change status of Organisation by id  :: {}",CLASS,id);
        return organizationService.approveOrRejectOrg(status,id);
    }



    @PostMapping("/post/upload/software/by/admin")
    public ApiResponse uploadSoftwareToDownload(@RequestParam(value="zipFile") MultipartFile zipFile,

                                                @RequestParam(value = "model") String model)  throws JsonProcessingException {

        logger.info("{} upload software of by admin",CLASS);
        ObjectMapper mapper = new ObjectMapper();
        UploadSofwareDTO uploadOrDownloadSoftwareDto = mapper.readValue(model, UploadSofwareDTO.class);
        return softwareService.uploadSoftware(uploadOrDownloadSoftwareDto,zipFile);

    }

    @GetMapping("/get/all-software-list")
    public ApiResponse getAllSoftwareList()
    {
        logger.info("{} get All software list",CLASS);
        return softwareService.getAllSoftwares();
    }

    @GetMapping("/change/software/status")
    public ApiResponse changeStatusOfSoftware(@RequestParam Long softwareId,@RequestParam String status){
        logger.info("{} change software status by id {}",CLASS,softwareId);

        return softwareService.publishOrUnpublishSoftware(softwareId,status);
    }


    @GetMapping("/get/software/names")

    public ApiResponse getSoftwareNames(){
        logger.info("{} get all software names",CLASS);
       return softwareService.getSoftwareNameWithValues();
    }


    @GetMapping("/get/all/wallet/cert/reqs")
    public ApiResponse getAllWalletCertReqs(){
        logger.info("{} get all wallet cert req",CLASS);
        return walletIface.getAllWalletReqs();
    }

    @GetMapping("/get/change/wallet/cert/req/status/by/id/{id}/{status}")
    public ApiResponse changeWalletCertReq(@PathVariable Long id,@PathVariable String status){
        logger.info("{} change wallet cert req",CLASS);
        return walletIface.changeStatusOfWalletCert(id,status);
    }


    @GetMapping("/get/orgdetails")
    public ApiResponse getAllOrgApprovalDetails() {
        return organizationService.getAllOrganizationApprovalDetails();
    }


    @GetMapping("/get/org-category/category-id/{orgid}")
    public ApiResponse getOrgCategoryandidByOrgid(@PathVariable("orgid") String orgId){
        logger.info("{} getOrgCategoryandidByOrgid {} orgId",CLASS, orgId  );
        return organizationService.getOrgCategoryandidByOrgid(orgId);

    }


    @GetMapping("/download/document/by/id/{orgDetailsId}/{documentName}/{documentType}")
    public ResponseEntity<Resource> downloadDocument(
            @PathVariable Long orgDetailsId,
            @PathVariable Long documentName,
            @PathVariable String documentType) {

        return organizationService.downloadDocument(
                orgDetailsId, documentName, documentType);
    }




}

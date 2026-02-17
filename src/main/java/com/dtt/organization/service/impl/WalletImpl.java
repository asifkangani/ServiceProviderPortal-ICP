package com.dtt.organization.service.impl;

import com.dtt.organization.dto.WalletCertResponseDto;
import com.dtt.organization.dto.WalletIssueDTO;
import com.dtt.organization.model.OrganizationEntity;
import com.dtt.organization.model.SpocEntity;
import com.dtt.organization.model.WalletCertApprovalEntity;
import com.dtt.organization.repository.OrganizationRepository;
import com.dtt.organization.repository.SpocRepository;
import com.dtt.organization.repository.WalletCertRequestsRepo;
import com.dtt.organization.service.iface.WalletIface;
import com.dtt.organization.util.APIRequestHandler;
import com.dtt.organization.util.ApiResponse;
import com.dtt.organization.util.AppUtil;
import com.dtt.organization.util.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Service
public class WalletImpl implements WalletIface {

    private static final String CLASS = "WalletImpl";
    private static final Logger logger = LoggerFactory.getLogger(WalletImpl.class);


    @Autowired
    APIRequestHandler apiRequestHandler;

    @Value("${url.org.wallet}")
    String walletUrl;

    @Value("${wallet.cert.payment}")
    boolean walletCertPayment;

    @Value("${url.find.duplicate.ref.no}")
    String walletDuplicatePayment;

    @Value("${wallet.cert.Admin.approval}")
    private boolean walletCertAdminApproval;

    @Value("${url.get.wallet.by.ouid}")
    private String walletGetByOuidUrl;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    SpocRepository spocRepository;

    @Autowired
    WalletCertRequestsRepo walletCertRequestsRepo;


    @Override
    public ApiResponse generateWalletCert(Long id,String paymentReferenceId) {
        try{
            OrganizationEntity organizationEntity = organizationRepository.findById(id).orElseThrow(() ->
                    new ValidationException("Organization not found"));

            ApiResponse response = callOrgWalletCert(organizationEntity.getOuid(),paymentReferenceId);
            if(!response.isSuccess()){
                return response;
            }
            return response;

        }catch (Exception e){
            logger.error("Unexpected exception", e);
            return new ApiResponse(false,"Something went wrong",null);
        }
    }


    @Override
    public ApiResponse saveWalletCertRequest(Long id, String paymentReferenceId) {
        try {
            if(!walletCertPayment){
                return saveWalletCertReqIntoTable(id,paymentReferenceId);
            }
            else{
                OrganizationEntity organizationEntity = organizationRepository.findById(id).orElseThrow(() ->
                        new ValidationException("Organization not found"));


                WalletIssueDTO walletIssueDTO = new WalletIssueDTO();
                walletIssueDTO.setOrganizationUid(organizationEntity.getOuid());
                walletIssueDTO.setTransactionReferenceId(paymentReferenceId);

                HttpHeaders headers = new HttpHeaders();

                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<Object> reqEntity = new HttpEntity<>(walletIssueDTO,headers);

                String url = walletDuplicatePayment;

                ApiResponse res = apiRequestHandler.handleApiRequest(url, HttpMethod.POST, reqEntity);
                logger.info("{} Response wallet url::: {} " ,CLASS, res);

                if(!res.isSuccess()){
                    return new ApiResponse(false,res.getMessage(),null);
                }

                return saveWalletCertReqIntoTable(id,paymentReferenceId);

            }

        }catch (Exception e){
            logger.error("Unexpected exception", e);
            return new ApiResponse(false,"Something went wrong",null);
        }
    }

    @Override
    public ApiResponse changeStatusOfWalletCert(Long id, String status) {

        Optional<WalletCertApprovalEntity> walletCertApproval = walletCertRequestsRepo.findById(id);
        OrganizationEntity organizationEntity = organizationRepository.findById(walletCertApproval.get().getOrgDetailsId()).orElseThrow(() ->
                new ValidationException("Organization not found"));

        if(status.equals("APPROVED")){
            return approveWalletCert(id,organizationEntity.getOuid(),walletCertApproval.get().getPaymentTransactionNo());
        }else if(status.equals("REJECTED")){
            return rejectWalletCert(id);
        }
        else{
            return new ApiResponse(false,"send proper request",null);
        }
    }


    @Override
    public ApiResponse getAllWalletReqs() {
        try{
            return new ApiResponse(true,"Fetched Successfully",walletCertRequestsRepo.findAll());

        }catch (Exception e){
            logger.error("Unexpected exception", e);
            return new ApiResponse(false,"Something went wrong",null);
        }
    }

    public ApiResponse callOrgWalletCert(String ouid, String paymentReferenceId){
        try{

            WalletIssueDTO walletIssueDTO = new WalletIssueDTO();
            walletIssueDTO.setOrganizationUid(ouid);
            walletIssueDTO.setTransactionReferenceId(paymentReferenceId);

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> reqEntity = new HttpEntity<>(walletIssueDTO,headers);

            String url = walletUrl;

            ApiResponse res = apiRequestHandler.handleApiRequest(url, HttpMethod.POST, reqEntity);
            logger.info("{} Response wallet url::: {} " ,CLASS, res);

            if(!res.isSuccess()){
                return new ApiResponse(false,res.getMessage(),null);
            }

            return new ApiResponse(res.isSuccess(),res.getMessage(),res.getResult());


        }catch(Exception e){
            logger.error("Unexpected exception", e);
            return new ApiResponse(false,"Something went wrong",null);

        }

    }

    public ApiResponse rejectWalletCert(Long id){
        Optional<WalletCertApprovalEntity> walletCertApprovalEntity = walletCertRequestsRepo.findById(id);
        if (walletCertApprovalEntity.isEmpty()) {
            return new ApiResponse(false, "Wallet certificate request not found for id: " + id,null);
        }
        WalletCertApprovalEntity entity = walletCertApprovalEntity.get();
        entity.setStatus("REJECTED");
        entity.setUpdatedOn(AppUtil.getDate());
        return new ApiResponse(true,"Wallet Certificate request rejected successfully",null);

    }



    public ApiResponse approveWalletCert(Long id,String ouid, String paymentReferenceId){
        Optional<WalletCertApprovalEntity> walletCertApprovalEntity = walletCertRequestsRepo.findById(id);
        if (walletCertApprovalEntity.isEmpty()) {
            return new ApiResponse(false, "Wallet certificate request not found for id: " + id,null);
        }

        ApiResponse response = callOrgWalletCert(ouid,paymentReferenceId);
        if(!response.isSuccess()){
            return response;
        }
        WalletCertApprovalEntity entity = walletCertApprovalEntity.get();
        entity.setStatus("APPROVED");
        entity.setUpdatedOn(AppUtil.getDate());
        walletCertRequestsRepo.save(entity);
        return new ApiResponse(true,"Wallet Certificate request approved successfully",null);

    }


    public ApiResponse saveWalletCertReqIntoTable(Long id,String paymentReferenceId){
        OrganizationEntity organizationEntity = organizationRepository.findById(id).orElseThrow(() ->
                new ValidationException("Organization not found"));
        Optional<SpocEntity> spocEntity = spocRepository.findByOrgDetailsId(id);
        WalletCertApprovalEntity walletCertApproval = walletCertRequestsRepo.findByOrgDetailsID(id);

        if(walletCertApproval!=null){
            walletCertApproval.setStatus("PENDING");
            walletCertApproval.setUpdatedOn(AppUtil.getDate());
            walletCertRequestsRepo.save(walletCertApproval);
            return new ApiResponse(true,"Wallet Certificate Renew Request is saved successfully",null);
        }else {


            WalletCertApprovalEntity walletCertApprovalEntity = new WalletCertApprovalEntity();
            walletCertApprovalEntity.setOrgName(organizationEntity.getOrgName());
            walletCertApprovalEntity.setOrgDetailsId(organizationEntity.getId());
            walletCertApprovalEntity.setPaymentTransactionNo(paymentReferenceId);
            walletCertApprovalEntity.setSpocName(spocEntity.get().getSpocName());
            walletCertApprovalEntity.setStatus("PENDING");
            walletCertApprovalEntity.setCreatedOn(AppUtil.getDate());
            walletCertApprovalEntity.setUpdatedOn(AppUtil.getDate());
            walletCertRequestsRepo.save(walletCertApprovalEntity);
            return new ApiResponse(true, "Wallet Certificate Request is saved successfully", null);
        }
    }

    @Override
    public ApiResponse walletDetails(long orgDetailsId) {
        try {

            OrganizationEntity organization = organizationRepository
                    .findById(orgDetailsId)
                    .orElseThrow(() -> new ValidationException("Organization not found"));


            if (walletCertAdminApproval) {

                Optional<WalletCertApprovalEntity> approvalOpt =
                        walletCertRequestsRepo.findTopByOrgDetailsIdOrderByCreatedOnDesc(orgDetailsId);

                if (approvalOpt.isEmpty()) {
                    WalletCertResponseDto dto = new WalletCertResponseDto();
                    dto.setStatus("NOT_FOUND");
                    return new ApiResponse(true, "No certificate request found", dto);
                }

                WalletCertApprovalEntity approval = approvalOpt.get();
                String status = approval.getStatus();

                if ("PENDING".equalsIgnoreCase(status)) {
                    WalletCertResponseDto dto = new WalletCertResponseDto();
                    dto.setStatus("PENDING");
                    return new ApiResponse(true, "Certificate pending approval", dto);
                }

                if ("REJECTED".equalsIgnoreCase(status)) {
                    WalletCertResponseDto dto = new WalletCertResponseDto();
                    dto.setStatus("REJECTED");
                    return new ApiResponse(true, "Certificate rejected", dto);
                }

                if ("APPROVED".equalsIgnoreCase(status)) {
                    return fetchWalletCertificateByOuid(organization.getOuid());
                }
            }


            return fetchWalletCertificateByOuid(organization.getOuid());

        } catch (Exception e) {
            logger.error("Unexpected exception", e);
            return new ApiResponse(false, "Something went wrong", null);
        }
    }

    private ApiResponse fetchWalletCertificateByOuid(String ouid) {

        WalletCertResponseDto dto = new WalletCertResponseDto();

        String url = walletGetByOuidUrl + "/" + ouid;

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        ApiResponse res = apiRequestHandler.handleApiRequest(
                url,
                HttpMethod.GET,
                requestEntity
        );

        logger.info("{} Response wallet cert by ouid ::: {}", CLASS, res);




        if (!res.isSuccess() || res.getResult() == null) {
            dto.setStatus("NOT_FOUND");
        }

        else if(res.isSuccess() && res.getResult() == null){
            dto.setStatus("NOT_FOUND");
        }
        else{
            Object resultObj = res.getResult();

            if (!(resultObj instanceof Map)) {
                return new ApiResponse(false, "Invalid wallet certificate response", null);
            }

            Map<?, ?> result = (Map<?, ?>) resultObj;
            dto.setStatus((String) result.get("certificateStatus"));
            dto.setCertificateIssueDate(
                    trimDate((String) result.get("certificateStartDate"))
            );
            dto.setCertificateExpiryDate(
                    trimDate((String) result.get("certificateEndDate"))
            );
        }


        return new ApiResponse(true, "Wallet certificate fetched successfully", dto);
    }


    private String trimDate(String dateTime) {
        if (dateTime == null || dateTime.isBlank()) {
            return null;
        }
        return dateTime.split("T| ")[0];
    }






}
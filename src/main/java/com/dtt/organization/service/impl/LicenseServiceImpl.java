package com.dtt.organization.service.impl;

import com.dtt.organization.dto.ApplyLicenseDTO;
import com.dtt.organization.model.OrganizationEntity;
import com.dtt.organization.repository.OrganizationRepository;
import com.dtt.organization.service.iface.LicenseService;
import com.dtt.organization.util.APIRequestHandler;
import com.dtt.organization.util.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;


@Service
public class LicenseServiceImpl implements LicenseService {


    private static final String CLASS = "LicenseServiceImpl";
    private static final Logger logger = LoggerFactory.getLogger(LicenseServiceImpl.class);

    private final OrganizationRepository organizationRepository;

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Value("${generate.license}")
    String generateLicense;

    @Value("${download.license}")
    String downloadLicense;

    @Autowired
    APIRequestHandler apiRequestHandler;


    ObjectMapper objectMapper = new ObjectMapper();


    public LicenseServiceImpl(OrganizationRepository organizationRepository) {

        this.organizationRepository = organizationRepository;
    }


    @Override
    public ResponseEntity<Resource> downloadLicense(String ouid,String type) {

        try {
            logger.info("{} downloadLicense() for ouid :: {}",CLASS,ouid);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String allclienttUrl = downloadLicense + ouid + "/" + type;
            logger.info("{} sending GET request to external API | url={}", CLASS, allclienttUrl);
            HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
            ApiResponse res = apiRequestHandler.handleApiRequest(allclienttUrl, HttpMethod.GET, requestEntity);
            logger.info("{} API response received | ouid={} | success={}", CLASS, ouid, res.isSuccess());


            if (!res.isSuccess()) {
                logger.error("{} downloadLicense failed | ouid={} | reason=API response unsuccessful", CLASS, ouid);
                throw new RuntimeException("Didnot get Proper resposne");
            }

            String content = (String) res.getResult();

            byte[] data = content.getBytes(StandardCharsets.UTF_8);
            Resource resource = new ByteArrayResource(data);

            String fileName = "license.txt";
            logger.info("{} downloadLicense successful | ouid={} | fileName={}", CLASS, ouid, fileName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(data.length)
                    .body(resource);

        } catch (Exception e) {
            logger.error("{} downloadLicense failed | ouid={} | exception={}", CLASS, ouid, e.getMessage(), e);
            throw new RuntimeException("License download failed", e);
        }
    }


    @Override
    public ApiResponse applyLicense(Long orgId) {
        logger.info("{} applyLicense() for orgId={}", CLASS, orgId);
        try {
            OrganizationEntity org = organizationRepository.findById(orgId)
                    .orElseThrow(() -> {
                        logger.warn("{} applyLicense() Organization not found | orgId={}", CLASS, orgId);
                        return new RuntimeException("Organization not found");
                    });

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ApplyLicenseDTO downloadLicenseDTO = new ApplyLicenseDTO();
            downloadLicenseDTO.setOuid(org.getOuid());
            downloadLicenseDTO.setLicenseType("COMMERCIAL");

            String applicationType = ("ENTERPRISE_GATEWAY" + "_" + org.getId()).replace(" ", "_");

            downloadLicenseDTO.setApplicationType(applicationType);
            logger.info("{}  applyLicense API request for orgId={} | ouid={} | applicationType={}",
                    CLASS, orgId, org.getOuid(), applicationType);

            logger.info("{} DownloadLicenseDTO {}", CLASS, downloadLicenseDTO);


            HttpEntity<Object> reqEntity = new HttpEntity<>(downloadLicenseDTO, headers);

            String url = generateLicense;

            ApiResponse res = apiRequestHandler.handleApiRequest(url, HttpMethod.POST, reqEntity);

            if (!res.isSuccess()) {
                logger.error("{} applyLicense API failed | orgId={} | message={}", CLASS, orgId, res.getMessage());
                return new ApiResponse(false, res.getMessage(), null);
            }


            String admin = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(res.getResult());
            System.out.println(" admin "+admin+" \n "+res.getResult());
            if(admin != null){
                logger.info("{} applyLicense successful | orgId={}", CLASS, orgId);
                return new ApiResponse(true, res.getMessage(), null);
            }else{


                logger.info("{} applyLicense submitted for approval | orgId={}", CLASS, orgId);

                return new ApiResponse( true, "Your license request has been submitted, and you will be notified when it is approved.", null);
            }


        } catch (Exception e) {
            logger.error("{} applyLicense() failed | orgId={} | exception={}", CLASS, orgId, e.getMessage(), e);
            logger.error("Unexpected exception", e);
            return new ApiResponse(false, "Something went wrong", null);
        }

    }
}


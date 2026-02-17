package com.dtt.organization.service.impl;

import com.dtt.organization.dto.SoftwareLicenseDTO;

import com.dtt.organization.dto.SoftwareResponseDto;
import com.dtt.organization.dto.SoftwareWithLicenseDTO;
import com.dtt.organization.dto.UploadSofwareDTO;

import com.dtt.organization.model.OrganizationEntity;
import com.dtt.organization.model.SoftwareEntity;

import com.dtt.organization.repository.MetaSoftwareRepo;
import com.dtt.organization.repository.OrganizationRepository;
import com.dtt.organization.repository.SoftwareRepository;
import com.dtt.organization.service.iface.SoftwareService;
import com.dtt.organization.util.APIRequestHandler;
import com.dtt.organization.util.ApiResponse;
import com.dtt.organization.util.AppUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
@Transactional
public class SoftwareServiceImpl implements SoftwareService {

    private static final String CLASS = "SoftwareServiceImpl";
    private static final Logger logger = LoggerFactory.getLogger(SoftwareServiceImpl.class);

    private final SoftwareRepository softwareRepository;

    private final OrganizationRepository organizationRepository;

    private final MetaSoftwareRepo metaSoftwareRepo;

    @Autowired
    APIRequestHandler apiRequestHandler;

    public SoftwareServiceImpl(
            SoftwareRepository softwareRepository,
            OrganizationRepository organizationRepository,MetaSoftwareRepo metaSoftwareRepo) {

        this.softwareRepository = softwareRepository;

        this.organizationRepository = organizationRepository;
        this.metaSoftwareRepo = metaSoftwareRepo;
    }

    @Value("${software.upload.path}")
    String softwareUploadPath;

    @Value("${all.license.ouid}")
    String allLicenseOuid;


    ObjectMapper objectMapper = new ObjectMapper();

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");





    @Override
    public ApiResponse publishOrUnpublishSoftware(Long softwareId, String action) {
        logger.info("{} software publish or Unpublish for software id {} , action{}::" ,CLASS,softwareId,action);
        try {
            SoftwareEntity software = softwareRepository.findById(softwareId).orElse(null);

            if (software == null) {
                return new ApiResponse(false,"Software not found", null);
            }

            if ("publish".equalsIgnoreCase(action)) {

                if ("PUBLISHED".equalsIgnoreCase(software.getStatus())) {
                    return new ApiResponse(false, "Software already published", null);
                }

                boolean alreadyPublished = softwareRepository.existsByStatusIgnoreCase("PUBLISHED");

                if (alreadyPublished) {
                    return new ApiResponse(false, "Another software is already published. Unpublish it first.", null);
                }

                software.setStatus("PUBLISHED");
                software.setPublishedOn(AppUtil.getDate());

            }
            else if ("unpublish".equalsIgnoreCase(action)) {

                if ("NOT_PUBLISHED".equalsIgnoreCase(software.getStatus())) {
                    return new ApiResponse(false,"Software already unpublished",null);
                }

                software.setStatus("NOT_PUBLISHED");

            } else {
                return new ApiResponse(false,"Invalid action",null);
            }

            software.setUpdatedOn(AppUtil.getDate());
            softwareRepository.save(software);

            return new ApiResponse(true,"Software status updated successfully", null);

        } catch (Exception e) {
            logger.error("Unexpected exception", e);
            return new ApiResponse(false,"Something wemt wrong",null);
        }
    }




@Override
public ApiResponse getAllSoftwares() {
    logger.info("{} get All softwares", CLASS);
    try {
        List<SoftwareEntity> list = softwareRepository.findAll();

        List<SoftwareResponseDto> response = list.stream().map(entity -> {
            SoftwareResponseDto dto = new SoftwareResponseDto();
            dto.setSoftwareId(entity.getSoftwareId());
            dto.setFileName(entity.getFileName());
            dto.setSoftwareVersion(entity.getSoftwareVersion());
            dto.setDownloadLink(entity.getDownloadLink());
            dto.setInstallManual(entity.getInstallManual());


            dto.setSoftwareName(
                    entity.getSoftwareName() != null
                            ? entity.getSoftwareName().replace("_", " ")
                            : null
            );

            dto.setSizeOfSoftware(entity.getSizeOfSoftware());
            dto.setStatus(entity.getStatus());
            dto.setCreatedOn(entity.getCreatedOn());
            dto.setUpdatedOn(entity.getUpdatedOn());
            dto.setPublishedOn(entity.getPublishedOn());
            return dto;
        }).toList();

        return new ApiResponse(true, "Software list fetched", response);

    } catch (Exception e) {
        logger.error("Unexpected exception", e);
        return new ApiResponse(false, "Something went wrong", null);
    }
}





    @Override
    public ApiResponse uploadSoftware(UploadSofwareDTO dto, MultipartFile zipFile) {
        logger.info("{} software upload :::" ,CLASS);
        try {



            String softwareName = sanitize(dto.getSoftwareName());
            String version = sanitize(dto.getSoftwareVersion());


            boolean exists = softwareRepository.existsBySoftwareNameIgnoreCaseAndSoftwareVersionIgnoreCase(softwareName, version);

            if (exists) {
                return new ApiResponse(false, "Software with same version already exists", null);
            }

            if (zipFile == null || zipFile.isEmpty()) {
                return new ApiResponse(false, "ZIP file is required", null);
            }


            long maxSize = 2L * 1024 * 1024 * 1024; // 2GB
            if (zipFile.getSize() > maxSize) {
                return new ApiResponse(false, "ZIP file size exceeds limit", null);
            }


            Path versionDir = Paths.get(softwareUploadPath, softwareName, version);

            Files.createDirectories(versionDir);


            String zipFileName = sanitize(zipFile.getOriginalFilename());


            Path zipPath = versionDir.resolve(zipFileName);


            Files.copy(zipFile.getInputStream(), zipPath,
                    StandardCopyOption.REPLACE_EXISTING);

            double zipSizeKb = zipFile.getSize() / 1024.0;


            SoftwareEntity software = new SoftwareEntity();
            software.setSoftwareName(softwareName);
            software.setSoftwareVersion(version);


            software.setFileName(zipFileName);

            software.setSizeOfSoftware(String.format("%.2f Kb", zipSizeKb));

            software.setStatus("NOT_PUBLISHED");
            software.setCreatedOn(AppUtil.getDate());
            software.setUpdatedOn(AppUtil.getDate());

            softwareRepository.save(software);

            return new ApiResponse(true, "Software uploaded successfully", null);

        } catch (Exception e) {

            return new ApiResponse(false, "Something went wrong while uploading software", null);
        }
    }


    private String sanitize(String input) {
        return input.replaceAll("[^a-zA-Z0-9._-]", "_");
    }



    @Override
    public ResponseEntity<Resource> downloadSoftware(Long  softwareId) {
        logger.info("{} download upload :::" ,CLASS);
        try {

            SoftwareEntity software = softwareRepository.findById(softwareId).orElseThrow(() -> new RuntimeException("Software not found"));

            String softwareName = software.getSoftwareName();
            String version = software.getSoftwareVersion();
            String fileName = software.getFileName();

            String basePath = softwareUploadPath;


            String versionDirectory = basePath + softwareName + "/" + version + "/";

            String filePath = versionDirectory + fileName;

            File file = ResourceUtils.getFile(filePath);
            Path path = Paths.get(filePath);


            long fileSizeInBytes = file.length();


            String contentType;
            String fileExtension = "";
            int dotIndex = fileName.lastIndexOf('.');
            if (dotIndex >= 0 && dotIndex < fileName.length() - 1) {
                fileExtension = fileName.substring(dotIndex + 1).toLowerCase();
            }
            switch (fileExtension.toLowerCase()) {
                case "pdf":
                    contentType = "application/pdf";
                    break;
                case "zip":
                    contentType = "application/zip";
                    break;
                default:
                    contentType = "application/octet-stream";
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(fileSizeInBytes);
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName).build());

            Resource fileSystemResource = new UrlResource(path.toUri());
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileSystemResource);



        } catch (IOException e) {

            logger.error("Unexpected exception", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }




    @Override
    public List<SoftwareWithLicenseDTO> getSoftwareLicenseCards(Long orgDetailsId) {
        logger.info("{} get software license cards for orgDetailsId{} ::" ,CLASS,orgDetailsId);
        OrganizationEntity org = organizationRepository.findById(orgDetailsId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        String ouid = org.getOuid();
        logger.info("{} Calling License ouid url={}", CLASS, allLicenseOuid);
        ApiResponse res = apiRequestHandler.handleApiRequest(allLicenseOuid + ouid, HttpMethod.GET, new HttpEntity<>(new HttpHeaders())
        );

        List<SoftwareLicenseDTO> licenses =
                res.isSuccess()
                        ? objectMapper.convertValue(
                        res.getResult(),
                        new TypeReference<List<SoftwareLicenseDTO>>() {})
                        : List.of();


        Map<String, List<SoftwareLicenseDTO>> licenseMap = new HashMap<>();
        for (SoftwareLicenseDTO lic : licenses) {
            licenseMap
                    .computeIfAbsent(lic.getAppid().toUpperCase(), k -> new ArrayList<>())
                    .add(lic);
        }

        List<SoftwareEntity> softwares =
                softwareRepository.findByStatusIgnoreCase("PUBLISHED");

        List<SoftwareWithLicenseDTO> result = new ArrayList<>();

        for (SoftwareEntity software : softwares) {

            List<SoftwareLicenseDTO> swLicenses =
                    licenseMap.getOrDefault(
                            software.getSoftwareName().toUpperCase(),
                            List.of()
                    );


            if (swLicenses.isEmpty()) {

                SoftwareWithLicenseDTO dto = new SoftwareWithLicenseDTO();
                dto.setSoftwareId(software.getSoftwareId());
                dto.setSoftwareName(software.getSoftwareName());
                dto.setSoftwareVersion(software.getSoftwareVersion());
                dto.setOrgName(org.getOrgName());

                dto.setLicenceStatus("NONE");

                result.add(dto);
            }

            for (SoftwareLicenseDTO lic : swLicenses) {

                SoftwareWithLicenseDTO dto = new SoftwareWithLicenseDTO();
                dto.setSoftwareId(software.getSoftwareId());
                dto.setSoftwareName(software.getSoftwareName());
                dto.setSoftwareVersion(software.getSoftwareVersion());
                dto.setOrgName(org.getOrgName());

                dto.setLicenseId(Long.valueOf(lic.getId()));
                dto.setOuid(lic.getOuid());
                dto.setLicenceStatus(lic.getLicenceStatus());
                dto.setLicenseType(lic.getLicenseType());
                dto.setValidUpTo(lic.getValidUpTo());

                result.add(dto);
            }
        }

        return result;
    }


    @Override
    public ApiResponse getSoftwareNameWithValues() {
        logger.info("{} software name :::" ,CLASS);
        try{

            return new ApiResponse(true,"Fetched",metaSoftwareRepo.findAll());


        }catch (Exception e){
            logger.error("Unexpected exception", e);
            return new ApiResponse(false,"Something went wrong",null);

        }
    }
}

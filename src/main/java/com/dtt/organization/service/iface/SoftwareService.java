package com.dtt.organization.service.iface;

import com.dtt.organization.dto.SoftwareWithLicenseDTO;
import com.dtt.organization.dto.UploadSofwareDTO;
import com.dtt.organization.util.ApiResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SoftwareService {

    ApiResponse uploadSoftware(UploadSofwareDTO dto, MultipartFile softwareZip);

    ApiResponse publishOrUnpublishSoftware(Long softwareId, String action);

    ApiResponse getAllSoftwares();

    List<SoftwareWithLicenseDTO> getSoftwareLicenseCards(Long orgDetailsId);

    ResponseEntity<Resource> downloadSoftware(Long softwareId);

    ApiResponse getSoftwareNameWithValues();
}

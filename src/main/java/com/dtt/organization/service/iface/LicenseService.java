package com.dtt.organization.service.iface;

import com.dtt.organization.util.ApiResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface LicenseService {

    ApiResponse applyLicense(Long orgId);

    ResponseEntity<Resource> downloadLicense(String ouid,String type);






}

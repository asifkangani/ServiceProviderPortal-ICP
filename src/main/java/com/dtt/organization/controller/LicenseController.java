package com.dtt.organization.controller;


import com.dtt.organization.service.iface.LicenseService;
import com.dtt.organization.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/license")
public class LicenseController {


    private static final String CLASS = "LicenseController";
    private static final Logger logger = LoggerFactory.getLogger(LicenseController.class);

        @Autowired
        private LicenseService licenseService;

        @PostMapping("/apply")
        public ApiResponse applyLicense(@RequestParam Long orgId) {
            logger.info("{} apply license for orgId={}", CLASS, orgId);
            return licenseService.applyLicense(orgId);
        }


        @GetMapping("/download/{ouid}/{type}")
        public ResponseEntity<Resource> downloadLicense(@PathVariable("ouid") String ouid,
                                                        @PathVariable("type") String type) {
            logger.info("{} download license for ouid={}", CLASS, ouid);
            return licenseService.downloadLicense(ouid, type);
        }




}

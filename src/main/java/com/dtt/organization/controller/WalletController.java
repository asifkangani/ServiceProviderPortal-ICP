package com.dtt.organization.controller;


import com.dtt.organization.service.iface.WalletIface;
import com.dtt.organization.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    WalletIface walletIface;

    private static final String CLASS = "WalletController";
    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

    @PostMapping("/save/wallet")
    public ApiResponse saveWallet(@RequestParam Long orgDetailsId,@RequestParam(required = false) String paymentId) {
        logger.info("{} saveWallet",CLASS);
        return walletIface.generateWalletCert(orgDetailsId,paymentId);
    }

    @PostMapping("/save/wallet/req")
    public ApiResponse saveWalletReq(@RequestParam Long orgDetailsId,@RequestParam(required = false) String paymentId) {
        logger.info("{} saveWalletReq",CLASS);
        return walletIface.saveWalletCertRequest(orgDetailsId,paymentId);
    }



}

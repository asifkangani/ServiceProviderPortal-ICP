package com.dtt.organization.service.iface;

import com.dtt.organization.util.ApiResponse;

public interface WalletIface {

    ApiResponse generateWalletCert(Long id,String paymentReferenceId);

    ApiResponse saveWalletCertRequest(Long id,String paymentReferenceId);

    ApiResponse changeStatusOfWalletCert(Long id,String status);

    ApiResponse getAllWalletReqs();

    ApiResponse walletDetails(long id);



}

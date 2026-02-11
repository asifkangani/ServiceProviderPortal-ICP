package com.dtt.organization.dto;


import com.auth0.jwk.InvalidPublicKeyException;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class JwkNew {
    private static final String PUBLIC_KEY_ALGORITHM = "RSA";

    private String id;
    private String type;
    private String algorithm;
    private String usage;
    private List<String> operations;
    private String certificateUrl;
    private List<String> certificateChain;
    private String certificateThumbprint;
    private Map<String, Object> additionalAttributes;
    private String n;
    private String e;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public List<String> getOperations() {
        return operations;
    }

    public void setOperations(List<String> operations) {
        this.operations = operations;
    }

    public String getCertificateUrl() {
        return certificateUrl;
    }

    public void setCertificateUrl(String certificateUrl) {
        this.certificateUrl = certificateUrl;
    }

    public List<String> getCertificateChain() {
        return certificateChain;
    }

    public void setCertificateChain(List<String> certificateChain) {
        this.certificateChain = certificateChain;
    }

    public String getCertificateThumbprint() {
        return certificateThumbprint;
    }

    public void setCertificateThumbprint(String certificateThumbprint) {
        this.certificateThumbprint = certificateThumbprint;
    }

    public Map<String, Object> getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setAdditionalAttributes(Map<String, Object> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    @Override
    public String toString() {
        return "Jwk [id=" + id + ", type=" + type + ", algorithm=" + algorithm + ", usage=" + usage + ", operations="
                + operations + ", certificateUrl=" + certificateUrl + ", certificateChain=" + certificateChain
                + ", certificateThumbprint=" + certificateThumbprint + ", additionalAttributes=" + additionalAttributes
                + "]";
    }

    public PublicKey getPublicKey() throws InvalidPublicKeyException {
        if (!PUBLIC_KEY_ALGORITHM.equalsIgnoreCase(type)) {
            throw new InvalidPublicKeyException("The key is not of type RSA", null);
        }
        try {
            KeyFactory kf = KeyFactory.getInstance(PUBLIC_KEY_ALGORITHM);
//            BigInteger modulus = new BigInteger(1, Base64.decodeBase64(getN()));
//            BigInteger exponent = new BigInteger(1, Base64.decodeBase64(getE()));
            BigInteger modulus = new BigInteger(1, Base64.getDecoder().decode(getN()));
            BigInteger exponent = new BigInteger(1, Base64.getDecoder().decode(getE()));
            return kf.generatePublic(new RSAPublicKeySpec(modulus, exponent));
        } catch (InvalidKeySpecException e) {
            throw new InvalidPublicKeyException("Invalid public key", e);
        } catch (NoSuchAlgorithmException e) {
            throw new InvalidPublicKeyException("Invalid algorithm to generate key", e);
        }
    }

}



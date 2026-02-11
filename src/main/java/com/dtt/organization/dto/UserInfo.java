package com.dtt.organization.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    String username;
    String email;
    String gender;
    String name;
    String suid;
    String birthdate;
    String phone;
    String id_document_type;
    String id_document_number;
    String loa;
    String country;
    String idToken;
    String applicationID;
    String profile_image;
    boolean smart_phone_user;
    String sub;
    String login_type;

    List<LoginProfile> login_profile;

    String accessToken;

    public UserInfo() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuid() {
        return suid;
    }

    public void setSuid(String suid) {
        this.suid = suid;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId_document_type() {
        return id_document_type;
    }

    public void setId_document_type(String id_document_type) {
        this.id_document_type = id_document_type;
    }

    public String getId_document_number() {
        return id_document_number;
    }

    public void setId_document_number(String id_document_number) {
        this.id_document_number = id_document_number;
    }

    public String getLoa() {
        return loa;
    }

    public void setLoa(String loa) {
        this.loa = loa;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    public List<LoginProfile> getLogin_profile() {
        return login_profile;
    }

    public void setLogin_profile(List<LoginProfile> login_profile) {
        this.login_profile = login_profile;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public boolean isSmart_phone_user() {
        return smart_phone_user;
    }

    public void setSmart_phone_user(boolean smart_phone_user) {
        this.smart_phone_user = smart_phone_user;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", name='" + name + '\'' +
                ", suid='" + suid + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", phone='" + phone + '\'' +
                ", id_document_type='" + id_document_type + '\'' +
                ", id_document_number='" + id_document_number + '\'' +
                ", loa='" + loa + '\'' +
                ", country='" + country + '\'' +
                ", idToken='" + idToken + '\'' +
                ", applicationID='" + applicationID + '\'' +
                ", profile_image='" + profile_image + '\'' +
                ", smart_phone_user=" + smart_phone_user +
                ", sub='" + sub + '\'' +
                ", login_type='" + login_type + '\'' +
                ", login_profile=" + login_profile +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}


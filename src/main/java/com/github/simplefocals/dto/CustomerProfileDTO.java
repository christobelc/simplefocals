package com.github.simplefocals.dto;

public class CustomerProfileDTO {

    private Integer id;
    private String email;
    private String address;
    private String county;
    private String postcode;
    private double rightEye;
    private double leftEye;
    private String prescriptionImgName;

    public CustomerProfileDTO() {
    }

    public CustomerProfileDTO(Integer id,
                              String email,
                              String address,
                              String county,
                              String postcode,
                              double rightEye,
                              double leftEye,
                              String prescriptionImgName) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.county = county;
        this.postcode = postcode;
        this.rightEye = rightEye;
        this.leftEye = leftEye;
        this.prescriptionImgName = prescriptionImgName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }


    public double getRightEye() {
        return rightEye;
    }

    public void setRightEye(double rightEye) {
        this.rightEye = rightEye;
    }

    public double getLeftEye() {
        return leftEye;
    }

    public void setLeftEye(double leftEye) {
        this.leftEye = leftEye;
    }

    public String getPrescriptionImgName() {
        return prescriptionImgName;
    }

    public void setPrescriptionImgName(String prescriptionImgName) {
        this.prescriptionImgName = prescriptionImgName;
    }
}

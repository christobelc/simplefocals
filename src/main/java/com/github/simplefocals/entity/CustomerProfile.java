package com.github.simplefocals.entity;

import javax.persistence.*;

@Entity
@Table(name = "customer_profile")
public class CustomerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "county", nullable = false)
    private String county;

    @Column(name = "postcode", nullable = false, length = 7)
    private String postcode;

    @Column(name = "right_eye", nullable = false)
    private double rightEye;

    @Column(name = "left_eye", nullable = false)
    private double leftEye;

    @Column(name = "prescription_img_name")
    private String prescriptionImgName;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customerProfile")
    private User user;

    public CustomerProfile() {
    }

    public CustomerProfile(Integer id){
        this.id = id;
    }

    public CustomerProfile(Integer id,
                           String email,
                           String address,
                           String county,
                           String postcode,
//                           Integer phoneNo,
                           double rightEye,
                           double leftEye,
                           String prescriptionImgName,
                           User user) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.county = county;
        this.postcode = postcode;
//        this.phoneNo = phoneNo;
        this.rightEye = rightEye;
        this.leftEye = leftEye;
        this.prescriptionImgName = prescriptionImgName;
        this.user = user;
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

//    public Integer getPhoneNo() {
//        return phoneNo;
//    }
//
//    public void setPhoneNo(Integer phoneNo) {
//        this.phoneNo = phoneNo;
//    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

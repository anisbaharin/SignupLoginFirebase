package com.example.signuploginfirebase;
public class Court {
    private String documentId;
    private String courtNumber;
    private String courtName;
    private String courtDetail;
    private String courtPrice;
    private String imageUrl;

    // Constructors
    public Court() {
        // Default constructor required for Firestore
    }

    public Court(String courtNumber, String courtName, String courtDetail, String courtPrice, String imageUrl) {
        this.courtNumber = courtNumber;
        this.courtName = courtName;
        this.courtDetail = courtDetail;
        this.courtPrice = courtPrice;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getCourtNumber() {
        return courtNumber;
    }

    public void setCourtNumber(String courtNumber) {
        this.courtNumber = courtNumber;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getCourtDetail() {
        return courtDetail;
    }

    public void setCourtDetail(String courtDetail) {
        this.courtDetail = courtDetail;
    }

    public String getCourtPrice() {
        return courtPrice;
    }

    public void setCourtPrice(String courtPrice) {
        this.courtPrice = courtPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

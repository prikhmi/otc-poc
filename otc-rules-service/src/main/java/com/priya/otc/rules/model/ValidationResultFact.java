package com.priya.otc.rules.model;

import java.util.ArrayList;
import java.util.List;

public class ValidationResultFact {

    private String paymentId;

    private boolean reject;
    private boolean review;

    private String status = "APPROVED"; // default
    private List<String> messages = new ArrayList<>();

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public boolean isReject() {
        return reject;
    }

    public boolean isReview() {
        return review;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void addRejectMessage(String msg) {
        this.reject = true;
        this.messages.add("[REJECT] " + msg);
    }

    public void addReviewMessage(String msg) {
        this.review = true;
        this.messages.add("[REVIEW] " + msg);
    }

    public void finalizeStatus() {
        if (reject) {
            this.status = "REJECTED";
        } else if (review) {
            this.status = "MANUAL_REVIEW";
        } else {
            this.status = "APPROVED";
        }
    }
}

package com.priya.otc.orchestrator.dto;

import java.math.BigDecimal;

public class RuleRequestDto {

    private String paymentId;
    private BigDecimal amount;
    private String currency;
    private String originCountry;
    private String destinationCountry;

    private String paymentMethod;  // INVOICE / CARD / CASH
    private String terms;          // NET30 / PREPAID

    // risk flags from CountryRisk
    private boolean originHighRisk;
    private boolean originSanctioned;
    private boolean destHighRisk;
    private boolean destSanctioned;

    private CustomerDto customer;
    private CustomerFinanceSummaryDto customerFinance;

    // getters & setters

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public boolean isOriginHighRisk() {
        return originHighRisk;
    }

    public void setOriginHighRisk(boolean originHighRisk) {
        this.originHighRisk = originHighRisk;
    }

    public boolean isOriginSanctioned() {
        return originSanctioned;
    }

    public void setOriginSanctioned(boolean originSanctioned) {
        this.originSanctioned = originSanctioned;
    }

    public boolean isDestHighRisk() {
        return destHighRisk;
    }

    public void setDestHighRisk(boolean destHighRisk) {
        this.destHighRisk = destHighRisk;
    }

    public boolean isDestSanctioned() {
        return destSanctioned;
    }

    public void setDestSanctioned(boolean destSanctioned) {
        this.destSanctioned = destSanctioned;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public CustomerFinanceSummaryDto getCustomerFinance() {
        return customerFinance;
    }

    public void setCustomerFinance(CustomerFinanceSummaryDto customerFinance) {
        this.customerFinance = customerFinance;
    }
}

package com.ap.homebanking_ap.dtos;

public class LoanApplicationDTO {
    private Long loanId;
    private Double amount;
    private Integer payments;

    private String toAccountNumber;

    public LoanApplicationDTO(){
    }

    public Long getLoanId(){
        return loanId;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }
}
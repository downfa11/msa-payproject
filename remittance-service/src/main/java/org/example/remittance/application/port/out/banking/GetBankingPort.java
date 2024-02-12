package org.example.remittance.application.port.out.banking;

public interface GetBankingPort {
    BankingInfo getMembershipBankingInfo(String bankName,String bankAccountName);
    boolean requestFirmbanking(String bankName,String bankAccountName,int amount);
}

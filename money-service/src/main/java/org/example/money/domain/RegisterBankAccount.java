package org.example.money.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterBankAccount {

    @Getter private final String RegisteredBankAccountId;
    @Getter private final String membershipId;
    @Getter private final String bankName;
    @Getter private final String bankAccountNumber;
    @Getter private final boolean linkedStatusIsValid;

    public static RegisterBankAccount generateRegisterBankAccount(
        RegisterBankAccount.RegisteredBankAccountId registeredBankAccountId,
        MembershipId membershipId,
        BankName bankName,
        BankAccountNumber bankAccountNumber,
        LinkedStatusIsValid linkedStatusIsValid){

        return new RegisterBankAccount(
                registeredBankAccountId.getRegisterdBankAccountId(),
                membershipId.getMembershipId(),
                bankName.getBankName(),
                bankAccountNumber.getBankAccountNumber(),
                linkedStatusIsValid.getLinkedStatusIsValid()
                );
    }
    @Value
    public static class RegisteredBankAccountId {
        public RegisteredBankAccountId(String value){
            this.registerdBankAccountId = value;
        }

        String registerdBankAccountId;
    }

    @Value
    public static class MembershipId {
        public MembershipId(String value){
            this.membershipId = value;
        }

        String membershipId;
    }

    @Value
    public static class BankName {
        public BankName(String value){
            this.bankName = value;
        }

        String bankName;
    }

    @Value
    public static class BankAccountNumber {
        public BankAccountNumber(String value){
            this.bankAccountNumber = value;
        }

        String bankAccountNumber;
    }

    @Value
    public static class LinkedStatusIsValid {
        public LinkedStatusIsValid(Boolean value){
            this.linkedStatusIsValid = value;
        }

        Boolean linkedStatusIsValid;
    }


}

package org.example.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterBankAccount {
    @Getter private final String registeredBankAccountId;

    @Getter private final String membershipId;

    @Getter private final String bankName; // enum

    @Getter private final String bankAccountNumber;

    @Getter private final boolean linkedStatusIsValid;
    @Getter private final String aggregateIdentifier;

    public static RegisterBankAccount generateRegisteredBankAccount (
            RegisterBankAccount.RegisteredBankAccountId registeredBankAccountId,
            RegisterBankAccount.MembershipId membershipId,
            RegisterBankAccount.BankName bankName,
            RegisterBankAccount.BankAccountNumber bankAccountNumber,
            RegisterBankAccount.LinkedStatusIsValid linkedStatusIsValid,
            RegisterBankAccount.AggregateIdentifier aggregateIdentifier
    ){
        return new RegisterBankAccount(
                registeredBankAccountId.registeredBankAccountId,
                membershipId.membershipId,
                bankName.bankName,
                bankAccountNumber.bankAccountNumber,
                linkedStatusIsValid.linkedStatusIsValid,
                aggregateIdentifier.getAggregateIdentifier()
        );
    }

    @Value
    public static class RegisteredBankAccountId {
        public RegisteredBankAccountId(String value) {
            this.registeredBankAccountId = value;
        }
        String registeredBankAccountId ;
    }

    @Value
    public static class MembershipId {
        public MembershipId(String value) {
            this.membershipId = value;
        }
        String membershipId ;
    }

    @Value
    public static class BankName {
        public BankName(String value) {
            this.bankName = value;
        }
        String bankName ;
    }

    @Value
    public static class BankAccountNumber {
        public BankAccountNumber(String value) {
            this.bankAccountNumber = value;
        }
        String bankAccountNumber ;
    }

    @Value
    public static class LinkedStatusIsValid {
        public LinkedStatusIsValid(boolean value) {
            this.linkedStatusIsValid = value;
        }
        boolean linkedStatusIsValid ;
    }

    @Value
    public static class AggregateIdentifier {
        public AggregateIdentifier(String value) {
            this.aggregateIdentifier = value;
        }
        String aggregateIdentifier;
    }
}
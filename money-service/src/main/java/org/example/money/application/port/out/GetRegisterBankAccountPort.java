package org.example.money.application.port.out;

public interface GetRegisterBankAccountPort {
    RegisteredBankAccountAggregateIdentifier getRegisteredBankAccount(String membershipId);
}

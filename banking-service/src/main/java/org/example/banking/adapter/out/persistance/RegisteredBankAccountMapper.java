package org.example.banking.adapter.out.persistance;

import org.example.banking.domain.RegisterBankAccount;
import org.springframework.stereotype.Component;

@Component
public class RegisteredBankAccountMapper {

    public RegisterBankAccount mapToDomainEntity(RegisteredBankAccountJpaEntity registeredBankAccountJpaEntity){
        return RegisterBankAccount.generateRegisteredBankAccount(
                new RegisterBankAccount.RegisteredBankAccountId(registeredBankAccountJpaEntity.getRegisteredBankAccountId()+""),
                new RegisterBankAccount.MembershipId(registeredBankAccountJpaEntity.getMembershipId()+""),
                new RegisterBankAccount.BankName(registeredBankAccountJpaEntity.getBankName()),
                new RegisterBankAccount.BankAccountNumber(registeredBankAccountJpaEntity.getBankAccountNumber()),
                new RegisterBankAccount.LinkedStatusIsValid(registeredBankAccountJpaEntity.isLinkedStatusIsValid()),
                new RegisterBankAccount.AggregateIdentifier(registeredBankAccountJpaEntity.getAggregateIdentifier())

        );
    }
}

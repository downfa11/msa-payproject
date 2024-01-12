package org.example.banking.application.port.out;

import org.example.banking.adapter.out.persistance.RegisteredBankAccountJpaEntity;
import org.example.banking.domain.RegisterBankAccount;

public interface RegisterBankAccountPort {

    RegisteredBankAccountJpaEntity createRegisteredBankAccount(
            RegisterBankAccount.MembershipId membershipId,
            RegisterBankAccount.BankName bankName,
            RegisterBankAccount.BankAccountNumber bankAccountNumber,
            RegisterBankAccount.LinkedStatusIsValid linkedStatusIsValid);
}

package org.example.money.adapter.out.persistance;

import lombok.RequiredArgsConstructor;
import org.example.banking.application.port.out.RegisterBankAccountPort;
import org.example.banking.domain.RegisterBankAccount;
import org.example.common.PersistanceAdapter;

@PersistanceAdapter
@RequiredArgsConstructor
public class RegisteredBankAccountPersistanceAdapter implements RegisterBankAccountPort {

    private final SpringDataRegisteredBankAccountRepository registeredBankAccountRepository;

    @Override
    public RegisteredBankAccountJpaEntity createRegisteredBankAccount(RegisterBankAccount.MembershipId membershipId, RegisterBankAccount.BankName bankName, RegisterBankAccount.BankAccountNumber bankAccountNumber, RegisterBankAccount.LinkedStatusIsValid linkedStatusIsValid) {
        return registeredBankAccountRepository.save(
                new RegisteredBankAccountJpaEntity(
                        membershipId.getMembershipId(),
                        bankName.getBankName(),
                        bankAccountNumber.getBankAccountNumber(),
                        linkedStatusIsValid.getLinkedStatusIsValid()
                )
        );
    }
}

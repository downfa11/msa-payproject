package org.example.banking.adapter.out.persistance;

import lombok.RequiredArgsConstructor;
import org.example.banking.application.port.in.GetRegisteredBankAccountCommand;
import org.example.banking.application.port.out.GetRegisterBankAccountPort;
import org.example.banking.application.port.out.RegisterBankAccountPort;
import org.example.banking.domain.RegisterBankAccount;
import org.example.common.PersistanceAdapter;

import java.util.List;

@PersistanceAdapter
@RequiredArgsConstructor
public class RegisteredBankAccountPersistanceAdapter implements RegisterBankAccountPort, GetRegisterBankAccountPort {

    private final SpringDataRegisteredBankAccountRepository registeredBankAccountRepository;

    @Override
    public RegisteredBankAccountJpaEntity createRegisteredBankAccount(RegisterBankAccount.MembershipId membershipId, RegisterBankAccount.BankName bankName, RegisterBankAccount.BankAccountNumber bankAccountNumber, RegisterBankAccount.LinkedStatusIsValid linkedStatusIsValid,RegisterBankAccount.AggregateIdentifier aggregateIdentifier) {
        return registeredBankAccountRepository.save(
                new RegisteredBankAccountJpaEntity(
                        membershipId.getMembershipId(),
                        bankName.getBankName(),
                        bankAccountNumber.getBankAccountNumber(),
                        linkedStatusIsValid.isLinkedStatusIsValid(),
                        aggregateIdentifier.getAggregateIdentifier()
                )
        );
    }

    @Override
    public RegisteredBankAccountJpaEntity getRegisteredBankAccount(GetRegisteredBankAccountCommand command) {
        List<RegisteredBankAccountJpaEntity> entityList = registeredBankAccountRepository.findByMembershipId(command.getMembershipId());
        if (entityList.size() > 0) {
            return entityList.get(0);
        }
        return null;
    }



}

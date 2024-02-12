package org.example.banking.application.port.out;

import org.example.banking.adapter.out.persistance.RegisteredBankAccountJpaEntity;
import org.example.banking.application.port.in.GetRegisteredBankAccountCommand;

public interface GetRegisterBankAccountPort {
    RegisteredBankAccountJpaEntity getRegisteredBankAccount(GetRegisteredBankAccountCommand command);
}

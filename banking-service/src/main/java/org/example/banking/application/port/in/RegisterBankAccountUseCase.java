package org.example.banking.application.port.in;

import org.example.banking.domain.RegisterBankAccount;

public interface RegisterBankAccountUseCase {
    RegisterBankAccount registerBankAccount(RegisterBankAccountCommand command);
    void registerBankAccountByEvent(RegisterBankAccountCommand command);
}

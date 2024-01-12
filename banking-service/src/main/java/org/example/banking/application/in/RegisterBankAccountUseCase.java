package org.example.banking.application.in;

import org.example.banking.domain.RegisterBankAccount;

public interface RegisterBankAccountUseCase {

    RegisterBankAccount registerBankAccount(RegisterBankAccountCommand command);
}

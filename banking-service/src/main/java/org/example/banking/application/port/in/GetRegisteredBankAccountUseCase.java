package org.example.banking.application.port.in;

import org.example.banking.adapter.in.web.GetRegisteredBankAccountController;
import org.example.banking.domain.RegisterBankAccount;

public interface GetRegisteredBankAccountUseCase {
    RegisterBankAccount getRegisterBankAccount(GetRegisteredBankAccountCommand command);
}

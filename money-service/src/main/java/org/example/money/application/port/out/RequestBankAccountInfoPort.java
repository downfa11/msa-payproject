package org.example.money.application.port.out;

import org.example.banking.adapter.out.external.bank.BankAccount;
import org.example.banking.adapter.out.external.bank.GetBankAccountRequest;

public interface RequestBankAccountInfoPort {
    public BankAccount getBankAccountInfo(GetBankAccountRequest request);

}

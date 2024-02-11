package org.example.banking.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.example.banking.application.port.in.GetRegisteredBankAccountCommand;
import org.example.banking.application.port.in.GetRegisteredBankAccountUseCase;
import org.example.banking.domain.RegisterBankAccount;
import org.example.common.WebAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class GetRegisteredBankAccountController {

    private final GetRegisteredBankAccountUseCase getRegisteredBankAccountUseCase;
    @GetMapping(path = "/banking/account/{membershipId}")
    RegisterBankAccount getRegisteredBankAccount(@PathVariable String membershipId) {
        // 편의상 사용.
        GetRegisteredBankAccountCommand command = GetRegisteredBankAccountCommand.builder().membershipId(membershipId).build();
        return getRegisteredBankAccountUseCase.getRegisterBankAccount(command);
    }
}
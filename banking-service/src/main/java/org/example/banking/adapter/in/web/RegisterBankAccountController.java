package org.example.banking.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.example.banking.application.in.RegisterBankAccountCommand;
import org.example.banking.application.in.RegisterBankAccountUseCase;
import org.example.banking.domain.RegisterBankAccount;
import org.example.common.WebAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterBankAccountController {

    private final RegisterBankAccountUseCase registerBankAccountUseCase;
    @PostMapping (path = "/banking/account/register")
    RegisterBankAccount registerMembership(@RequestBody RegisterBankAccountRequest request){
        System.out.println("Hello Bank!");
        // request -> Command로 추상화
        // UseCase ~~(request x, command)

        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
                .membershipId(request.getMembershipId())
                .bankName(request.getBankName())
                .bankAccountNumber(request.getBankAccountNumber())
                .isValid(request.isValid()).build();

        RegisterBankAccount registerBankAccount = registerBankAccountUseCase.registerBankAccount(command);
        if(registerBankAccount==null)
            return null; // To do
        return registerBankAccount;
    }
}

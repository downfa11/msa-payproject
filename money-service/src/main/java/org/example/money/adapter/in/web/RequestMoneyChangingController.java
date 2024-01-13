package org.example.money.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.example.banking.application.in.RegisterBankAccountCommand;
import org.example.banking.application.in.RegisterBankAccountUseCase;
import org.example.banking.domain.RegisterBankAccount;
import org.example.common.WebAdapter;
import org.example.money.application.in.DecreaseMoneyRequestCommand;
import org.example.money.application.in.IncreaseMoneyRequestCommand;
import org.example.money.application.in.IncreaseMoneyRequestUseCase;
import org.example.money.domain.MoneyChangingRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestMoneyChangingController {

    private final IncreaseMoneyRequestUseCase increaseMoneyRequestUseCase;
    private final DecreaseMoneyRequestUseCase decreaseMoneyRequestUseCase;
    @PostMapping (path = "/money/increase")
    MoneyChangingResultDetail increaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request){
        System.out.println("Hello Money increase!");
        // request -> Command로 추상화
        // UseCase ~~(request x, command)

        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .Amount(request.getAmount()).build();

        MoneyChangingRequest moneyChangingRequest = ;
        // MoneyChangingRequest -> MoneyChangingResultDetail

        return increaseMoneyRequestUseCase.increaseMoneyRequest(command);
    }

    @PostMapping (path = "/money/decrease")
    MoneyChangingResultDetail decreaseMoneyChangingRequest(@RequestBody DecreaseMoneyChangingRequest request){
        System.out.println("Hello Money decrease!");
        // request -> Command로 추상화
        // UseCase ~~(request x, command)

        DecreaseMoneyRequestCommand command = DecreaseMoneyRequestCommand.builder()
                .membershipId(request.getMembershipId())
                .bankName(request.getBankName())
                .bankAccountNumber(request.getBankAccountNumber())
                .isValid(request.isValid()).build();

        return decreaseMoneyRequestUseCase.decreaseMoneyRequest(command);
    }
}

package org.example.money.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.example.common.WebAdapter;
import org.example.money.application.port.in.DecreaseMoneyRequestCommand;
import org.example.money.application.port.in.DecreaseMoneyRequestUseCase;
import org.example.money.application.port.in.IncreaseMoneyRequestCommand;
import org.example.money.application.port.in.IncreaseMoneyRequestUseCase;
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

        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequest(command);
        // MoneyChangingRequest -> MoneyChangingResultDetail
        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChaningRequestId(),
                0,0, moneyChangingRequest.getChangingMoneyAmount()
        );

        return resultDetail;
    }

    @PostMapping (path = "/money/decrease")
    MoneyChangingResultDetail decreaseMoneyChangingRequest(@RequestBody DecreaseMoneyChangingRequest request){
        System.out.println("Hello Money decrease!");
        // request -> Command로 추상화
        // UseCase ~~(request x, command)

        DecreaseMoneyRequestCommand command = DecreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .Amount(request.getAmount()).build();

        MoneyChangingRequest moneyChangingRequest = decreaseMoneyRequestUseCase.decreaseMoneyRequest(command);
        // MoneyChangingRequest -> MoneyChangingResultDetail
        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChaningRequestId(),
                0,0, moneyChangingRequest.getChangingMoneyAmount()
        );
        return resultDetail;
    }
}

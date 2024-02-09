package org.example.money.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.example.common.WebAdapter;
import org.example.money.adapter.axon.common.IncreaseMemberMoneyCommand;
import org.example.money.application.port.in.*;
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
    private final CreateMemberMoneyUserCase createMemberMoneyUserCase;

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
                moneyChangingRequest.getMoneyChangingRequestId(),
                0,0, moneyChangingRequest.getChangingMoneyAmount()
        );

        return resultDetail;
    }

    @PostMapping (path = "/money/increase-async")
    MoneyChangingResultDetail increaseMoneyChangingRequestAsync(@RequestBody IncreaseMoneyChangingRequest request){
        // request -> Command로 추상화
        // UseCase ~~(request x, command)

        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .Amount(request.getAmount()).build();

        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequestAsync(command);
        // MoneyChangingRequest -> MoneyChangingResultDetail
        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
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
                moneyChangingRequest.getMoneyChangingRequestId(),
                0,0, moneyChangingRequest.getChangingMoneyAmount()
        );
        return resultDetail;
    }

    @PostMapping (path = "/money/decrease-async")
    MoneyChangingResultDetail decreaseMoneyChangingRequestAsync(@RequestBody DecreaseMoneyChangingRequest request){
        // request -> Command로 추상화
        // UseCase ~~(request x, command)

        DecreaseMoneyRequestCommand command = DecreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .Amount(request.getAmount()).build();

        MoneyChangingRequest moneyChangingRequest = decreaseMoneyRequestUseCase.decreaseMoneyRequestAsync(command);
        // MoneyChangingRequest -> MoneyChangingResultDetail
        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                0,0, moneyChangingRequest.getChangingMoneyAmount()
        );
        return resultDetail;
    }

    @PostMapping(path="/money/create-member-money")
    void createMemberMoney(@RequestBody CreateMemberMoneyRequest request){
        createMemberMoneyUserCase.createMemberMoney(CreateMemberMoneyCommand.builder().membershipId(request.getMembershipId()).build());
    }

    @PostMapping(path="/money/increase-eda")
    void increateMoneyChangingRequestByEvent(@RequestBody IncreaseMoneyChangingRequest request){
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .Amount(request.getAmount())
                .build();

        createMemberMoneyUserCase.increaseMoneyRequestByEvent(command);
        // 임시로 createMemberMoneyUserCase에서 balance의 증감도 같이 하는중
    }
}

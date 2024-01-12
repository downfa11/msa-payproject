package org.example.banking.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.example.banking.application.in.RegisterBankAccountCommand;
import org.example.banking.application.in.RegisterBankAccountUseCase;
import org.example.banking.application.in.RequestFirmbankingRequestCommand;
import org.example.banking.application.in.RequestFirmbankingUseCase;
import org.example.banking.domain.FirmbankingRequest;
import org.example.banking.domain.RegisterBankAccount;
import org.example.common.WebAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmbankingController {

    private final RequestFirmbankingUseCase requestFirmbankingUseCase;
    @PostMapping (path = "/banking/firmbanking/request")
    FirmbankingRequest registerMembership(@RequestBody RequestFirmbankingRequest request){
        System.out.println("Hello FirmBank!");
        // request -> Command로 추상화
        // UseCase ~~(request x, command)

        RequestFirmbankingRequestCommand command = RequestFirmbankingRequestCommand.builder()
                .toBankName(request.getToBankName())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
                .build();
        return requestFirmbankingUseCase.requestFirmbanking(command);
    }
}

package org.example.banking.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.example.banking.application.port.in.RequestFirmbankingRequestCommand;
import org.example.banking.application.port.in.RequestFirmbankingUseCase;
import org.example.banking.application.port.in.UpdateFirmbankingCommand;
import org.example.banking.application.port.in.UpdateFirmbankingUseCase;
import org.example.banking.domain.FirmbankingRequest;
import org.example.common.WebAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmbankingController {

    private final RequestFirmbankingUseCase requestFirmbankingUseCase;
    private final UpdateFirmbankingUseCase updateFirmbankingUseCase;

    @PostMapping (path = "/banking/firmbanking/request")
    FirmbankingRequest requestFirmbanking(@RequestBody RequestFirmbankingRequest request){
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

    @PostMapping (path = "/banking/firmbanking/request-eda")
    void requestFirmbankingByEvent(@RequestBody RequestFirmbankingRequest request){

        RequestFirmbankingRequestCommand command = RequestFirmbankingRequestCommand.builder()
                .toBankName(request.getToBankName())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
                .build();
        requestFirmbankingUseCase.requestFirmbankingByEvent(command);
    }

    @PutMapping(path = "/banking/firmbanking/update-eda")
    void updateFirmbankingByEvent(@RequestBody UpdateFirmbankingRequest request){

        UpdateFirmbankingCommand command = UpdateFirmbankingCommand.builder().
                firmbankingAggregateIdentifier(request.getFirmbankingRequestAggregateIdentifier())
                .firmbankingStatus(request.getStatus()).build();
        updateFirmbankingUseCase.updateFirmbankingByEvent(command);
    }

}

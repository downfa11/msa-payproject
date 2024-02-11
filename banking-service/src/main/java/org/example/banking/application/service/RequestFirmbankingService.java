package org.example.banking.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.banking.adapter.axon.command.CreateFirmbankingRequestCommand;
import org.example.banking.adapter.axon.command.UpdateFirmbankingRequestCommand;
import org.example.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import org.example.banking.adapter.out.external.bank.FirmbankingResult;
import org.example.banking.adapter.out.persistance.FirmbankingRequestJpaEntity;
import org.example.banking.adapter.out.persistance.FirmbankingRequestMapper;
import org.example.banking.application.port.in.RequestFirmbankingRequestCommand;
import org.example.banking.application.port.in.RequestFirmbankingUseCase;
import org.example.banking.application.port.in.UpdateFirmbankingCommand;
import org.example.banking.application.port.in.UpdateFirmbankingUseCase;
import org.example.banking.application.port.out.RequestExternalFirmbankingPort;
import org.example.banking.application.port.out.RequestFirmbankingPort;
import org.example.banking.domain.FirmbankingRequest;
import org.example.common.UseCase;

import javax.transaction.Transactional;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RequestFirmbankingService implements RequestFirmbankingUseCase, UpdateFirmbankingUseCase {

    private final RequestExternalFirmbankingPort requestExternalFirmbankingPort;
    private final RequestFirmbankingPort requestFirmbankingPort;
    private final FirmbankingRequestMapper firmbankingRequestMapper;

    private final CommandGateway gateway;

    @Override
    public FirmbankingRequest requestFirmbanking(RequestFirmbankingRequestCommand command) {

        FirmbankingRequestJpaEntity requestJpaEntity = requestFirmbankingPort.createFirmbankingRequest(
                new FirmbankingRequest.fromBankName(command.getFromBankName()),
                new FirmbankingRequest.fromBankAccountNumber(command.getFromBankAccountNumber()),
                new FirmbankingRequest.toBankName(command.getToBankName()),
                new FirmbankingRequest.toBankAccountNumber(command.getToBankAccountNumber()),
                new FirmbankingRequest.moneyAmount(command.getMoneyAmount()),
                new FirmbankingRequest.firmbankingStatus(0),
                new FirmbankingRequest.FirmbankingAggregateIdentifier("")
        );
        FirmbankingResult result = requestExternalFirmbankingPort.requestExternalFirmbanking(new ExternalFirmbankingRequest(
                command.getFromBankName(),
                command.getFromBankAccountNumber(),
                command.getToBankName(),
                command.getToBankAccountNumber(),
                command.getMoneyAmount()
        ));
        UUID randomUUID = UUID.randomUUID();
        requestJpaEntity.setUuid(randomUUID.toString());

        if(result.getResultCode()==0){
            requestJpaEntity.setFirmbankingStatus(1); // success
        }
        else requestJpaEntity.setFirmbankingStatus(2); // fail

        return firmbankingRequestMapper.mapToDomainEntity(requestFirmbankingPort.modifyFirmbankingRequest(requestJpaEntity), randomUUID);

    }

    @Override
    public void requestFirmbankingByEvent(RequestFirmbankingRequestCommand command) {

        // Command를 통해 Event Sourcing을 진행
        CreateFirmbankingRequestCommand createFirmbankingRequestCommand = CreateFirmbankingRequestCommand.builder()
                .toBankName(command.getToBankName())
                .toBankAccountNumber(command.getToBankAccountNumber())
                .fromBankName(command.getFromBankName())
                .fromBankAccountNumber(command.getFromBankAccountNumber())
                .moneyAmount(command.getMoneyAmount())
                .build();

        gateway.send(createFirmbankingRequestCommand).whenComplete(
                (result,throwable) -> {
                    if(throwable!=null){
                        log.info("throwable = "+throwable);
                    }
                    else {
                        log.info("createFirmbankingRequestCommand completed, Aggregate ID :"+result);
                        // Request Firmbanking DB save

                        FirmbankingRequestJpaEntity requestJpaEntity = requestFirmbankingPort.createFirmbankingRequest(
                                new FirmbankingRequest.fromBankName(command.getFromBankName()),
                                new FirmbankingRequest.fromBankAccountNumber(command.getFromBankAccountNumber()),
                                new FirmbankingRequest.toBankName(command.getToBankName()),
                                new FirmbankingRequest.toBankAccountNumber(command.getToBankAccountNumber()),
                                new FirmbankingRequest.moneyAmount(command.getMoneyAmount()),
                                new FirmbankingRequest.firmbankingStatus(0),
                                new FirmbankingRequest.FirmbankingAggregateIdentifier(result.toString())
                        );

                        FirmbankingResult firmbankingResult = requestExternalFirmbankingPort.requestExternalFirmbanking(new ExternalFirmbankingRequest(
                                command.getFromBankName(),
                                command.getFromBankAccountNumber(),
                                command.getToBankName(),
                                command.getToBankAccountNumber(),
                                command.getMoneyAmount()
                        ));

                        if(firmbankingResult.getResultCode()==0){
                            requestJpaEntity.setFirmbankingStatus(1); // success
                        }
                        else requestJpaEntity.setFirmbankingStatus(2); // fail

                        requestFirmbankingPort.modifyFirmbankingRequest(requestJpaEntity);

                    }
                }
        );
    }

    @Override
    public void updateFirmbankingByEvent(UpdateFirmbankingCommand command) {

        UpdateFirmbankingRequestCommand updateFirmbankingRequestCommand = new UpdateFirmbankingRequestCommand(
                command.getFirmbankingAggregateIdentifier(), command.getFirmbankingStatus()
        );

        gateway.send(updateFirmbankingRequestCommand).whenComplete(
                (result,throwable) -> {
                    if(throwable!=null){
                        log.info("throwable = "+throwable);
                    }
                    else {
                        log.info("updateFirmbankingRequestCommand completed, Aggregate ID :" + result);
                        FirmbankingRequestJpaEntity entity = requestFirmbankingPort.getFirmbankingRequest(
                                new FirmbankingRequest.FirmbankingAggregateIdentifier(command.getFirmbankingAggregateIdentifier())
                        );

                        //status 변경으로 인한 외부 은행과 통신
                        entity.setFirmbankingStatus(command.getFirmbankingStatus());
                        requestFirmbankingPort.modifyFirmbankingRequest(entity);



                    }
                });
    }
}

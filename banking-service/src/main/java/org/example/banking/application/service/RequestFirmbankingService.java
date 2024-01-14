package org.example.banking.application.service;

import lombok.RequiredArgsConstructor;
import org.example.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import org.example.banking.adapter.out.external.bank.FirmbankingResult;
import org.example.banking.adapter.out.persistance.FirmbankingRequestJpaEntity;
import org.example.banking.adapter.out.persistance.FirmbankingRequestMapper;
import org.example.banking.application.in.RequestFirmbankingRequestCommand;
import org.example.banking.application.in.RequestFirmbankingUseCase;
import org.example.banking.application.port.out.RequestExternalFirmbankingPort;
import org.example.banking.application.port.out.RequestFirmbankingPort;
import org.example.banking.domain.FirmbankingRequest;
import org.example.common.UseCase;

import javax.transaction.Transactional;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestFirmbankingService implements RequestFirmbankingUseCase {

    private final RequestExternalFirmbankingPort requestExternalFirmbankingPort;
    private final RequestFirmbankingPort requestFirmbankingPort;
    private final FirmbankingRequestMapper firmbankingRequestMapper;
    @Override
    public FirmbankingRequest requestFirmbanking(RequestFirmbankingRequestCommand command) {

        FirmbankingRequestJpaEntity requestJpaEntity = requestFirmbankingPort.createFirmbankingRequest(
                new FirmbankingRequest.fromBankName(command.getFromBankName()),
                new FirmbankingRequest.fromBankAccountNumber(command.getFromBankAccountNumber()),
                new FirmbankingRequest.toBankName(command.getToBankName()),
                new FirmbankingRequest.toBankAccountNumber(command.getToBankAccountNumber()),
                new FirmbankingRequest.moneyAmount(command.getMoneyAmount()),
                new FirmbankingRequest.firmbankingStatus(0)
        );
        FirmbankingResult result = requestExternalFirmbankingPort.requestExternalFirmbanking(new ExternalFirmbankingRequest(
                command.getFromBankName(),
                command.getFromBankAccountNumber(),
                command.getToBankName(),
                command.getToBankAccountNumber()
        ));
        UUID randomUUID = UUID.randomUUID();
        requestJpaEntity.setUuid(randomUUID.toString());

        if(result.getResultCode()==0){
            requestJpaEntity.setFirmbankingStatus(1); // success
        }
        else requestJpaEntity.setFirmbankingStatus(2); // fail

        return firmbankingRequestMapper.mapToDomainEntity(requestFirmbankingPort.modifyFirmbankingRequest(requestJpaEntity), randomUUID);

    }
}

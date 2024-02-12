package org.example.banking.adapter.out.persistance;

import lombok.RequiredArgsConstructor;
import org.example.banking.application.port.out.RegisterBankAccountPort;
import org.example.banking.application.port.out.RequestFirmbankingPort;
import org.example.banking.domain.FirmbankingRequest;
import org.example.banking.domain.RegisterBankAccount;
import org.example.common.PersistanceAdapter;

import java.util.List;
import java.util.UUID;

@PersistanceAdapter
@RequiredArgsConstructor
public class FirmbankingRequestPersistanceAdapter implements RequestFirmbankingPort {

    private final SpringDataFirmbankingRequestRepository firmbankingRequestRepository;

    @Override
    public FirmbankingRequestJpaEntity createFirmbankingRequest(FirmbankingRequest.fromBankName fromBankName, FirmbankingRequest.fromBankAccountNumber fromBankAccountNumber, FirmbankingRequest.toBankName toBankName, FirmbankingRequest.toBankAccountNumber toBankAccountNumber, FirmbankingRequest.moneyAmount moneyAmount, FirmbankingRequest.firmbankingStatus firmbankingStatus, FirmbankingRequest.FirmbankingAggregateIdentifier firmbankingAggregateIdentifier) {
        FirmbankingRequestJpaEntity entity = firmbankingRequestRepository.save(
                new FirmbankingRequestJpaEntity(
                        fromBankName.getFromBankName(),
                        fromBankAccountNumber.getFromBankAccountNumber(),
                        toBankName.getToBankName(),
                        toBankAccountNumber.getToBankAccountNumber(),
                        moneyAmount.getMoneyAmount(), firmbankingStatus.getFirmbankingStatus(),
                        UUID.randomUUID(),firmbankingAggregateIdentifier.getFirmbankingAggregateIdentifier())
        );
        return entity;
    }

    @Override
    public FirmbankingRequestJpaEntity modifyFirmbankingRequest(FirmbankingRequestJpaEntity entity) {
        return firmbankingRequestRepository.save(entity);
    }

    @Override
    public FirmbankingRequestJpaEntity getFirmbankingRequest(FirmbankingRequest.FirmbankingAggregateIdentifier firmbankingAggregateIdentifier) {
        List<FirmbankingRequestJpaEntity> entityList = firmbankingRequestRepository.findByAggregateIdentifier(firmbankingAggregateIdentifier.getFirmbankingAggregateIdentifier());
        if(entityList.size()>=1)
            return entityList.get(0);
        else return null;
    }
}

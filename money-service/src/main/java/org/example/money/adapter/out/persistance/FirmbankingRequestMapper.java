package org.example.money.adapter.out.persistance;

import org.example.banking.domain.FirmbankingRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FirmbankingRequestMapper {

    public FirmbankingRequest mapToDomainEntity(FirmbankingRequestJpaEntity firmbankingRequestJpaEntity, UUID uuid){
        return FirmbankingRequest.generateFirmbankingRequest(
                new FirmbankingRequest.firmbankingRequestId(firmbankingRequestJpaEntity.getRequestFirmbankingId()),
                new FirmbankingRequest.fromBankName(firmbankingRequestJpaEntity.getFromBankName()),
                new FirmbankingRequest.fromBankAccountNumber(firmbankingRequestJpaEntity.getFromBankAccountNumber()),
                new FirmbankingRequest.toBankName(firmbankingRequestJpaEntity.getToBankName()),
                new FirmbankingRequest.toBankAccountNumber(firmbankingRequestJpaEntity.getToBankAccountNumber()),
                new FirmbankingRequest.moneyAmount(firmbankingRequestJpaEntity.getMoneyAmount()),
                new FirmbankingRequest.firmbankingStatus(firmbankingRequestJpaEntity.getFirmbankingStatus()),
                uuid);
    }
}

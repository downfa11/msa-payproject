package org.example.banking.application.port.out;

import org.example.banking.adapter.out.persistance.FirmbankingRequestJpaEntity;
import org.example.banking.adapter.out.persistance.RegisteredBankAccountJpaEntity;
import org.example.banking.domain.FirmbankingRequest;
import org.example.banking.domain.RegisterBankAccount;

public interface RequestFirmbankingPort {

    FirmbankingRequestJpaEntity createFirmbankingRequest(
            FirmbankingRequest.fromBankName fromBankName,
            FirmbankingRequest.fromBankAccountNumber fromBankAccountNumber,
            FirmbankingRequest.toBankName toBankName,
            FirmbankingRequest.toBankAccountNumber toBankAccountNumber,
            FirmbankingRequest.moneyAmount moneyAmount,
            FirmbankingRequest.firmbankingStatus firmbankingStatus,
            FirmbankingRequest.FirmbankingAggregateIdentifier firmbankingAggregateIdentifier
        );

    FirmbankingRequestJpaEntity modifyFirmbankingRequest(
            FirmbankingRequestJpaEntity entity);

    FirmbankingRequestJpaEntity getFirmbankingRequest(
            FirmbankingRequest.FirmbankingAggregateIdentifier firmbankingAggregateIdentifier
    );
}

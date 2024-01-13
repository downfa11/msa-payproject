package org.example.money.application.port.out;

import org.example.banking.adapter.out.persistance.FirmbankingRequestJpaEntity;
import org.example.banking.domain.FirmbankingRequest;

public interface RequestFirmbankingPort {

    FirmbankingRequestJpaEntity createFirmbankingRequest(
            FirmbankingRequest.fromBankName fromBankName,
            FirmbankingRequest.fromBankAccountNumber fromBankAccountNumber,
            FirmbankingRequest.toBankName toBankName,
            FirmbankingRequest.toBankAccountNumber toBankAccountNumber,
            FirmbankingRequest.moneyAmount moneyAmount,
            FirmbankingRequest.firmbankingStatus firmbankingStatus);

    FirmbankingRequestJpaEntity modifyFirmbankingRequest(
            FirmbankingRequestJpaEntity entity);
}

package org.example.banking.application.port.out;

import org.example.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import org.example.banking.adapter.out.external.bank.FirmbankingResult;
import org.example.banking.adapter.out.persistance.FirmbankingRequestJpaEntity;
import org.example.banking.domain.FirmbankingRequest;

public interface RequestExternalFirmbankingPort {

    FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request);
}

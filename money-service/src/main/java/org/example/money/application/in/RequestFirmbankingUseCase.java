package org.example.money.application.in;

import org.example.banking.domain.FirmbankingRequest;

public interface RequestFirmbankingUseCase {

    FirmbankingRequest requestFirmbanking(RequestFirmbankingRequestCommand command);
}

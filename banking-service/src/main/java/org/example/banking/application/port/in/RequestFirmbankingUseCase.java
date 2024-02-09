package org.example.banking.application.port.in;

import org.example.banking.domain.FirmbankingRequest;

public interface RequestFirmbankingUseCase {

    FirmbankingRequest requestFirmbanking(RequestFirmbankingRequestCommand command);

    void requestFirmbankingByEvent(RequestFirmbankingRequestCommand command);
}

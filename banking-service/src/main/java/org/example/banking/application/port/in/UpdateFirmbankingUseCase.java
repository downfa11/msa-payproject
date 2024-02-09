package org.example.banking.application.port.in;

import org.example.banking.domain.FirmbankingRequest;

public interface UpdateFirmbankingUseCase {
    void updateFirmbankingByEvent(UpdateFirmbankingCommand command);
}

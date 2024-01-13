package org.example.money.application.port.out;

import org.example.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import org.example.banking.adapter.out.external.bank.FirmbankingResult;

public interface RequestExternalFirmbankingPort {

    FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request);
}

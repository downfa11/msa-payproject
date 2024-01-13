package org.example.money.adapter.out.external.bank;

import lombok.RequiredArgsConstructor;
import org.example.banking.adapter.out.persistance.SpringDataRegisteredBankAccountRepository;
import org.example.banking.application.port.out.RequestBankAccountInfoPort;
import org.example.banking.application.port.out.RequestExternalFirmbankingPort;
import org.example.common.PersistanceAdapter;

@PersistanceAdapter
@RequiredArgsConstructor
public class BankAccountAdapter implements RequestBankAccountInfoPort, RequestExternalFirmbankingPort {

    private final SpringDataRegisteredBankAccountRepository registeredBankAccountRepository;
    // 외부 은행에서 http로 은행 계좌 정보를 가져와야함
    @Override
    public BankAccount getBankAccountInfo(GetBankAccountRequest request){
        return new BankAccount(request.getBankName(),request.getBankAccountNumber(),true);
    }

    @Override
    public FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request) {
        // 실제로 외부 은행에서 http로 펌뱅킹 요청하고 결과를  FirmBankingResult로 Parsing
        return new FirmbankingResult(0);
    }
}

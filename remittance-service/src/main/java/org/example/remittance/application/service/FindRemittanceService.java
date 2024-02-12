package org.example.remittance.application.service;

import lombok.RequiredArgsConstructor;
import org.example.common.UseCase;
import org.example.remittance.adapter.out.persistance.RemittanceRequestJpaEntity;
import org.example.remittance.adapter.out.persistance.RemittanceRequestMapper;
import org.example.remittance.application.port.in.FindRemittanceCommand;
import org.example.remittance.application.port.in.FindRemittanceUseCase;
import org.example.remittance.application.port.in.RequestRemittanceCommand;
import org.example.remittance.application.port.in.RequestRemittanceUseCase;
import org.example.remittance.application.port.out.FindRemittancePort;
import org.example.remittance.application.port.out.RequestRemittancePort;
import org.example.remittance.application.port.out.banking.GetBankingPort;
import org.example.remittance.application.port.out.membership.GetMembershipPort;
import org.example.remittance.application.port.out.membership.MembershipStatus;
import org.example.remittance.application.port.out.money.GetMoneyPort;
import org.example.remittance.application.port.out.money.MoneyInfo;
import org.example.remittance.domain.RemittanceRequest;

import javax.transaction.Transactional;
import java.util.List;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindRemittanceService implements FindRemittanceUseCase {

    private final FindRemittancePort findRemittancePort;
    private final RemittanceRequestMapper remittanceRequestMapper;
    private final GetMembershipPort getMembershipPort;
    private final GetMoneyPort getMoneyPort;
    private final GetBankingPort getBankingPort;

    @Override
    public List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command) {
        findRemittancePort.findRemittanceHistory(command);
        return null;
    }
}

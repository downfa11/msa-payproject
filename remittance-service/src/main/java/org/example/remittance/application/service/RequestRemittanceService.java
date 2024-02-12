package org.example.remittance.application.service;

import lombok.RequiredArgsConstructor;
import org.example.common.UseCase;
import org.example.remittance.adapter.out.persistance.RemittanceRequestJpaEntity;
import org.example.remittance.adapter.out.persistance.RemittanceRequestMapper;
import org.example.remittance.application.port.in.RequestRemittanceCommand;
import org.example.remittance.application.port.in.RequestRemittanceUseCase;
import org.example.remittance.application.port.out.banking.GetBankingPort;
import org.example.remittance.application.port.out.membership.GetMembershipPort;
import org.example.remittance.application.port.out.RequestRemittancePort;
import org.example.remittance.application.port.out.membership.MembershipStatus;
import org.example.remittance.application.port.out.money.GetMoneyPort;
import org.example.remittance.application.port.out.money.MoneyInfo;
import org.example.remittance.domain.RemittanceRequest;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestRemittanceService implements RequestRemittanceUseCase {

    private final RequestRemittancePort requestRemittancePort;
    private final RemittanceRequestMapper remittanceRequestMapper;
    private final GetMembershipPort getMembershipPort;
    private final GetMoneyPort getMoneyPort;
    private final GetBankingPort getBankingPort;
    @Override
    public RemittanceRequest requestRemittance(RequestRemittanceCommand command) {

        RemittanceRequestJpaEntity entity = requestRemittancePort.createRemittanceRequestHistory(command);

        // from member check
        MembershipStatus membershipStatus = getMembershipPort.getMembershipStatus(command.getFromMembershipId());
        if(membershipStatus.isValid()) {
            return null;
        }

        // money balance check
        MoneyInfo moneyInfo = getMoneyPort.getMoneyInfo(command.getFromMembershipId());
        if(moneyInfo.getBalance()< command.getAmount()){
            // 만원 단위로 올려보자 Math 함수
            int rechargeAmount = (int) Math.ceil((command.getAmount() - moneyInfo.getBalance()) / 10000.0) * 10000;
            boolean moneyResult = getMoneyPort.requestMoneyRecharging(command.getFromMembershipId(),rechargeAmount);
            if(!moneyResult)
                return null;

        }


        // remittance
        if(command.getRemittanceType()==0){
            boolean remittanceFromResult=false;
            boolean remittanceToResult=false;
            remittanceFromResult = getMoneyPort.requestMoneyDecrease(command.getFromMembershipId(),command.getAmount());
            remittanceToResult = getMoneyPort.requestMoneyIncrease(command.getToMembershipId() ,command.getAmount());
            if(!remittanceFromResult || !remittanceToResult){
                return null;
             }

        } else if(command.getRemittanceType()==1){

            boolean remittanceResult = getBankingPort.requestFirmbanking(command.getToBankName(),command.getToBankAccountNumber(),command.getAmount());
            if(!remittanceResult)
                return null;
        }


        // success history
        entity.setRemittanceStatus("success");
        boolean result = requestRemittancePort.saveRemittanceRequestHistory(entity);
        if(result)
            return remittanceRequestMapper.mapToDomainEntity(entity);
        return null;
    }
}

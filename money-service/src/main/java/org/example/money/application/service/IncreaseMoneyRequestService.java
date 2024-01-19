package org.example.money.application.service;

import lombok.RequiredArgsConstructor;
import org.example.common.UseCase;
import org.example.money.adapter.out.persistance.MemberMoneyJpaEntity;
import org.example.money.adapter.out.persistance.MoneyChangingRequestMapper;
import org.example.money.application.port.in.IncreaseMoneyRequestCommand;
import org.example.money.application.port.in.IncreaseMoneyRequestUseCase;
import org.example.money.application.port.out.IncreaseMoneyPort;
import org.example.money.domain.MemberMoney;
import org.example.money.domain.MoneyChangingRequest;

import javax.transaction.Transactional;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase {

    private final MoneyChangingRequestMapper moneyChangingRequestMapper; // to Entity
    private final IncreaseMoneyPort increaseMoneyPort;


    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {

        MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId()),command.getAmount());

        if(memberMoneyJpaEntity!=null) {
            return moneyChangingRequestMapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
                    new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                    new MoneyChangingRequest.changingType(0),
                    new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                    new MoneyChangingRequest.changingMoneyStatus(1),
                    new MoneyChangingRequest.Uuid(UUID.randomUUID())
            ));
        }
        return null;
    }
}

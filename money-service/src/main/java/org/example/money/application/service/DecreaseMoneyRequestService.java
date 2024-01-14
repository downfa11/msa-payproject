package org.example.money.application.service;

import lombok.RequiredArgsConstructor;
import org.example.common.UseCase;
import org.example.money.adapter.out.persistance.MemberMoneyJpaEntity;
import org.example.money.adapter.out.persistance.MoneyChangingRequestMapper;
import org.example.money.application.in.DecreaseMoneyRequestCommand;
import org.example.money.application.in.DecreaseMoneyRequestUseCase;
import org.example.money.application.in.IncreaseMoneyRequestCommand;
import org.example.money.application.in.IncreaseMoneyRequestUseCase;
import org.example.money.application.port.out.DecreaseMoneyPort;
import org.example.money.application.port.out.IncreaseMoneyPort;
import org.example.money.domain.MemberMoney;
import org.example.money.domain.MoneyChangingRequest;

import javax.transaction.Transactional;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class DecreaseMoneyRequestService implements DecreaseMoneyRequestUseCase {

    private final MoneyChangingRequestMapper moneyChangingRequestMapper; // to Entity
    private final DecreaseMoneyPort decreaseMoneyPort;


    @Override
    public MoneyChangingRequest decreaseMoneyRequest(DecreaseMoneyRequestCommand command) {

        MemberMoneyJpaEntity memberMoneyJpaEntity = decreaseMoneyPort.decreaseMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId()),command.getAmount());

        if(memberMoneyJpaEntity!=null) {
            return moneyChangingRequestMapper.mapToDomainEntity(decreaseMoneyPort.createMoneyChangingRequest(
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

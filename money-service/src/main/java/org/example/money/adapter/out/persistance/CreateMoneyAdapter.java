package org.example.money.adapter.out.persistance;

import lombok.RequiredArgsConstructor;
import org.example.common.PersistanceAdapter;
import org.example.money.application.port.out.CreateMemberMoneyPort;
import org.example.money.application.port.out.DecreaseMoneyPort;
import org.example.money.application.port.out.GetMemberMoneyPort;
import org.example.money.application.port.out.IncreaseMoneyPort;
import org.example.money.domain.MemberMoney;
import org.example.money.domain.MoneyChangingRequest;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@PersistanceAdapter
@RequiredArgsConstructor
public class CreateMoneyAdapter implements CreateMemberMoneyPort, GetMemberMoneyPort {

    private final SpringDataMoneyChangingRepository moneyChangingRepository;
    private final SpringDataMemberMoneyRepository memberMoneyRepository;

    @Override
    public void createMemberMoney(MemberMoney.MembershipId memberId, MemberMoney.MoneyAggregateIdentifier aggregateIdentifier) {
        MemberMoneyJpaEntity entity = new MemberMoneyJpaEntity(
                Long.parseLong(memberId.getMembershipId()),0, aggregateIdentifier.getAggregateIdentifier()
        );

        memberMoneyRepository.save(entity);
    }

    @Override
    public MemberMoneyJpaEntity getMemberMoney(MemberMoney.MembershipId memberId) {
        MemberMoneyJpaEntity entity;
        List<MemberMoneyJpaEntity> entityList = memberMoneyRepository.findByMembershipId(Long.parseLong(memberId.getMembershipId()));
        if(entityList.size()==0){
            entity = new MemberMoneyJpaEntity(
                    Long.parseLong(memberId.getMembershipId()),
                    0,""
            );
            entity = memberMoneyRepository.save(entity);
            return entity;
        }

        return entityList.get(0);
    }
}

package org.example.money.application.port.out;

import org.example.money.adapter.out.persistance.MemberMoneyJpaEntity;
import org.example.money.adapter.out.persistance.MoneyChangingRequestJpaEntity;
import org.example.money.domain.MemberMoney;
import org.example.money.domain.MoneyChangingRequest;

import java.lang.reflect.Member;

public interface IncreaseMoneyPort {

    MoneyChangingRequestJpaEntity createMoneyChangingRequest(
            MoneyChangingRequest.TargetMembershipId targetMembershipId,
            MoneyChangingRequest.changingType changingType,
            MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount,
            MoneyChangingRequest.changingMoneyStatus changingMoneyStatus,
            MoneyChangingRequest.Uuid uuid
    );

    MemberMoneyJpaEntity increaseMoney(
            MemberMoney.MembershipId membershipId,
            int increaseMoneyAmount

    );
}

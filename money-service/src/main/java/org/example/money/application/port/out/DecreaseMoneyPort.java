package org.example.money.application.port.out;

import org.example.money.adapter.out.persistance.MemberMoneyJpaEntity;
import org.example.money.adapter.out.persistance.MoneyChangingRequestJpaEntity;
import org.example.money.domain.MemberMoney;
import org.example.money.domain.MoneyChangingRequest;

public interface DecreaseMoneyPort {

    MoneyChangingRequestJpaEntity createMoneyChangingRequest(
            MoneyChangingRequest.TargetMembershipId targetMembershipId,
            MoneyChangingRequest.changingType changingType,
            MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount,
            MoneyChangingRequest.changingMoneyStatus changingMoneyStatus,
            MoneyChangingRequest.Uuid uuid
    );

    MemberMoneyJpaEntity decreaseMoney(
            MemberMoney.MembershipId membershipId,
            int increaseMoneyAmount

    );
}

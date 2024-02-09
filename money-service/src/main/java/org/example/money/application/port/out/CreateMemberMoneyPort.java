package org.example.money.application.port.out;

import org.example.money.domain.MemberMoney;

public interface CreateMemberMoneyPort {
    void createMemberMoney(
            MemberMoney.MembershipId memberId,
            MemberMoney.MoneyAggregateIdentifier aggregateIdentifier
    );

}

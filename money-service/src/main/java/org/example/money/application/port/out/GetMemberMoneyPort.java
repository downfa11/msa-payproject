package org.example.money.application.port.out;

import org.example.money.adapter.out.persistance.MemberMoneyJpaEntity;
import org.example.money.domain.MemberMoney;

public interface GetMemberMoneyPort {
    MemberMoneyJpaEntity getMemberMoney(
            MemberMoney.MembershipId memberId
    );

}

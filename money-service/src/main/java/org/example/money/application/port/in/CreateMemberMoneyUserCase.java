package org.example.money.application.port.in;

import org.example.money.adapter.axon.common.IncreaseMemberMoneyCommand;

public interface CreateMemberMoneyUserCase {
    void createMemberMoney(CreateMemberMoneyCommand command);

    void increaseMoneyRequestByEvent(IncreaseMoneyRequestCommand command);

}


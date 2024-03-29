package org.example.money.application.port.in;

import org.example.money.adapter.in.web.IncreaseMoneyChangingRequest;
import org.example.money.domain.MoneyChangingRequest;

public interface IncreaseMoneyRequestUseCase {

    MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);
    MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command);
}

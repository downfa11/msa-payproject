package org.example.money.application.in;

import org.example.money.domain.MoneyChangingRequest;

public interface DecreaseMoneyRequestUseCase {

    MoneyChangingRequest decreaseMoneyRequest(DecreaseMoneyRequestCommand command);
}

package org.example.money.application.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.common.SelfValidating;

import javax.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)

public class DecreaseMoneyRequestCommand extends SelfValidating<DecreaseMoneyRequestCommand> {
// CRUD에서 c,u,d를 위한 기능을 위한 Command
@NotNull
private final String targetMembershipId;
    @NotNull
    private final int Amount;

    public DecreaseMoneyRequestCommand(String targetMembershipId, int amount) {
        this.targetMembershipId = targetMembershipId;
        this.Amount = amount;

        this.validateSelf();
    }
}

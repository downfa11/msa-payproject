package org.example.money.adapter.axon.event;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.example.common.SelfValidating;

import javax.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)

public class IncreaseMemberMoneyEvent extends SelfValidating<IncreaseMemberMoneyEvent> {

    private String aggregateIdentifier;
    private String membershipId;
    private int amount;

    public IncreaseMemberMoneyEvent(String aggregateIdentifier, String membershipId, int amount) {
        this.aggregateIdentifier = aggregateIdentifier;
        this.membershipId = membershipId;
        this.amount = amount;
    }

    public IncreaseMemberMoneyEvent() {
    }
}
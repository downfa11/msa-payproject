package org.example.money.adapter.axon.common;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.example.common.SelfValidating;

import javax.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)

public class IncreaseMemberMoneyCommand extends SelfValidating<MemberMoneyCreateCommand> {

    @NotNull
    private String membershipId;
    @NotNull
    private int amount;
    @NotNull
    @TargetAggregateIdentifier
    private String aggregateIdentifier;

}

package org.example.money.adapter.axon.common;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.common.SelfValidating;

import javax.validation.constraints.NotNull;


@Builder
@Data
@EqualsAndHashCode(callSuper = false)

public class MemberMoneyCreateCommand extends SelfValidating<MemberMoneyCreateCommand> {

    @NotNull
    private String membershipId;


    public MemberMoneyCreateCommand(@NotNull String MembershipId) {
        this.membershipId = MembershipId;
        this.validateSelf();
    }

    public MemberMoneyCreateCommand() {
    }
}
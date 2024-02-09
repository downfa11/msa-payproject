package org.example.money.adapter.axon.event;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.common.SelfValidating;

import javax.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)

public class MemberMoneyCreateEvent extends SelfValidating<MemberMoneyCreateEvent> {

    @NotNull
    private String membershipId;


    public MemberMoneyCreateEvent(@NotNull String MembershipId) {
        this.membershipId = MembershipId;
        this.validateSelf();
    }

    public MemberMoneyCreateEvent() {
    }
}
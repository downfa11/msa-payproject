package org.example.money.adapter.axon.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RechargingRequestCreateCommand {
    @TargetAggregateIdentifier
    private String aggregateIdentifier;

    private String rechargingRequestId;
    private String membershipId;
    private int amount;
}

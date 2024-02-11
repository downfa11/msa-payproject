package org.example.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckRegisteredBankAccountCommand {

    @TargetAggregateIdentifier
    private String aggregateIdentifier;

    private String rechargeRequestId;

    private String membershipId;

    private String checkRegisteredBankAccountId;
    private String bankName;
    private String bankAccountNumber;
    private int amount;
}

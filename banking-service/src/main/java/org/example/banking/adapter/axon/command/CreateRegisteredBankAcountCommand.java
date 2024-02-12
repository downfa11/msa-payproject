package org.example.banking.adapter.axon.command;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CreateRegisteredBankAcountCommand {

    private String membershipId;
    private String bankName;
    private String bankAccountNumber;
}

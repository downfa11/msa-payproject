package org.example.banking.adapter.axon.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRegisteredBankAcountEvent {
    private String membershipId;
    private String bankName;
    private String bankAccountNumber;
}

package org.example.banking.adapter.axon.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRegisteredBankAcountEvent {
    private String membershipId;
    private String bankName;
    private String bankAccountNumber;
}

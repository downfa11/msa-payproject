package org.example.remittance.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRemittanceRequest {
    private String fromMembershipId;
    private String toMembershipId;
    private String tobankName;
    private String toBankAccountNumber;
    private int remittanceType; // 0: memberhip, 1: bank
    private int amount;
}

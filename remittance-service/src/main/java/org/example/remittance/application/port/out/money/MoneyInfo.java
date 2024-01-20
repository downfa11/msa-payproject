package org.example.remittance.application.port.out.money;

import lombok.Data;

@Data
public class MoneyInfo {
    private String membershipId;

    private int balance;
}

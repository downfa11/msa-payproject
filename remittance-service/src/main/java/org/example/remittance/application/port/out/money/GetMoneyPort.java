package org.example.remittance.application.port.out.money;

public interface GetMoneyPort {
    public MoneyInfo getMoneyInfo(String moneyId);

    boolean requestMoneyRecharging(String membershipId, int amount);
    boolean requestMoneyIncrease(String membershipId, int amount);
    boolean requestMoneyDecrease(String membershipId, int amount);
}

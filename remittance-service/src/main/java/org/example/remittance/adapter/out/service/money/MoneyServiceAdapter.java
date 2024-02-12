package org.example.remittance.adapter.out.service.money;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.common.CommonHttpClient;
import org.example.remittance.adapter.out.service.membership.Membership;
import org.example.remittance.application.port.out.membership.GetMembershipPort;
import org.example.remittance.application.port.out.membership.MembershipStatus;
import org.example.remittance.application.port.out.money.GetMoneyPort;
import org.example.remittance.application.port.out.money.MoneyInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class MoneyServiceAdapter implements GetMoneyPort {
   private final CommonHttpClient commonHttpClient;
   private final String moneyServiceUrl;

   public MoneyServiceAdapter(CommonHttpClient commonHttpClient,
                              @Value("${service.money.url}") String moneyServiceUrl){
       this.commonHttpClient=commonHttpClient;
       this.moneyServiceUrl=moneyServiceUrl;
   }


    @Override
    public MoneyInfo getMoneyInfo(String moneyId) {
        return null;
    }

    @Override
    public boolean requestMoneyRecharging(String membershipId, int amount) {
        return false;
    }

    @Override
    public boolean requestMoneyIncrease(String membershipId, int amount) {
        return false;
    }

    @Override
    public boolean requestMoneyDecrease(String membershipId, int amount) {
        return false;
    }
}

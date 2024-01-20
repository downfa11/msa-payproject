package org.example.remittance.adapter.out.service.banking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.common.CommonHttpClient;
import org.example.remittance.adapter.out.service.membership.Membership;
import org.example.remittance.application.port.out.banking.BankingInfo;
import org.example.remittance.application.port.out.banking.GetBankingPort;
import org.example.remittance.application.port.out.membership.GetMembershipPort;
import org.example.remittance.application.port.out.membership.MembershipStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class BankingServiceAdapter implements GetBankingPort {
   private final CommonHttpClient commonHttpClient;
   private final String bankingServiceUrl;

   public BankingServiceAdapter(CommonHttpClient commonHttpClient,
                                @Value("${service.banking.url}") String bankingServiceUrl) {
       this.commonHttpClient = commonHttpClient;
       this.bankingServiceUrl = bankingServiceUrl;
   }

    @Override
    public BankingInfo getMembershipBankingInfo(String bankName, String bankAccountName) {
      return null;
    }

    @Override
    public boolean requestFirmbanking(String bankName, String bankAccountName, int amount) {
        return false;
    }
}

package org.example.remittance.adapter.out.service.membership;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.remittance.application.port.out.membership.GetMembershipPort;
import org.example.common.CommonHttpClient;
import org.example.remittance.application.port.out.membership.MembershipStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class MembershipServiceAdapter implements GetMembershipPort {
   private final CommonHttpClient commonHttpClient;
   private final String membershipServiceUrl;

   public MembershipServiceAdapter(CommonHttpClient commonHttpClient,
                                   @Value("${service.membership.url}") String membershipServiceUrl){
       this.commonHttpClient=commonHttpClient;
       this.membershipServiceUrl=membershipServiceUrl;
   }

    @Override
    public MembershipStatus getMembershipStatus(String membershipId) {
        // 실제론 http Call라서 http client 필요함
        // -> common-CommonHttpClient로 대체

        String url = String.join("/",membershipServiceUrl, "membership",membershipId);
        try{
            String jsonResponse = commonHttpClient.sendGetRequest(url).body();

            ObjectMapper mapper = new ObjectMapper();
            Membership membership = mapper.readValue(jsonResponse, Membership.class);

            if(membership.isValid())
                return new MembershipStatus(membership.getMembershipId(),true);
            else {
                return new MembershipStatus(membership.getMembershipId(),false);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}

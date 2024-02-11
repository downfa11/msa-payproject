package org.example.remittance.adapter.out.persistance;
import org.example.remittance.domain.RemittanceRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RemittanceRequestMapper {

    public RemittanceRequest mapToDomainEntity(RemittanceRequestJpaEntity remittanceRequestJpaEntity){
        return RemittanceRequest.generateRemittanceRequest(
                new RemittanceRequest.RemittanceRequestId(remittanceRequestJpaEntity.getFromMembershipId()),
                new RemittanceRequest.RemittanceFromMembershipId(remittanceRequestJpaEntity.getFromMembershipId()),
                new RemittanceRequest.ToBankName(remittanceRequestJpaEntity.getToBankName()),
                new RemittanceRequest.ToBankAccountNumber(remittanceRequestJpaEntity.getToBankAccountNumber()),
                new RemittanceRequest.RemittanceType(remittanceRequestJpaEntity.getRemittanceType()),
                new RemittanceRequest.Amount(remittanceRequestJpaEntity.getAmount()),
                new RemittanceRequest.RemittanceStatus(remittanceRequestJpaEntity.getRemittanceStatus())
                );

    }
}
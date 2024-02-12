package org.example.remittance.adapter.out.persistance;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name ="request_remittance")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RemittanceRequestJpaEntity {

    @Id
    @GeneratedValue
    private Long remittanceRequestId;

    private String fromMembershipId;
    private String toMembershipId;
    private String toBankName;
    private String toBankAccountNumber;
    private int amount;
    private int remittanceType; // 0:membership, 1:bank
    private String remittanceStatus;

}

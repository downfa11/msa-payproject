package org.example.money.adapter.out.persistance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name ="money_changing_request")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberMoneyJpaEntity {

    @Id
    @GeneratedValue
    private Long memberMoneyId;

    private String membershipId;
    private int balance;

    public MemberMoneyJpaEntity(String membershipId, int balance) {
        this.membershipId = membershipId;
        this.balance = balance;
    }
}

package org.example.banking.adapter.out.persistance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="registered_bank_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredBankAccountJpaEntity {

    @Id
    @GeneratedValue
    private Long RegisteredBankAccountId;

    private String membershipId;
    private String bankName;
    private String bankAccountNumber;

    private boolean linkedStatusIsValid;
    private String aggregateIdentifier;
    public RegisteredBankAccountJpaEntity(String membershipId, String bankName, String bankAccountNumber, boolean linkedStatusIsValid, String aggregateIdentifier){
        this.membershipId = membershipId;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.linkedStatusIsValid = linkedStatusIsValid;
        this.aggregateIdentifier = aggregateIdentifier;
    }

    @Override
    public String toString() {
        return "RegisteredBankAccountJpaEntity{" +
                "registeredBankAccountId=" + RegisteredBankAccountId +
                ", membershipId='" + membershipId + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", linkedStatusIsValid=" + linkedStatusIsValid +
                ", aggregateIdentifier='" + aggregateIdentifier + '\'' +
                '}';
    }
}
package org.example.money.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMoney {
    @Getter private final String memberMoneyId;
    @Getter private final String membershipId;
    @Getter private final int balance;// 잔액
    @Getter private final int linkedBankAccount;

    public static MemberMoney generateMoneyChangingRequest(
            MemberMoneyId memberMoneyId,
            MembershipId membershipId,
            Balance balance,
            LinkedBankAccount linkedBankAccount
    ){
        return new MemberMoney(
                memberMoneyId.getMemberMoneyId(),
                membershipId.membershipId,
                balance.getBalance(),
                linkedBankAccount.getLinkedBankAccount()
        );
    }
    @Value
    public static class changingType {
        public changingType(int value){
            this.changingType = value;
        }

        int changingType;
    }

    @Value
    public static class MemberMoneyId {
        public MemberMoneyId(String value){
            this.memberMoneyId = value;
        }

        String memberMoneyId;
    }

    @Value
    public static class MembershipId {
        public MembershipId(String value){
            this.membershipId = value;
        }

        String membershipId;
    }

    @Value
    public static class Balance {
        public Balance(int value){
            this.balance = value;
        }

        int balance;
    }

    @Value
    public static class LinkedBankAccount {
        public LinkedBankAccount(int value){
            this.linkedBankAccount = value;
        }

        int linkedBankAccount;
    }
}

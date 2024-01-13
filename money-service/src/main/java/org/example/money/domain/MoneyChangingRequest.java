package org.example.money.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MoneyChangingRequest {
    @Getter private final String moneyChaningRequestId;
    @Getter private final String targetMembershipId;
    @Getter private final ChangingType changingType;// 0:요청, 1:완료, 2:실패
    enum ChangingType{
        INCEASING,
        DECREASING
    }
    @Getter private final int changingMoneyAmount;
    @Getter private final ChangingMoneyStatus changingMoneyStatus;

    enum ChangingMoneyStatus{
        REQUESTED,
        SUCCEDDED,
        FAILED,
        CANCELED
    }
    @Getter private final String uuid;
    @Getter private final Date createAt;

    public static MoneyChangingRequest moneyChangingRequest(
            MoneyChaningRequestId moneyChaningRequestId,
            TargetMembershipId targetMembershipId,
            changingType changingType,
            ChangingMoneyAmount changingMoneyAmount,
            changingMoneyStatus changingMoneyStatus,
            Uuid uuid
    ){
        return new MoneyChangingRequest(
                moneyChaningRequestId.getMoneyChaningRequestId(),
                targetMembershipId.getTargetMembershipId(),
                changingType.getChangingType(),
                Integer.parseInt(changingMoneyAmount.getChangingMoneyAmount()),
                changingMoneyStatus.getChangingMoneyStatus(),
                uuid.getUuid(),
                new Date()
        );
    }
    @Value
    public static class MoneyChaningRequestId {
        public MoneyChaningRequestId(String value){
            this.moneyChaningRequestId = value;
        }

        String moneyChaningRequestId;
    }

    @Value
    public static class TargetMembershipId {
        public TargetMembershipId(String value){
            this.targetMembershipId = value;
        }

        String targetMembershipId;
    }

    @Value
    public static class changingType {
        public changingType(ChangingType value){
            this.changingType = value;
        }

        ChangingType changingType;
    }

    @Value
    public static class ChangingMoneyAmount {
        public ChangingMoneyAmount(String value){
            this.changingMoneyAmount = value;
        }

        String changingMoneyAmount;
    }

    @Value
    public static class changingMoneyStatus {
        public changingMoneyStatus(ChangingMoneyStatus value){
            this.changingMoneyStatus = value;
        }

        ChangingMoneyStatus changingMoneyStatus;
    }

    @Value
    public static class Uuid {
        public Uuid(UUID value){
            this.uuid = value.toString();
        }

        String uuid;
    }

    @Value
    public static class CreateAt {
        public CreateAt(Date value){
            this.createAt = value;
        }

        Date createAt;
    }
}

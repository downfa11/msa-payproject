package org.example.money.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MoneyChangingRequest {
    @Getter private final String moneyChangingRequestId;
    @Getter private final String targetMembershipId;
    @Getter private final int changingType;// 0:증액, 1:감액

    @Getter private final int changingMoneyAmount;
    @Getter private final int changingMoneyStatus;// 0:요청, 1:완료, 2:실패
    @Getter private final String uuid;
    @Getter private final Date createAt;

    public static MoneyChangingRequest generateMoneyChangingRequest(
            MoneyChangingRequestId moneyChangingRequestId,
            TargetMembershipId targetMembershipId,
            changingType changingType,
            ChangingMoneyAmount changingMoneyAmount,
            changingMoneyStatus changingMoneyStatus,
            Uuid uuid
    ){
        return new MoneyChangingRequest(
                moneyChangingRequestId.getMoneyChangingRequestId(),
                targetMembershipId.getTargetMembershipId(),
                changingType.getChangingType(),
                changingMoneyAmount.getChangingMoneyAmount(),
                changingMoneyStatus.getChangingMoneyStatus(),
                uuid.getUuid(),
                new Date()
        );
    }
    @Value
    public static class MoneyChangingRequestId {
        public MoneyChangingRequestId(String value){
            this.moneyChangingRequestId = value;
        }

        String moneyChangingRequestId;
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
        public changingType(int value){
            this.changingType = value;
        }

        int changingType;
    }

    @Value
    public static class ChangingMoneyAmount {
        public ChangingMoneyAmount(int value){
            this.changingMoneyAmount = value;
        }

        int changingMoneyAmount;
    }

    @Value
    public static class changingMoneyStatus {
        public changingMoneyStatus(int value){
            this.changingMoneyStatus = value;
        }

        int changingMoneyStatus;
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

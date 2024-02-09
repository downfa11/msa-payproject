package org.example.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FirmbankingRequest {
    @Getter private final Long firmbankingRequestId;
    @Getter private final String fromBankName;
    @Getter private final String fromBankAccountNumber;
    @Getter private final String toBankName;
    @Getter private final String toBankAccountNumber;
    @Getter private final int moneyAmount;
    @Getter private final int firmbankingStatus; // 0:요청, 1:완료, 2:실패

    @Getter private final UUID uuid;
    @Getter private final String aggregateIdentifier;
    public static FirmbankingRequest generateFirmbankingRequest(
            FirmbankingRequest.firmbankingRequestId firmbankingRequestId,
            FirmbankingRequest.fromBankName fromBankName,
        FirmbankingRequest.fromBankAccountNumber fromBankAccountNumber,
        FirmbankingRequest.toBankName toBankName,
        FirmbankingRequest.toBankAccountNumber toBankAccountNumber,
        FirmbankingRequest.moneyAmount moneyAmount,
        FirmbankingRequest.firmbankingStatus firmbankingStatus,
            UUID uuid,
            FirmbankingAggregateIdentifier firmbankingAggregateIdentifier
        ){

        return new FirmbankingRequest(
                firmbankingRequestId.getFirmbankingRequestId(),
                fromBankName.getFromBankName(),
                fromBankAccountNumber.getFromBankAccountNumber(),
                toBankName.getToBankName(),
                toBankAccountNumber.getToBankAccountNumber(),
                moneyAmount.getMoneyAmount(),
                firmbankingStatus.getFirmbankingStatus(),
                uuid, firmbankingAggregateIdentifier.getFirmbankingAggregateIdentifier()
                );
    }
    @Value
    public static class firmbankingRequestId {
        public firmbankingRequestId(Long value){ this.firmbankingRequestId = value;}

        Long firmbankingRequestId;
    }
    @Value
    public static class fromBankName {
        public fromBankName(String value){
            this.fromBankName = value;
        }

        String fromBankName;
    }

    @Value
    public static class fromBankAccountNumber {
        public fromBankAccountNumber(String value){
            this.fromBankAccountNumber = value;
        }

        String fromBankAccountNumber;
    }

    @Value
    public static class toBankName {
        public toBankName(String value){
            this.toBankName = value;
        }

        String toBankName;
    }

    @Value
    public static class toBankAccountNumber {
        public toBankAccountNumber(String value){
            this.toBankAccountNumber = value;
        }

        String toBankAccountNumber;
    }

    @Value
    public static class moneyAmount {
        public moneyAmount(int value){
            this.moneyAmount = value;
        }

        int moneyAmount;
    }

    @Value
    public static class firmbankingStatus {
        public firmbankingStatus(int value){
            this.firmbankingStatus = value;
        }

        int firmbankingStatus;
    }

    @Value
    public static class FirmbankingAggregateIdentifier {
        public FirmbankingAggregateIdentifier(String value){
            this.firmbankingAggregateIdentifier = value;
        }

        String firmbankingAggregateIdentifier;
    }
}

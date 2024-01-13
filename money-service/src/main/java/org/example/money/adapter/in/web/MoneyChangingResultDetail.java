package org.example.money.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.money.domain.MoneyChangingRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyChangingResultDetail {
    private String moneyChangingRequestId;
    private MoneyChangingType moneyChangingType; // enum 0:증액, 1:감액
    private MoneyChangingResultStatus moneyChangingResultStatus;
    private int amount;

    public static MoenyChangingResultDetail moenyChangingResultDetail(MoneyChangingResult moneyChangingResult){

    }
}
enum MoneyChangingType{
    INCREASE,
    DECREASE
}

enum MoneyChangingResultStatus{
    SUCCEDDED,
    FAILED_NOT_ENOUGH_MONEY,
    FAILED_NOT_EXIST_MEMBERSHIP,
    FAILED_NOT_EXIST_MONEY_CHANGING_REQUEST
}
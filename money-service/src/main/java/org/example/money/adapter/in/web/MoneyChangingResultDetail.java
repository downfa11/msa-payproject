package org.example.money.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyChangingResultDetail {
    private String moneyChangingRequestId;
    private int moneyChangingType; // enum 0:증액, 1:감액
    private int moneyChangingResultStatus; // 0:성공, 실패, 잔액부족, 멤버십 없음, 변액 요청 없음
    private int amount;

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
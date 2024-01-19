package org.example.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RechargingMoneyTask {
    private String taskID;
    private String taskName;
    private String membershipId;
    private List<SubTask> subTaskList;
    private String toBankName;
    private String toBankAccountNumber;
    private int moneyAmount;
}

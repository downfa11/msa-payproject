package org.example.money.application.service;

import lombok.RequiredArgsConstructor;
import org.example.common.CountDownLatchManager;
import org.example.common.RechargingMoneyTask;
import org.example.common.SubTask;
import org.example.common.UseCase;
import org.example.money.adapter.out.persistance.MemberMoneyJpaEntity;
import org.example.money.adapter.out.persistance.MoneyChangingRequestMapper;
import org.example.money.application.port.in.IncreaseMoneyRequestCommand;
import org.example.money.application.port.in.IncreaseMoneyRequestUseCase;
import org.example.money.application.port.out.GetMembershipPort;
import org.example.money.application.port.out.IncreaseMoneyPort;
import org.example.money.application.port.out.SendRechargingMoneyTaskPort;
import org.example.money.domain.MemberMoney;
import org.example.money.domain.MoneyChangingRequest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase {

    private final MoneyChangingRequestMapper moneyChangingRequestMapper; // to Entity
    private final IncreaseMoneyPort increaseMoneyPort;
    private final GetMembershipPort membershipPort;
    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;
    private final CountDownLatchManager countDownLatchManager;

    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {

        // 고객 정보가 정상인지 membership-service에 Sync로 요청
        membershipPort.getMembership(command.getTargetMembershipId());

        MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId()),command.getAmount());

        if(memberMoneyJpaEntity!=null) {
            return moneyChangingRequestMapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
                    new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                    new MoneyChangingRequest.changingType(1),
                    new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                    new MoneyChangingRequest.changingMoneyStatus(1),
                    new MoneyChangingRequest.Uuid(UUID.randomUUID())
            ));
        }
        return null;
    }

    @Override
    public MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command) {

        SubTask validMemberTask = SubTask.builder().
                subTaskName("validMemberTask : "+ "Membership validation")
                .membersrhipId(command.getTargetMembershipId())
                .taskType("membership")
                .status("ready").build();

        SubTask validAccountTask = SubTask.builder().
                subTaskName("validAccountTask : "+ "Account validation")
                .membersrhipId(command.getTargetMembershipId())
                .taskType("banking")
                .status("ready").build();

        List<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(validMemberTask);
        subTaskList.add(validAccountTask);

        RechargingMoneyTask task = RechargingMoneyTask.builder()
                .taskID(UUID.randomUUID().toString())
                .taskName("Increase Money Task.")
                .subTaskList(subTaskList)
                .moneyAmount(command.getAmount())
                .membershipId(command.getTargetMembershipId())
                .toBankName("fastcampus")
                .build();
        // membershipId로 Validation을 하기 위한 Task
        sendRechargingMoneyTaskPort.sendRechargingMoneyTaskPort(task);
        countDownLatchManager.addCountDownLatch(task.getTaskID());

        // Firmbanking 무조건 ok 받는다고 가정
        try {
            countDownLatchManager.getCountDownLatch(task.getTaskID()).await();
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        // 기다리다가 Kafka Cluster로부터 Consume해온 결과로 동작 처리


        String result = countDownLatchManager.getDataForKey(task.getTaskID());
        if(result.equals("success")){
            MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                    new MemberMoney.MembershipId(command.getTargetMembershipId()),command.getAmount());

            if(memberMoneyJpaEntity!=null) {
                return moneyChangingRequestMapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
                        new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                        new MoneyChangingRequest.changingType(1),
                        new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                        new MoneyChangingRequest.changingMoneyStatus(1),
                        new MoneyChangingRequest.Uuid(UUID.randomUUID())
                ));
            }
            else return null;
        }

        return null;
    }
}

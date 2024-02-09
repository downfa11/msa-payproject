package org.example.money.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.money.adapter.axon.common.IncreaseMemberMoneyCommand;
import org.example.money.adapter.axon.common.MemberMoneyCreateCommand;
import org.example.money.adapter.out.persistance.MemberMoneyJpaEntity;
import org.example.money.application.port.in.CreateMemberMoneyCommand;
import org.example.money.application.port.in.CreateMemberMoneyUserCase;
import org.example.money.application.port.in.IncreaseMoneyRequestCommand;
import org.example.money.application.port.out.CreateMemberMoneyPort;
import org.example.money.application.port.out.GetMemberMoneyPort;
import org.example.money.application.port.out.IncreaseMoneyPort;
import org.example.money.domain.MemberMoney;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class CreateMoneyRequestService implements CreateMemberMoneyUserCase {

    private final CreateMemberMoneyPort createMemberMoneyPort;
    private final GetMemberMoneyPort getMemberMoneyPort;
    private final IncreaseMoneyPort increaseMoneyPort;
    private final CommandGateway commandGateway;

    @Override
    public void createMemberMoney(CreateMemberMoneyCommand command) {
        MemberMoneyCreateCommand axonCommand = new MemberMoneyCreateCommand(command.getMembershipId());
        commandGateway.send(axonCommand).whenComplete((result,throwable) -> {
            if(throwable !=null){
                log.info("throwable = "+throwable);
                throw new RuntimeException(throwable);
            } else {
                log.info("result = "+result);
                createMemberMoneyPort.createMemberMoney(
                        new MemberMoney.MembershipId(command.getMembershipId()),
                                new MemberMoney.MoneyAggregateIdentifier(result.toString())
                );
            }
        });
        //
    }

    @Override
    public void increaseMoneyRequestByEvent(IncreaseMoneyRequestCommand command) {
        // command를 만들어서 axon-server로 보낼거야
        MemberMoneyJpaEntity memberMoneyJpaEntity = getMemberMoneyPort.getMemberMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId())
        );

        String aggregateIdentifier = memberMoneyJpaEntity.getAggregateIdentifier();

        commandGateway.send(IncreaseMemberMoneyCommand.builder()
                        .aggregateIdentifier(aggregateIdentifier)
                        .membershipId(command.getTargetMembershipId())
                        .amount(command.getAmount()).build())
                .whenComplete(
                        (result,throwable) -> {
                            if(throwable !=null){
                                log.info("throwable = "+throwable);
                                throw new RuntimeException(throwable);
                            } else {
                                log.info("increaseMoney result = "+result);

                                increaseMoneyPort.increaseMoney(
                                        new MemberMoney.MembershipId(command.getTargetMembershipId())
                                        ,command.getAmount()
                                );
                            }
                        });
    }
}

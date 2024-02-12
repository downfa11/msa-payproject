package org.example.money.adapter.axon.aggregate;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.money.adapter.axon.common.IncreaseMemberMoneyCommand;
import org.example.money.adapter.axon.common.MemberMoneyCreateCommand;
import org.example.money.adapter.axon.common.RechargingRequestCreateCommand;
import org.example.money.adapter.axon.event.IncreaseMemberMoneyEvent;
import org.example.money.adapter.axon.event.MemberMoneyCreateEvent;
import org.example.money.adapter.axon.event.RechargingRequestCreatedEvent;
import org.example.money.application.port.out.GetRegisterBankAccountPort;
import org.example.money.application.port.out.RegisteredBankAccountAggregateIdentifier;

import javax.validation.constraints.NotNull;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate()
@Data
@Slf4j
public class MemberMoneyAggregate {
    @AggregateIdentifier
    private String id;

    private Long membershipId;
    private int balance;

    @CommandHandler
    public MemberMoneyAggregate(MemberMoneyCreateCommand command){
        log.info("MemberMoneyCreateCommand Handler");

        apply(new MemberMoneyCreateEvent(command.getMembershipId()));
    }

    @CommandHandler
    public String handle(@NotNull IncreaseMemberMoneyCommand command){
        log.info("IncreaseMemberMoneyCommand Handler");
        id = command.getAggregateIdentifier();
        apply(new IncreaseMemberMoneyEvent(id, command.getMembershipId(),command.getAmount()));
        return id;
    }

    @CommandHandler
    public void handle(@NotNull RechargingRequestCreateCommand command, GetRegisterBankAccountPort getRegisterBankAccountPort){
        log.info("RechargingRequestCreateCommand Handler");
        id = command.getAggregateIdentifier();
        RegisteredBankAccountAggregateIdentifier registeredBankAccountAggregateIdentifier = getRegisterBankAccountPort.getRegisteredBankAccount(command.getMembershipId());

        apply(new RechargingRequestCreatedEvent(
                command.getRechargingRequestId(),
                command.getMembershipId(),
                command.getAmount(),
                registeredBankAccountAggregateIdentifier.getAggregateIdentifier(),
                registeredBankAccountAggregateIdentifier.getBankName(),
                registeredBankAccountAggregateIdentifier.getBankAccountNumber()
        )); // Saga start
    }

    @EventSourcingHandler
    public void on(MemberMoneyCreateEvent event){
        log.info("MemberMoneyCreateEvent Sourcing Handler");
        id = UUID.randomUUID().toString();
        membershipId = Long.parseLong(event.getMembershipId());
        balance=0;
    }

    @EventSourcingHandler
    public void on(IncreaseMemberMoneyEvent event){
        log.info("IncreaseMemberMoneyEvent Sourcing Handler");
        id = event.getAggregateIdentifier();
        membershipId = Long.parseLong(event.getMembershipId());
        balance=event.getAmount();
    }

    public MemberMoneyAggregate() {
    }
}

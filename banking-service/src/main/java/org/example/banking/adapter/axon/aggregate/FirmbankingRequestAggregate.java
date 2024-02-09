package org.example.banking.adapter.axon.aggregate;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.banking.adapter.axon.command.CreateFirmbankingRequestCommand;
import org.example.banking.adapter.axon.command.UpdateFirmbankingRequestCommand;
import org.example.banking.adapter.axon.event.FirmbankingRequestCreatedEvent;
import org.example.banking.adapter.axon.event.FirmbankingRequestUpdatedEvent;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate()
@Data
@Slf4j
public class FirmbankingRequestAggregate {
    @TargetAggregateIdentifier
    private String id;

    private String fromBankName;
    private String fromBankAccountNumber;

    private String toBankName;
    private String toBankAccountNumber;

    private int moneyAmount;

    private int firmbankingStatus;

    @CommandHandler
    public FirmbankingRequestAggregate(CreateFirmbankingRequestCommand command){
        log.info("CreateFirmbankingRequestCommand Handler");
        apply(new FirmbankingRequestCreatedEvent(command.getFromBankName(),command.getFromBankAccountNumber(),command.getToBankName(),command.getToBankAccountNumber(),command.getMoneyAmount()));
    }

    @CommandHandler
    public String handle(UpdateFirmbankingRequestCommand command){
        log.info("UpdateFirmbankingRequestCommand Handler");

        id = command.getAggregateIdentifier();
        apply(new FirmbankingRequestUpdatedEvent(command.getFirmbankingStatus()));
        return id;
    }

    @EventSourcingHandler
    public void on(FirmbankingRequestCreatedEvent event){
        log.info("FirmbankingRequestCreatedEvent Sourcing Handler");
        id = UUID.randomUUID().toString();
        fromBankName = event.getFromBankName();
        fromBankAccountNumber = event.getFromBankAccountNumber();
        toBankName = event.getToBankName();
        toBankAccountNumber = event.getToBankAccountNumber();
    }

    @EventSourcingHandler
    public void on(FirmbankingRequestUpdatedEvent event){
        log.info("FirmbankingRequestUpdatedEvent Sourcing Handler");

        firmbankingStatus = event.getFirmbankingStatus();
    }

    public FirmbankingRequestAggregate() {
    }
}

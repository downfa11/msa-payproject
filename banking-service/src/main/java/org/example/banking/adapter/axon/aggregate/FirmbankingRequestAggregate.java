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
import org.example.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import org.example.banking.adapter.out.external.bank.FirmbankingResult;
import org.example.banking.application.port.out.RequestExternalFirmbankingPort;
import org.example.banking.application.port.out.RequestFirmbankingPort;
import org.example.banking.domain.FirmbankingRequest;
import org.example.common.events.RequestFirmbankingFinishedEvent;
import org.example.common.events.RollbackFirmbankingFinishedEvent;

import javax.validation.constraints.NotNull;
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
    public FirmbankingRequestAggregate(org.example.common.events.RequestFirmbankingCommand command, RequestFirmbankingPort firmbankingPort, RequestExternalFirmbankingPort externalFirmbankingPort){
        System.out.println("FirmbankingRequestAggregate Handler");
        id = command.getAggregateIdentifier();

        // from -> to
        // 펌뱅킹 수행!
        firmbankingPort.createFirmbankingRequest(
                new FirmbankingRequest.fromBankName(command.getToBankName()),
                new FirmbankingRequest.fromBankAccountNumber(command.getToBankAccountNumber()),
                new FirmbankingRequest.toBankName("nsbank"),
                new FirmbankingRequest.toBankAccountNumber("123-456-7890"),
                new FirmbankingRequest.moneyAmount(command.getMoneyAmount()),
                new FirmbankingRequest.firmbankingStatus(0),
                new FirmbankingRequest.FirmbankingAggregateIdentifier(id));

        // firmbanking!
        FirmbankingResult firmbankingResult = externalFirmbankingPort.requestExternalFirmbanking(
                new ExternalFirmbankingRequest(
                        command.getFromBankName(),
                        command.getFromBankAccountNumber(),
                        command.getToBankName(),
                        command.getToBankAccountNumber(),
                        command.getMoneyAmount()
                ));

        int resultCode = firmbankingResult.getResultCode();

        // 0. 성공, 1. 실패
        apply(new RequestFirmbankingFinishedEvent(
                command.getRequestFirmbankingId(),
                command.getRechargeRequestId(),
                command.getMembershipId(),
                command.getToBankName(),
                command.getToBankAccountNumber(),
                command.getMoneyAmount(),
                resultCode,
                id
        ));
    }

    @CommandHandler
    public FirmbankingRequestAggregate(@NotNull org.example.common.events.RollbackFirmbankingRequestCommand command, RequestFirmbankingPort firmbankingPort, RequestExternalFirmbankingPort externalFirmbankingPort) {
        System.out.println("RollbackFirmbankingRequestCommand Handler");
        id = UUID.randomUUID().toString();

        // rollback 수행 (-> 법인 계좌 -> 고객 계좌 펌뱅킹)
        firmbankingPort.createFirmbankingRequest(
                new FirmbankingRequest.fromBankName("nsBank"),
                new FirmbankingRequest.fromBankAccountNumber("123-456-7890"),
                new FirmbankingRequest.toBankName(command.getBankName()),
                new FirmbankingRequest.toBankAccountNumber(command.getBankAccountNumber()),
                new FirmbankingRequest.moneyAmount(command.getMoneyAmount()),
                new FirmbankingRequest.firmbankingStatus(0),
                new FirmbankingRequest.FirmbankingAggregateIdentifier(id));

        // firmbanking
        FirmbankingResult result = externalFirmbankingPort.requestExternalFirmbanking(
                new ExternalFirmbankingRequest(
                        "nsBank",
                        "123-456-7890",
                        command.getBankName(),
                        command.getBankAccountNumber(),
                        command.getMoneyAmount()
                ));

        int res = result.getResultCode();

        apply(new RollbackFirmbankingFinishedEvent(
                command.getRollbackFirmbankingId(),
                command.getMembershipId(),
                id)
        );
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

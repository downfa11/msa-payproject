package org.example.banking.adapter.axon.aggregate;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.banking.adapter.axon.command.CreateRegisteredBankAcountCommand;
import org.example.banking.adapter.axon.event.CreateRegisteredBankAcountEvent;
import org.example.banking.adapter.out.external.bank.BankAccount;
import org.example.banking.adapter.out.external.bank.GetBankAccountRequest;
import org.example.banking.application.port.out.RequestBankAccountInfoPort;
import org.example.common.events.CheckedRegisteredBankAccountEvent;
import org.example.common.events.CheckRegisteredBankAccountCommand;

import javax.validation.constraints.NotNull;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate()
@Data
@Slf4j
public class RegisteredBankAccountAggregate {
    @TargetAggregateIdentifier
    private String id;

    private String membershipId;

    private String bankName;
    private String bankAccountNumber;


    @CommandHandler
    public RegisteredBankAccountAggregate(CreateRegisteredBankAcountCommand command){
        log.info("RegisteredBankAccountAggregate Handler");
        apply(new CreateRegisteredBankAcountEvent(command.getMembershipId(),command.getBankName(),command.getBankAccountNumber()));
    }

    public void handle(@NotNull CheckRegisteredBankAccountCommand command, RequestBankAccountInfoPort bankAccountInfoPort){
        log.info("CheckRegisteredBankAccountCommand Handler");
        id = command.getAggregateIdentifier();

        // Check RegisterdBankAccount
        BankAccount account = bankAccountInfoPort.getBankAccountInfo(new GetBankAccountRequest(command.getBankName(), command.getBankAccountNumber()));
        boolean isValidAccount = account.isValid();
        String firmbankingUUID = UUID.randomUUID().toString();

        apply(new CheckedRegisteredBankAccountEvent(
                        command.getRechargeRequestId()
                        , command.getCheckRegisteredBankAccountId()
                        , command.getMembershipId()
                        , isValidAccount
                        , command.getAmount()
                        , firmbankingUUID
                        , account.getBankName()
                        , account.getBankAccountNumber()
                )
        );

    }

    @EventSourcingHandler
    public void on(CreateRegisteredBankAcountEvent event){
        log.info("CreateRegisteredBankAcountEvent Sourcing Handler");

        id = UUID.randomUUID().toString();
        membershipId = event.getMembershipId();
        bankName = event.getBankName();
        bankAccountNumber = event.getBankAccountNumber();
    }

    public RegisteredBankAccountAggregate() {
    }
}

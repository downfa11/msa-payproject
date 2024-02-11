package org.example.banking.application.service;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.banking.adapter.axon.command.CreateRegisteredBankAcountCommand;
import org.example.banking.adapter.out.external.bank.BankAccount;
import org.example.banking.adapter.out.external.bank.GetBankAccountRequest;
import org.example.banking.adapter.out.persistance.RegisteredBankAccountJpaEntity;
import org.example.banking.adapter.out.persistance.RegisteredBankAccountMapper;
import org.example.banking.adapter.out.service.MembershipStatus;
import org.example.banking.application.port.in.GetRegisteredBankAccountCommand;
import org.example.banking.application.port.in.GetRegisteredBankAccountUseCase;
import org.example.banking.application.port.in.RegisterBankAccountCommand;
import org.example.banking.application.port.in.RegisterBankAccountUseCase;
import org.example.banking.application.port.out.GetMembershipPort;
import org.example.banking.application.port.out.GetRegisterBankAccountPort;
import org.example.banking.application.port.out.RegisterBankAccountPort;
import org.example.banking.application.port.out.RequestBankAccountInfoPort;
import org.example.banking.domain.RegisterBankAccount;
import org.example.common.UseCase;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterBankAccountService implements RegisterBankAccountUseCase, GetRegisteredBankAccountUseCase {

    private final RegisterBankAccountPort registerBankAccountPort;
    private final RegisteredBankAccountMapper registeredBankAccountMapper; // to Entity
    private final RequestBankAccountInfoPort requestBankAccountInfoPort;
    private final GetMembershipPort getMembershipPort;
    private final GetRegisterBankAccountPort getRegisteredBankAccountPort;

    private final CommandGateway gateway;
    // 비즈니스 로직
    // 1. 등록된 계좌인지?
    // 음.. 내부에서 외부로 나가기 위해서는 Port->Adapter -> External System

    // 2.  -> 등록을 성공하면 성공한 등록 정보를 반환
    // 2-1. -> 등록가능하지 않으면 error 반환
    @Override
    public RegisterBankAccount registerBankAccount(RegisterBankAccountCommand command) {

        MembershipStatus membershipStatus = getMembershipPort.getMembership(command.getMembershipId());
        if (!membershipStatus.isValid())
            return null;
        // call membership bank svc. 정상인지 확인한다.
        //


        BankAccount accountinfo = requestBankAccountInfoPort.getBankAccountInfo(new GetBankAccountRequest(command.getBankName(), command.getBankAccountNumber()));
        boolean accountIsValid = accountinfo.isValid();

        if (accountIsValid) {
            RegisteredBankAccountJpaEntity savedAccountInfo = registerBankAccountPort.createRegisteredBankAccount(
                    new RegisterBankAccount.MembershipId(command.getMembershipId() + ""),
                    new RegisterBankAccount.BankName(command.getBankName()),
                    new RegisterBankAccount.BankAccountNumber(command.getBankAccountNumber()),
                    new RegisterBankAccount.LinkedStatusIsValid(command.isValid()),
                    new RegisterBankAccount.AggregateIdentifier("")
            );
            return registeredBankAccountMapper.mapToDomainEntity(savedAccountInfo);
        } else return null;
    }

    @Override
    public void registerBankAccountByEvent(RegisterBankAccountCommand command) {

        gateway.send(new CreateRegisteredBankAcountCommand(command.getMembershipId(), command.getBankName(), command.getBankAccountNumber()))
                .whenComplete(
                        (result, throwable) -> {
                            if (throwable != null) {
                                throwable.printStackTrace();
                            } else {
                                //registeredBankAccount를 Insert

                                registerBankAccountPort.createRegisteredBankAccount(
                                        new RegisterBankAccount.MembershipId(command.getMembershipId() + ""),
                                        new RegisterBankAccount.BankName(command.getBankName()),
                                        new RegisterBankAccount.BankAccountNumber(command.getBankAccountNumber()),
                                        new RegisterBankAccount.LinkedStatusIsValid(command.isValid()),
                                        new RegisterBankAccount.AggregateIdentifier(result.toString())
                                );
                            }
                        }
                );
    }


    @Override
    public RegisterBankAccount getRegisterBankAccount(GetRegisteredBankAccountCommand command) {
        return registeredBankAccountMapper.mapToDomainEntity(getRegisteredBankAccountPort.getRegisteredBankAccount(command));
    }
}
package org.example.remittance.adapter.out.persistance;

import lombok.RequiredArgsConstructor;
import org.example.common.PersistanceAdapter;
import org.example.remittance.application.port.in.FindRemittanceCommand;
import org.example.remittance.application.port.in.RequestRemittanceCommand;
import org.example.remittance.application.port.out.FindRemittancePort;
import org.example.remittance.application.port.out.RequestRemittancePort;

import java.util.List;

@PersistanceAdapter
@RequiredArgsConstructor
public class RemittanceRequestPersistanceAdapter implements RequestRemittancePort, FindRemittancePort {

    private final SpringDataRemittanceRequestRepository repository;

    @Override
    public RemittanceRequestJpaEntity createRemittanceRequestHistory(RequestRemittanceCommand command) {

        return repository.save(RemittanceRequestJpaEntity.builder()
                .fromMembershipId(command.getFromMembershipId())
                .toMembershipId(command.getToMembershipId())
                .toBankName(command.getToBankName())
                .toBankAccountNumber(command.getToBankAccountNumber())
                .amount(command.getAmount())
                .remittanceType(command.getRemittanceType()).build());

    }

    @Override
    public boolean saveRemittanceRequestHistory(RemittanceRequestJpaEntity entity) {

        repository.save(entity);
        return true;
    }

    @Override
    public List<RemittanceRequestJpaEntity> findRemittanceHistory(FindRemittanceCommand command) {
        // using JPA
        return null;
    }
}
package org.example.remittance.application.port.out;

import org.example.remittance.adapter.out.persistance.RemittanceRequestJpaEntity;
import org.example.remittance.application.port.in.FindRemittanceCommand;
import org.example.remittance.application.port.in.RequestRemittanceCommand;

import java.util.List;

public interface FindRemittancePort {
    List<RemittanceRequestJpaEntity> findRemittanceHistory(FindRemittanceCommand command);

}

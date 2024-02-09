package org.example.money.adapter.out.persistance;

import lombok.RequiredArgsConstructor;
import org.example.common.PersistanceAdapter;
import org.example.money.application.port.out.CreateMemberMoneyPort;
import org.example.money.application.port.out.DecreaseMoneyPort;
import org.example.money.application.port.out.IncreaseMoneyPort;
import org.example.money.domain.MemberMoney;
import org.example.money.domain.MoneyChangingRequest;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@PersistanceAdapter
@RequiredArgsConstructor
public class MoneyChangingRequestPersistanceAdapter implements IncreaseMoneyPort, DecreaseMoneyPort {

    private final SpringDataMoneyChangingRepository moneyChangingRepository;
    private final SpringDataMemberMoneyRepository memberMoneyRepository;


    @Override
    public MoneyChangingRequestJpaEntity createMoneyChangingRequest(MoneyChangingRequest.TargetMembershipId targetMembershipId, MoneyChangingRequest.changingType changingType, MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount, MoneyChangingRequest.changingMoneyStatus changingMoneyStatus, MoneyChangingRequest.Uuid uuid) {
        return moneyChangingRepository.save(
                new MoneyChangingRequestJpaEntity(
                        targetMembershipId.getTargetMembershipId(),
                        changingType.getChangingType(),
                        changingMoneyAmount.getChangingMoneyAmount(),
                        new Timestamp(System.currentTimeMillis()),
                        changingMoneyStatus.getChangingMoneyStatus(),
                        UUID.randomUUID()
                )
        );
    }

    @Override
    public MemberMoneyJpaEntity increaseMoney(MemberMoney.MembershipId membershipId, int increaseMoneyAmount) {

        MemberMoneyJpaEntity entity;
        try{
            List<MemberMoneyJpaEntity> entityList = memberMoneyRepository.findByMembershipId(Long.parseLong(membershipId.getMembershipId()));
            entity = entityList.get(0);

            entity.setBalance(entity.getBalance()+increaseMoneyAmount);
            return memberMoneyRepository.save(entity);
        }
         catch (Exception e){
             entity = new MemberMoneyJpaEntity(
                     Long.parseLong(membershipId.getMembershipId()),
                     increaseMoneyAmount,""
             );

             entity= memberMoneyRepository.save(entity);
             return entity;
         }
    }

    @Override
    public MemberMoneyJpaEntity decreaseMoney(MemberMoney.MembershipId membershipId, int decreaseMoneyAmount) {

        MemberMoneyJpaEntity entity;
        try{
            List<MemberMoneyJpaEntity> entityList = memberMoneyRepository.findByMembershipId(Long.parseLong(membershipId.getMembershipId()));
            entity = entityList.get(0);

            entity.setBalance(entity.getBalance()-decreaseMoneyAmount);
            return memberMoneyRepository.save(entity);
        }
        catch (Exception e){
            entity = new MemberMoneyJpaEntity(
                    Long.parseLong(membershipId.getMembershipId()),
                    decreaseMoneyAmount,""
            );

            entity= memberMoneyRepository.save(entity);
            return entity;
        }
    }
}

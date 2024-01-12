package org.example.membership.application.service;

import lombok.RequiredArgsConstructor;
import org.example.common.UseCase;
import org.example.membership.adapter.out.persistance.MembershipJpaEntity;
import org.example.membership.adapter.out.persistance.MembershipMapper;
import org.example.membership.application.in.RegisterMembershipCommand;
import org.example.membership.application.in.RegisterMembershipUseCase;
import org.example.membership.application.port.out.RegisterMembershipPort;
import org.example.membership.domain.Membership;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterMembershipService implements RegisterMembershipUseCase {

    private final RegisterMembershipPort registerMembershipPort;
    private final MembershipMapper membershipMapper;
    @Override
    public Membership registerMembership(RegisterMembershipCommand command) {

        // db는 외부 시스템이라 이용하기 위해선 port, adapter를 통해서 나갈 수 있다.

       MembershipJpaEntity jpaEntity = registerMembershipPort.createMembership(
                new Membership.MembershipName(command.getName()),
               new Membership.MembershipAddress(command.getAddress()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipIsValid(command.isValid()),
                new Membership.MembershipIsCorp(command.isCorp())
        );

       // entity -> Membership 도메인으로 변환해야한다.
        return membershipMapper.mapToDomainEntity(jpaEntity);
    }
}

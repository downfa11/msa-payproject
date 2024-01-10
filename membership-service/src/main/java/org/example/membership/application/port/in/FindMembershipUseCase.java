package org.example.membership.application.port.in;

import org.example.membership.adapter.out.persistance.MembershipJpaEntity;
import org.example.membership.domain.Membership;

public interface FindMembershipUseCase {

    Membership findMembership(FindMembershipCommand command);
}

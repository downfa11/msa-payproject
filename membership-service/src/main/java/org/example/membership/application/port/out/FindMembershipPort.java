package org.example.membership.application.port.out;

import org.example.membership.adapter.out.persistance.MembershipJpaEntity;
import org.example.membership.domain.Membership;

public interface FindMembershipPort {
     MembershipJpaEntity findMembership(
             Membership.MembershipId membershipId
     );
}

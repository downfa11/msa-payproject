package org.example.membership.application.port.out;

import org.example.membership.adapter.out.persistance.MembershipJpaEntity;
import org.example.membership.domain.Membership;

public interface ModifyMembershipPort {

    MembershipJpaEntity modifyMembership(
            Membership.MembershipId membershipId,
            Membership.MembershipName membershipName,
            Membership.MembershipAddress membershipAddress,
            Membership.MembershipEmail membershipEmail,
            Membership.MembershipIsValid membershipIsValid,
            Membership.MembershipIsCorp membershipIsCorp);
}

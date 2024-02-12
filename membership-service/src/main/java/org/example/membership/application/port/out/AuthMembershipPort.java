package org.example.membership.application.port.out;

import org.example.membership.adapter.out.persistance.MembershipJpaEntity;
import org.example.membership.domain.Membership;

import java.lang.reflect.Member;

public interface AuthMembershipPort {
     String generateJwtToken(
             Membership.MembershipId membershipID
     );

     String generateRefreshToken(
             Membership.MembershipId membershipId
     );

     boolean validateJwtToken(String jwtToken);

     Membership.MembershipId parseMembershipIdFromToken(String jwtToken);
}

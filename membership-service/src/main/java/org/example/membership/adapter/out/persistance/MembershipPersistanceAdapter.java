package org.example.membership.adapter.out.persistance;

import lombok.RequiredArgsConstructor;
import org.example.common.PersistanceAdapter;
import org.example.membership.application.port.out.FindMembershipPort;
import org.example.membership.application.port.out.RegisterMembershipPort;
import org.example.membership.domain.Membership;

@PersistanceAdapter
@RequiredArgsConstructor
public class MembershipPersistanceAdapter implements RegisterMembershipPort, FindMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;
    @Override
    public MembershipJpaEntity createMembership(Membership.MembershipName membershipName,Membership.MembershipAddress membershipAddress, Membership.MembershipEmail membershipEmail,  Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp) {
        return membershipRepository.save(
                new MembershipJpaEntity(
                        membershipName.getNameValue(),
                        membershipAddress.getAddressValue(),
                        membershipEmail.getEmailValue(),
                        membershipIsValid.isValidValue(),
                        membershipIsCorp.isCorpValue()
                )
                );

    }

    @Override
    public MembershipJpaEntity findMembership(Membership.MembershipId membershipId) {
        return membershipRepository.getById(Long.parseLong(membershipId.getMembershipId()));
    }
}

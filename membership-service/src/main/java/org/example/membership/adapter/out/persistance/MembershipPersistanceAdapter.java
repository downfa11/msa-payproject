package org.example.membership.adapter.out.persistance;

import lombok.RequiredArgsConstructor;
import org.example.common.PersistanceAdapter;
import org.example.membership.application.port.out.FindMembershipPort;
import org.example.membership.application.port.out.ModifyMembershipPort;
import org.example.membership.application.port.out.RegisterMembershipPort;
import org.example.membership.domain.Membership;

@PersistanceAdapter
@RequiredArgsConstructor
public class MembershipPersistanceAdapter implements RegisterMembershipPort, FindMembershipPort, ModifyMembershipPort {

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

    @Override
    public MembershipJpaEntity modifyMembership(Membership.MembershipId membershipId, Membership.MembershipName membershipName, Membership.MembershipAddress membershipAddress, Membership.MembershipEmail membershipEmail, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp) {
        MembershipJpaEntity entity = membershipRepository.getById(Long.parseLong(membershipId.getMembershipId()));
        entity.setName(membershipName.getNameValue());
        entity.setAddress(membershipAddress.getAddressValue());
        entity.setEmail(membershipEmail.getEmailValue());
        entity.setCorp(membershipIsCorp.isCorpValue());
        entity.setValid(membershipIsValid.isValidValue());
        // 대규모 트래픽을 받는 경우 쿼리를 보내는게 더 빠를걸. JPA entity주고받는 방식은 직관성
        return membershipRepository.save(entity);
    }
}

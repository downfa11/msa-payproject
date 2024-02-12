package org.example.membership.adapter.out.persistance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.PersistanceAdapter;
import org.example.membership.adapter.out.vault.VaultAdapter;
import org.example.membership.application.port.out.FindMembershipPort;
import org.example.membership.application.port.out.ModifyMembershipPort;
import org.example.membership.application.port.out.RegisterMembershipPort;
import org.example.membership.domain.Membership;

@PersistanceAdapter
@RequiredArgsConstructor
@Slf4j
public class MembershipPersistanceAdapter implements RegisterMembershipPort, FindMembershipPort, ModifyMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;
    private final VaultAdapter vaultAdapter;
    @Override
    public MembershipJpaEntity createMembership(Membership.MembershipName membershipName,Membership.MembershipAddress membershipAddress, Membership.MembershipEmail membershipEmail,  Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp,Membership.RefreshToken refreshToken) {

        String encryptedEmail = vaultAdapter.encrypt(membershipEmail.getEmailValue());
        MembershipJpaEntity jpaEntity = new MembershipJpaEntity(
                membershipName.getNameValue(),
                membershipAddress.getAddressValue(),
                encryptedEmail,
                membershipIsValid.isValidValue(),
                membershipIsCorp.isCorpValue(),
                ""
        );
        membershipRepository.save(jpaEntity);
        log.info("entity encrypted Email : "+jpaEntity.getEmail());
        return new MembershipJpaEntity(
                membershipName.getNameValue(),
                membershipAddress.getAddressValue(),
                membershipEmail.getEmailValue(),
                membershipIsValid.isValidValue(),
                membershipIsCorp.isCorpValue(),
                ""
        );
    }

    @Override
    public MembershipJpaEntity findMembership(Membership.MembershipId membershipId) {
        MembershipJpaEntity membershipJpaEntity = membershipRepository.getById(Long.parseLong(membershipId.getMembershipId()));
        String encryptedEmail = membershipJpaEntity.getEmail();
        String decrptedEmail = vaultAdapter.decrypt(encryptedEmail);
        membershipJpaEntity.setEmail(decrptedEmail);
        return membershipRepository.getById(Long.parseLong(membershipId.getMembershipId()));
    }

    @Override
    public MembershipJpaEntity modifyMembership(Membership.MembershipId membershipId, Membership.MembershipName membershipName, Membership.MembershipAddress membershipAddress, Membership.MembershipEmail membershipEmail, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp, Membership.RefreshToken refreshToken) {
        MembershipJpaEntity entity = membershipRepository.getById(Long.parseLong(membershipId.getMembershipId()));
        String encryptedEmail = vaultAdapter.encrypt(membershipEmail.getEmailValue());

        entity.setName(membershipName.getNameValue());
        entity.setAddress(membershipAddress.getAddressValue());
        entity.setEmail(encryptedEmail);
        entity.setCorp(membershipIsCorp.isCorpValue());
        entity.setValid(membershipIsValid.isValidValue());
        entity.setRefreshToken(refreshToken.getRefreshToken());
        // 대규모 트래픽을 받는 경우 쿼리를 보내는게 더 빠를걸. JPA entity주고받는 방식은 직관성
        membershipRepository.save(entity);

        return new MembershipJpaEntity(
                membershipName.getNameValue(),
                membershipAddress.getAddressValue(),
                membershipEmail.getEmailValue(),
                membershipIsValid.isValidValue(),
                membershipIsCorp.isCorpValue(),
                ""
        );
    }
}

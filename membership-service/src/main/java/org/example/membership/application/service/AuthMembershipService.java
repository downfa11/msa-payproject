package org.example.membership.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.membership.adapter.out.persistance.MembershipJpaEntity;
import org.example.membership.adapter.out.persistance.MembershipMapper;
import org.example.membership.application.port.in.LoginMembershipCommand;
import org.example.membership.application.port.in.LoginMembershipUseCase;
import org.example.membership.application.port.in.RefreshTokenCommand;
import org.example.membership.application.port.in.ValidateTokenCommand;
import org.example.membership.application.port.out.AuthMembershipPort;
import org.example.membership.application.port.out.FindMembershipPort;
import org.example.membership.application.port.out.ModifyMembershipPort;
import org.example.membership.domain.JWtToken;
import org.example.membership.domain.Membership;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class AuthMembershipService implements LoginMembershipUseCase {

    private final AuthMembershipPort authMembershipPort;
    private final FindMembershipPort findMembershipPort;
    private final ModifyMembershipPort modifyMembershipPort;
    private final MembershipMapper mapper;
    @Override
    public JWtToken LoginMembership(LoginMembershipCommand command) {

        String membershipId = command.getMembershipId();
        MembershipJpaEntity membershipJpaEntity = findMembershipPort.findMembership(
                new Membership.MembershipId(membershipId)
        );

        if(membershipJpaEntity.isValid()) {
            String jwtToken = authMembershipPort.generateJwtToken(
                    new Membership.MembershipId(membershipId)
            );
            String refreshToken = authMembershipPort.generateRefreshToken(
                    new Membership.MembershipId(membershipId)
            );

            modifyMembershipPort.modifyMembership(
                    new Membership.MembershipId(membershipId),
                    new Membership.MembershipName(membershipJpaEntity.getName()),
                    new Membership.MembershipAddress(membershipJpaEntity.getAddress()),
                    new Membership.MembershipEmail(membershipJpaEntity.getEmail()),
                    new Membership.MembershipIsValid(membershipJpaEntity.isValid()),
                    new Membership.MembershipIsCorp(membershipJpaEntity.isCorp()),
                    new Membership.RefreshToken(refreshToken)
                    );

            return JWtToken.generateJwtToken(
                    new JWtToken.MembershipId(membershipId),
                    new JWtToken.MembershipJwtToken(jwtToken),
                    new JWtToken.MembershipRefreshToken(refreshToken)
            );
        }
        return null;
    }

    @Override
    public JWtToken refreshJwtTokenByRefreshToken(RefreshTokenCommand command) {
        String RequestedRefreshToken = command.getRefreshToken();
        boolean isValid = authMembershipPort.validateJwtToken(RequestedRefreshToken);

        if(isValid){
            Membership.MembershipId membershipId = authMembershipPort.parseMembershipIdFromToken(RequestedRefreshToken);
        String membershipIdString = membershipId.getMembershipId();

        MembershipJpaEntity membershipJpaEntity = findMembershipPort.findMembership(membershipId);
        if(!membershipJpaEntity.getRefreshToken().equals(command.getRefreshToken()))
            return null;


        if(membershipJpaEntity.isValid()){
            String newJwtToken = authMembershipPort.generateJwtToken(
                    new Membership.MembershipId(membershipIdString)
            );

            return JWtToken.generateJwtToken(
                    new JWtToken.MembershipId(membershipIdString),
                    new JWtToken.MembershipJwtToken(newJwtToken),
                    new JWtToken.MembershipRefreshToken(RequestedRefreshToken)
            );
        }
        }
        log.info("membershipJpaEntity is not valid");
        return null;
    }

    @Override
    public boolean validateJwtToken(ValidateTokenCommand command) {

        String token = command.getJwtToken();
       return authMembershipPort.validateJwtToken(token);
    }

    @Override
    public Membership getMembershipByJwtToken(ValidateTokenCommand command) {
        String jwtToken = command.getJwtToken();
        boolean isValid = authMembershipPort.validateJwtToken(jwtToken);

        if (isValid) {
            Membership.MembershipId membershipId = authMembershipPort.parseMembershipIdFromToken(jwtToken);

            MembershipJpaEntity membershipJpaEntity = findMembershipPort.findMembership(membershipId);
            if (!membershipJpaEntity.getRefreshToken().equals(command.getJwtToken())) return null;

            return mapper.mapToDomainEntity(membershipJpaEntity);
        }
        return null;
    }

}

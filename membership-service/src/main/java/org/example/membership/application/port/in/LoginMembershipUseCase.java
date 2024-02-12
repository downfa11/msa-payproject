package org.example.membership.application.port.in;
import org.example.membership.domain.JWtToken;
import org.example.membership.domain.Membership;

public interface LoginMembershipUseCase {

    JWtToken LoginMembership(LoginMembershipCommand command);

    JWtToken refreshJwtTokenByRefreshToken(RefreshTokenCommand command);

    boolean validateJwtToken(ValidateTokenCommand command);
    Membership getMembershipByJwtToken(ValidateTokenCommand command);

}

package org.example.membership.application.in;
import org.example.membership.domain.Membership;

public interface RegisterMembershipUseCase {

    Membership registerMembership(RegisterMembershipCommand command);
}

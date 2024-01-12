package org.example.membership.application.in;

import org.example.membership.domain.Membership;

public interface ModifyMembershipUseCase {

    Membership modifyMembership(ModifyMembershipCommand command);
}

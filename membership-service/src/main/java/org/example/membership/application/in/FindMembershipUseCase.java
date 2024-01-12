package org.example.membership.application.in;

import org.example.membership.domain.Membership;

public interface FindMembershipUseCase {

    Membership findMembership(FindMembershipCommand command);
}

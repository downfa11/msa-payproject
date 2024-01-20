package org.example.remittance.application.port.out.membership;

public interface GetMembershipPort {
    public MembershipStatus getMembershipStatus(String membershipId);
}
